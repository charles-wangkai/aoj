import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
  static final int LIMIT = 100;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      int[] a = new int[n];
      int[] b = new int[n];
      for (int i = 0; i < n; ++i) {
        a[i] = sc.nextInt();
        b[i] = sc.nextInt();
      }

      System.out.println(solve(a, b));
    }

    sc.close();
  }

  static int solve(int[] a, int[] b) {
    Map<Integer, List<Integer>> nodeToAdjs = new HashMap<>();
    for (int i = 0; i < a.length; ++i) {
      nodeToAdjs.putIfAbsent(a[i], new ArrayList<>());
      nodeToAdjs.get(a[i]).add(b[i]);

      nodeToAdjs.putIfAbsent(b[i], new ArrayList<>());
      nodeToAdjs.get(b[i]).add(a[i]);
    }

    boolean[] visited = new boolean[LIMIT + 1];

    return nodeToAdjs.keySet().stream()
        .mapToInt(node -> search(nodeToAdjs, visited, node))
        .max()
        .getAsInt();
  }

  static int search(Map<Integer, List<Integer>> nodeToAdjs, boolean[] visited, int node) {
    visited[node] = true;

    int maxSubResult = 0;
    for (int adj : nodeToAdjs.get(node)) {
      if (!visited[adj]) {
        maxSubResult = Math.max(maxSubResult, search(nodeToAdjs, visited, adj));
      }
    }

    visited[node] = false;

    return 1 + maxSubResult;
  }
}