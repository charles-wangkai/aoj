import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      int k = sc.nextInt();
      if (n == 0 && k == 0) {
        break;
      }

      sc.nextLine();
      String[] queries = new String[k];
      for (int i = 0; i < queries.length; ++i) {
        queries[i] = sc.nextLine();
      }

      System.out.println(solve(n, queries));
    }

    sc.close();
  }

  static String solve(int n, String[] queries) {
    @SuppressWarnings("unchecked")
    Map<Integer, Integer>[] edgeMaps = new Map[n];
    for (int i = 0; i < edgeMaps.length; ++i) {
      edgeMaps[i] = new HashMap<>();
    }

    List<Integer> result = new ArrayList<>();
    for (String query : queries) {
      String[] parts = query.split(" ");
      if (parts[0].equals("0")) {
        int a = Integer.parseInt(parts[1]);
        int b = Integer.parseInt(parts[2]);

        result.add(computeDistance(edgeMaps, a - 1, b - 1));
      } else {
        int c = Integer.parseInt(parts[1]);
        int d = Integer.parseInt(parts[2]);
        int e = Integer.parseInt(parts[3]);

        edgeMaps[c - 1].put(
            d - 1, Math.min(edgeMaps[c - 1].getOrDefault(d - 1, Integer.MAX_VALUE), e));
        edgeMaps[d - 1].put(
            c - 1, Math.min(edgeMaps[d - 1].getOrDefault(c - 1, Integer.MAX_VALUE), e));
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
  }

  static int computeDistance(Map<Integer, Integer>[] edgeMaps, int from, int to) {
    Map<Integer, Integer> nodeToDistance = new HashMap<>();
    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.distance));
    pq.offer(new Element(from, 0));
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (!nodeToDistance.containsKey(head.node)) {
        nodeToDistance.put(head.node, head.distance);
        for (int adj : edgeMaps[head.node].keySet()) {
          if (!nodeToDistance.containsKey(adj)) {
            pq.offer(new Element(adj, head.distance + edgeMaps[head.node].get(adj)));
          }
        }
      }
    }

    return nodeToDistance.getOrDefault(to, -1);
  }
}

class Element {
  int node;
  int distance;

  Element(int node, int distance) {
    this.node = node;
    this.distance = distance;
  }
}
