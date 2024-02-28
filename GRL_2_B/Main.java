// https://en.wikipedia.org/wiki/Edmonds%27_algorithm
// https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int r = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    int[] w = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
      w[i] = sc.nextInt();
    }

    System.out.println(solve(V, s, t, w, r));

    sc.close();
  }

  static int solve(int V, int[] s, int[] t, int[] w, int r) {
    return computeSpanningArborescence(
            IntStream.range(0, V).boxed().collect(Collectors.toSet()),
            IntStream.range(0, s.length)
                .mapToObj(i -> new Edge(s[i], t[i], w[i], null))
                .collect(Collectors.toList()),
            r)
        .stream()
        .mapToInt(edge -> edge.weight)
        .sum();
  }

  static List<Edge> computeSpanningArborescence(Set<Integer> nodes, List<Edge> edges, int r) {
    List<Edge> simplified = new ArrayList<>();
    Map<Integer, Map<Integer, Integer>> fromToEdges = new HashMap<>();
    for (int node : nodes) {
      fromToEdges.put(node, new HashMap<>());
    }
    for (Edge edge : edges) {
      if (edge.to != r
          && edge.weight < fromToEdges.get(edge.from).getOrDefault(edge.to, Integer.MAX_VALUE)) {
        fromToEdges.get(edge.from).put(edge.to, edge.weight);
        simplified.add(edge);
      }
    }

    Map<Integer, Edge> toToIncoming = new HashMap<>();
    for (Edge edge : simplified) {
      if (!toToIncoming.containsKey(edge.to) || edge.weight < toToIncoming.get(edge.to).weight) {
        toToIncoming.put(edge.to, edge);
      }
    }

    Map<Integer, Integer> nodeToComponent =
        buildNodeToComponent(nodes, toToIncoming.values().stream().collect(Collectors.toList()));

    Map<Integer, Set<Integer>> componentToNodes = new HashMap<>();
    for (int node : nodeToComponent.keySet()) {
      componentToNodes.putIfAbsent(nodeToComponent.get(node), new HashSet<>());
      componentToNodes.get(nodeToComponent.get(node)).add(node);
    }

    Optional<Integer> cycleComponent =
        componentToNodes.keySet().stream()
            .filter(component -> componentToNodes.get(component).size() != 1)
            .findAny();
    if (cycleComponent.isEmpty()) {
      return toToIncoming.values().stream().collect(Collectors.toList());
    }

    Set<Integer> cycleNodes = componentToNodes.get(cycleComponent.get());
    int contracted = cycleNodes.iterator().next();

    List<Edge> nextEdges = new ArrayList<>();
    for (Edge edge : simplified) {
      if (cycleNodes.contains(edge.from)) {
        if (!cycleNodes.contains(edge.to)) {
          nextEdges.add(new Edge(contracted, edge.to, edge.weight, edge));
        }
      } else if (cycleNodes.contains(edge.to)) {
        nextEdges.add(
            new Edge(edge.from, contracted, edge.weight - toToIncoming.get(edge.to).weight, edge));
      } else {
        nextEdges.add(new Edge(edge.from, edge.to, edge.weight, edge));
      }
    }

    List<Edge> subResult =
        computeSpanningArborescence(
            Stream.concat(
                    nodes.stream().filter(node -> !cycleNodes.contains(node)),
                    Stream.of(contracted))
                .collect(Collectors.toSet()),
            nextEdges,
            r);

    Edge incomingToContracted =
        subResult.stream().filter(edge -> edge.to == contracted).findAny().get();

    List<Edge> result = new ArrayList<>();
    for (Edge edge : toToIncoming.values()) {
      if (nodeToComponent.get(edge.to).equals(cycleComponent.get())
          && edge.to != incomingToContracted.original.to) {
        result.add(edge);
      }
    }
    for (Edge edge : subResult) {
      result.add(edge.original);
    }

    return result;
  }

  static Map<Integer, Integer> buildNodeToComponent(Set<Integer> nodes, List<Edge> edges) {
    Map<Integer, List<Edge>> fromToEdges = new HashMap<>();
    for (int node : nodes) {
      fromToEdges.put(node, new ArrayList<>());
    }
    for (Edge edge : edges) {
      fromToEdges.get(edge.from).add(edge);
    }

    List<Integer> sortedNodes = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();
    for (int node : nodes) {
      if (!visited.contains(node)) {
        search1(fromToEdges, sortedNodes, visited, node);
      }
    }
    Collections.reverse(sortedNodes);

    Map<Integer, List<Edge>> toToEdges = new HashMap<>();
    for (int node : nodes) {
      toToEdges.put(node, new ArrayList<>());
    }
    for (Edge edge : edges) {
      toToEdges.get(edge.to).add(edge);
    }

    Map<Integer, Integer> nodeToComponent = new HashMap<>();
    int component = 0;
    for (int node : sortedNodes) {
      if (!nodeToComponent.containsKey(node)) {
        search2(toToEdges, nodeToComponent, node, component);
        ++component;
      }
    }

    return nodeToComponent;
  }

  static void search2(
      Map<Integer, List<Edge>> toToEdges,
      Map<Integer, Integer> nodeToComponent,
      int node,
      int component) {
    nodeToComponent.put(node, component);

    for (Edge edge : toToEdges.get(node)) {
      if (!nodeToComponent.containsKey(edge.from)) {
        search2(toToEdges, nodeToComponent, edge.from, component);
      }
    }
  }

  static void search1(
      Map<Integer, List<Edge>> fromToEdges,
      List<Integer> sortedNodes,
      Set<Integer> visited,
      int node) {
    visited.add(node);

    for (Edge edge : fromToEdges.get(node)) {
      if (!visited.contains(edge.to)) {
        search1(fromToEdges, sortedNodes, visited, edge.to);
      }
    }

    sortedNodes.add(node);
  }
}

class Edge {
  int from;
  int to;
  int weight;
  Edge original;

  Edge(int from, int to, int weight, Edge original) {
    this.from = from;
    this.to = to;
    this.weight = weight;
    this.original = original;
  }
}