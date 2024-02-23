// https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main implements Runnable {
  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 28).start();
  }

  @Override
  public void run() {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
    }
    int Q = sc.nextInt();
    int[] u = new int[Q];
    int[] v = new int[Q];
    for (int i = 0; i < Q; ++i) {
      u[i] = sc.nextInt();
      v[i] = sc.nextInt();
    }

    System.out.println(solve(V, s, t, u, v));

    sc.close();
  }

  static String solve(int V, int[] s, int[] t, int[] u, int[] v) {
    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[V];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < s.length; ++i) {
      adjLists[s[i]].add(t[i]);
    }

    List<Integer> sortedNodes = new ArrayList<>();
    boolean[] visited = new boolean[V];
    for (int i = 0; i < visited.length; ++i) {
      if (!visited[i]) {
        search1(adjLists, sortedNodes, visited, i);
      }
    }
    Collections.reverse(sortedNodes);

    @SuppressWarnings("unchecked")
    List<Integer>[] reversedAdjLists = new List[V];
    for (int i = 0; i < reversedAdjLists.length; ++i) {
      reversedAdjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < s.length; ++i) {
      reversedAdjLists[t[i]].add(s[i]);
    }

    int[] components = new int[V];
    Arrays.fill(components, -1);
    int component = 0;
    for (int node : sortedNodes) {
      if (components[node] == -1) {
        search2(reversedAdjLists, components, node, component);
        ++component;
      }
    }

    return IntStream.range(0, u.length)
        .map(i -> (components[u[i]] == components[v[i]]) ? 1 : 0)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining("\n"));
  }

  static void search2(List<Integer>[] reversedAdjLists, int[] components, int node, int component) {
    components[node] = component;

    for (int adj : reversedAdjLists[node]) {
      if (components[adj] == -1) {
        search2(reversedAdjLists, components, adj, component);
      }
    }
  }

  static void search1(
      List<Integer>[] adjLists, List<Integer> sortedNodes, boolean[] visited, int node) {
    visited[node] = true;

    for (int adj : adjLists[node]) {
      if (!visited[adj]) {
        search1(adjLists, sortedNodes, visited, adj);
      }
    }

    sortedNodes.add(node);
  }
}