import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      int[] p = new int[n];
      int[] q = new int[n];
      int[] r = new int[n];
      int[] b = new int[n];
      for (int i = 0; i < n; ++i) {
        p[i] = sc.nextInt();
        q[i] = sc.nextInt();
        r[i] = sc.nextInt();
        b[i] = sc.nextInt();
      }

      System.out.println(solve(p, q, r, b));
    }

    sc.close();
  }

  static int solve(int[] p, int[] q, int[] r, int[] b) {
    int n = p.length;

    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[n];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < n; ++i) {
      if (r[i] != 0) {
        adjLists[r[i] - 1].add(i);
      }
      if (b[i] != 0) {
        adjLists[b[i] - 1].add(i);
      }
    }

    List<Integer> sorted = topologicalSort(adjLists);

    int[] weights = new int[n];
    for (int index : sorted) {
      int l =
          lcm(
              p[index] * ((r[index] == 0) ? 1 : weights[r[index] - 1]),
              q[index] * ((b[index] == 0) ? 1 : weights[b[index] - 1]));

      weights[index] = l / p[index] + l / q[index];
    }

    return weights[sorted.get(sorted.size() - 1)];
  }

  static int lcm(int x, int y) {
    return x / gcd(x, y) * y;
  }

  static int gcd(int x, int y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  static List<Integer> topologicalSort(List<Integer>[] adjLists) {
    int n = adjLists.length;

    List<Integer> sorted = new ArrayList<>();
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; ++i) {
      if (!visited[i]) {
        search(sorted, adjLists, visited, i);
      }
    }
    Collections.reverse(sorted);

    return sorted;
  }

  static void search(List<Integer> sorted, List<Integer>[] adjLists, boolean[] visited, int node) {
    visited[node] = true;

    for (int adj : adjLists[node]) {
      if (!visited[adj]) {
        search(sorted, adjLists, visited, adj);
      }
    }

    sorted.add(node);
  }
}
