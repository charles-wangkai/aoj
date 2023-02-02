import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      if (N == 0) {
        break;
      }

      int[] u = new int[M];
      int[] v = new int[M];
      int[] d = new int[M];
      int[] c = new int[M];
      for (int i = 0; i < M; ++i) {
        u[i] = sc.nextInt() - 1;
        v[i] = sc.nextInt() - 1;
        d[i] = sc.nextInt();
        c[i] = sc.nextInt();
      }

      System.out.println(solve(N, u, v, d, c));
    }

    sc.close();
  }

  static int solve(int N, int[] u, int[] v, int[] d, int[] c) {
    @SuppressWarnings("unchecked")
    List<Integer>[] edgeLists = new List[N];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < u.length; ++i) {
      edgeLists[u[i]].add(i);
      edgeLists[v[i]].add(i);
    }

    int[] distances = new int[N];
    Arrays.fill(distances, -1);
    int[] costs = new int[N];
    PriorityQueue<Element> pq =
        new PriorityQueue<>(
            Comparator.comparing((Element e) -> e.distance).thenComparing(e -> e.cost));
    pq.offer(new Element(0, 0, 0));
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (distances[head.node] == -1) {
        distances[head.node] = head.distance;
        costs[head.node] = head.cost;

        for (int edge : edgeLists[head.node]) {
          int other = (u[edge] == head.node) ? v[edge] : u[edge];
          if (distances[other] == -1) {
            pq.offer(new Element(other, distances[head.node] + d[edge], c[edge]));
          }
        }
      }
    }

    return Arrays.stream(costs).sum();
  }
}

class Element {
  int node;
  int distance;
  int cost;

  Element(int node, int distance, int cost) {
    this.node = node;
    this.distance = distance;
    this.cost = cost;
  }
}
