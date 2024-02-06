import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] u = new int[n];
    int[][] v = new int[n][];
    int[][] c = new int[n][];
    for (int i = 0; i < n; ++i) {
      u[i] = sc.nextInt();
      int k = sc.nextInt();
      v[i] = new int[k];
      c[i] = new int[k];
      for (int j = 0; j < k; ++j) {
        v[i][j] = sc.nextInt();
        c[i][j] = sc.nextInt();
      }
    }

    System.out.println(solve(u, v, c));

    sc.close();
  }

  static String solve(int[] u, int[][] v, int[][] c) {
    int n = u.length;

    Edge[][] edgeLists = new Edge[n][];
    for (int i = 0; i < u.length; ++i) {
      edgeLists[u[i]] = new Edge[v[i].length];
      for (int j = 0; j < edgeLists[u[i]].length; ++j) {
        edgeLists[u[i]][j] = new Edge(v[i][j], c[i][j]);
      }
    }

    int[] distances = new int[n];
    Arrays.fill(distances, -1);
    PriorityQueue<Element> pq =
        new PriorityQueue<>(Comparator.comparing(element -> element.distance));
    pq.offer(new Element(0, 0));
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (distances[head.node] == -1) {
        distances[head.node] = head.distance;

        for (Edge edge : edgeLists[head.node]) {
          if (distances[edge.to] == -1) {
            pq.offer(new Element(edge.to, distances[head.node] + edge.weight));
          }
        }
      }
    }

    return IntStream.range(0, distances.length)
        .mapToObj(i -> String.format("%d %d", i, distances[i]))
        .collect(Collectors.joining("\n"));
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

class Edge {
  int to;
  int weight;

  Edge(int to, int weight) {
    this.to = to;
    this.weight = weight;
  }
}