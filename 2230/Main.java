// https://www.hankcs.com/program/algorithm/aoj-2230-how-to-create-a-good-game.html

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int INF = 2000;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int M = sc.nextInt();
    int[] x = new int[M];
    int[] y = new int[M];
    int[] s = new int[M];
    for (int i = 0; i < M; ++i) {
      x[i] = sc.nextInt();
      y[i] = sc.nextInt();
      s[i] = sc.nextInt();
    }

    System.out.println(solve(N, x, y, s));

    sc.close();
  }

  static int solve(int N, int[] x, int[] y, int[] s) {
    int[] inDegrees = new int[N];
    int[] outDegrees = new int[N];
    for (int i = 0; i < x.length; ++i) {
      ++outDegrees[x[i]];
      ++inDegrees[y[i]];
    }

    // Indices:
    // 0 - source
    // [1,N] - checkpoints
    // N+1 - sink

    List<Edge> edges = new ArrayList<>();

    @SuppressWarnings("unchecked")
    List<Integer>[] edgeLists = new List[N + 2];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < x.length; ++i) {
      addEdges(edges, edgeLists, x[i] + 1, y[i] + 1, INF, -s[i]);
    }
    for (int i = 0; i < N; ++i) {
      if (inDegrees[i] > outDegrees[i]) {
        addEdges(edges, edgeLists, 0, i + 1, inDegrees[i] - outDegrees[i], 0);
      } else {
        addEdges(edges, edgeLists, i + 1, N + 1, outDegrees[i] - inDegrees[i], 0);
      }
    }
    addEdges(
        edges, edgeLists, N, 1, INF, -computeMinCostFlow(edges, edgeLists, 1, N, 1).distances[N]);

    return computeMinCostFlow(
                edges,
                edgeLists,
                0,
                N + 1,
                IntStream.range(0, N).map(i -> Math.max(0, inDegrees[i] - outDegrees[i])).sum())
            .minCost
        - Arrays.stream(s).sum();
  }

  static Outcome computeMinCostFlow(
      List<Edge> edges, List<Integer>[] edgeLists, int s, int t, int f) {
    int N = edgeLists.length;

    int minCost = 0;
    int[] distances = new int[N];
    while (f != 0) {
      int[] prevEdges = new int[N];
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
      minCost += d * distances[t];

      for (int v = t; v != s; v = edges.get(prevEdges[v]).from) {
        Edge edge = edges.get(prevEdges[v]);

        edge.capacity -= d;
        edges.get(prevEdges[v] ^ 1).capacity += d;
      }
    }

    return new Outcome(minCost, distances);
  }

  static void addEdges(List<Edge> edges, List<Integer>[] edgeLists, int u, int v, int z, int cost) {
    edges.add(new Edge(u, v, z, cost));
    edgeLists[u].add(edges.size() - 1);

    edges.add(new Edge(v, u, 0, -cost));
    edgeLists[v].add(edges.size() - 1);
  }
}

class Outcome {
  int minCost;
  int[] distances;

  Outcome(int minCost, int[] distances) {
    this.minCost = minCost;
    this.distances = distances;
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