// https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=1116561

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      if (N == 0 && M == 0) {
        break;
      }

      int[] h = new int[N];
      for (int i = 0; i < h.length; ++i) {
        h[i] = sc.nextInt();
      }
      int[] a = new int[M];
      int[] b = new int[M];
      int[] c = new int[M];
      for (int i = 0; i < M; ++i) {
        a[i] = sc.nextInt();
        b[i] = sc.nextInt();
        c[i] = sc.nextInt();
      }

      System.out.println(solve(h, a, b, c));
    }

    sc.close();
  }

  static int solve(int[] h, int[] a, int[] b, int[] c) {
    int N = h.length;

    int[] sortedNodes =
        IntStream.range(0, N)
            .boxed()
            .sorted(Comparator.<Integer, Integer>comparing(i -> h[i]).reversed())
            .mapToInt(Integer::intValue)
            .toArray();

    int[] sortedEdges =
        IntStream.range(0, c.length)
            .boxed()
            .sorted(Comparator.comparing(i -> c[i]))
            .mapToInt(Integer::intValue)
            .toArray();

    int result = 0;
    int count = 0;
    Set<Integer> visited = new HashSet<>();
    int[] parents = new int[N];
    Arrays.fill(parents, -1);
    for (int i = 0; i < sortedNodes.length; ++i) {
      visited.add(sortedNodes[i]);

      if (i == sortedNodes.length - 1 || h[sortedNodes[i]] != h[sortedNodes[i + 1]]) {
        for (int j = 0; j < sortedEdges.length; ++j) {
          int node1 = a[sortedEdges[j]] - 1;
          int node2 = b[sortedEdges[j]] - 1;
          if (visited.contains(node1) && visited.contains(node2)) {
            int root1 = findRoot(parents, node1);
            int root2 = findRoot(parents, node2);
            if (root1 != root2) {
              parents[root2] = root1;
              result += c[sortedEdges[j]];
              ++count;
            }
          }
        }

        if (count != i) {
          Arrays.fill(parents, -1);
          result = 0;
          count = 0;
        }
      }
    }

    return result;
  }

  static int findRoot(int[] parents, int node) {
    if (parents[node] == -1) {
      return node;
    }

    parents[node] = findRoot(parents, parents[node]);

    return parents[node];
  }
}