import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int[] u = new int[E];
    int[] v = new int[E];
    int[] c = new int[E];
    for (int i = 0; i < E; ++i) {
      u[i] = sc.nextInt();
      v[i] = sc.nextInt();
      c[i] = sc.nextInt();
    }

    System.out.println(solve(V, u, v, c));

    sc.close();
  }

  static int solve(int V, int[] u, int[] v, int[] c) {
    List<Edge> edges = new ArrayList<>();

    @SuppressWarnings("unchecked")
    List<Integer>[] edgeLists = new List[V];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < u.length; ++i) {
      addEdges(edges, edgeLists, u[i], v[i], c[i]);
    }

    return dinic(edges, edgeLists, 0, V - 1);
  }

  static void addEdges(List<Edge> edges, List<Integer>[] edgeLists, int u, int v, int z) {
    edges.add(new Edge(u, v, z));
    edgeLists[u].add(edges.size() - 1);

    edges.add(new Edge(v, u, 0));
    edgeLists[v].add(edges.size() - 1);
  }

  static int dinic(List<Edge> edges, List<Integer>[] edgeLists, int s, int t) {
    int result = 0;
    while (true) {
      int[] levels = bfs(edges, edgeLists, s, t);
      if (levels == null) {
        break;
      }

      while (true) {
        int minflow = dfs(edges, edgeLists, levels, s, t, Integer.MAX_VALUE);
        if (minflow == 0) {
          break;
        }

        result += minflow;
      }
    }

    return result;
  }

  static int[] bfs(List<Edge> edges, List<Integer>[] edgeLists, int s, int t) {
    int[] levels = new int[edgeLists.length];
    Arrays.fill(levels, -1);
    levels[s] = 0;

    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(s);

    while (!queue.isEmpty()) {
      int head = queue.poll();
      if (head == t) {
        return levels;
      }

      for (int e : edgeLists[head]) {
        Edge edge = edges.get(e);
        if (edge.capacity != 0 && levels[edge.to] == -1) {
          levels[edge.to] = levels[head] + 1;
          queue.offer(edge.to);
        }
      }
    }

    return null;
  }

  static int dfs(List<Edge> edges, List<Integer>[] edgeLists, int[] levels, int s, int t, int low) {
    if (s == t) {
      return low;
    }

    int result = 0;
    for (int e : edgeLists[s]) {
      Edge edge = edges.get(e);
      if (edge.capacity != 0 && levels[edge.to] == levels[s] + 1) {
        int next = dfs(edges, edgeLists, levels, edge.to, t, Math.min(low - result, edge.capacity));
        edge.capacity -= next;
        edges.get(e ^ 1).capacity += next;

        result += next;
        if (result == low) {
          break;
        }
      }
    }

    if (result == 0) {
      levels[s] = -1;
    }

    return result;
  }
}

class Edge {
  int from;
  int to;
  int capacity;

  Edge(int from, int to, int capacity) {
    this.from = from;
    this.to = to;
    this.capacity = capacity;
  }
}