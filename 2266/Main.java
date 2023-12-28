// https://blog.51cto.com/u_15127504/3509930

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int M = sc.nextInt();
    int N = sc.nextInt();
    int K = sc.nextInt();
    int[] w = new int[N];
    for (int i = 0; i < w.length; ++i) {
      w[i] = sc.nextInt();
    }
    int[] a = new int[K];
    for (int i = 0; i < a.length; ++i) {
      a[i] = sc.nextInt();
    }

    System.out.println(solve(M, w, a));

    sc.close();
  }

  static int solve(int M, int[] w, int[] a) {
    if (IntStream.range(0, a.length - 1).anyMatch(i -> a[i] == a[i + 1])) {
      return solve(
          M,
          w,
          IntStream.range(0, a.length)
              .filter(i -> i == 0 || a[i] != a[i - 1])
              .map(i -> a[i])
              .toArray());
    }

    List<Edge> edges = new ArrayList<>();

    @SuppressWarnings("unchecked")
    List<Integer>[] edgeLists = new List[a.length];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < a.length - 1; ++i) {
      addEdges(edges, edgeLists, i, i + 1, M - 1, 0);
    }

    Map<Integer, Integer> ballToLastIndex = new HashMap<>();
    for (int i = 0; i < a.length; ++i) {
      if (ballToLastIndex.containsKey(a[i])) {
        addEdges(edges, edgeLists, ballToLastIndex.get(a[i]), i - 1, 1, -w[a[i] - 1]);
      }

      ballToLastIndex.put(a[i], i);
    }

    return Arrays.stream(a).map(ai -> w[ai - 1]).sum()
        + computeMinCostFlow(edges, edgeLists, 0, a.length - 1, M - 1);
  }

  static int computeMinCostFlow(List<Edge> edges, List<Integer>[] edgeLists, int s, int t, int f) {
    int N = edgeLists.length;

    int result = 0;
    while (f != 0) {
      int[] prevEdges = new int[N];
      int[] distances = new int[N];
      Arrays.fill(distances, Integer.MAX_VALUE);
      distances[s] = 0;
      while (true) {
        boolean updated = false;
        for (int v = 0; v < N; ++v) {
          if (distances[v] != Integer.MAX_VALUE) {
            for (int e : edgeLists[v]) {
              Edge edge = edges.get(e);
              if (edge.capacity != 0 && distances[v] + edge.cost < distances[edge.to]) {
                distances[edge.to] = distances[v] + edge.cost;
                prevEdges[edge.to] = e;

                updated = true;
              }
            }
          }
        }

        if (!updated) {
          break;
        }
      }

      int d = f;
      for (int v = t; v != s; v = edges.get(prevEdges[v]).from) {
        d = Math.min(d, edges.get(prevEdges[v]).capacity);
      }
      f -= d;
      result += d * distances[t];

      for (int v = t; v != s; v = edges.get(prevEdges[v]).from) {
        Edge edge = edges.get(prevEdges[v]);

        edge.capacity -= d;
        edges.get(prevEdges[v] ^ 1).capacity += d;
      }
    }

    return result;
  }

  static void addEdges(List<Edge> edges, List<Integer>[] edgeLists, int u, int v, int z, int cost) {
    edges.add(new Edge(u, v, z, cost));
    edgeLists[u].add(edges.size() - 1);

    edges.add(new Edge(v, u, 0, -cost));
    edgeLists[v].add(edges.size() - 1);
  }
}

class Edge {
  int from;
  int to;
  int capacity;
  int cost;

  Edge(int from, int to, int capacity, int cost) {
    this.from = from;
    this.to = to;
    this.capacity = capacity;
    this.cost = cost;
  }
}