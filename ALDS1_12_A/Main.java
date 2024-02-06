import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[][] A = new int[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        A[i][j] = sc.nextInt();
      }
    }

    System.out.println(solve(A));

    sc.close();
  }

  static int solve(int[][] A) {
    int n = A.length;

    List<Pair> pairs = new ArrayList<>();
    for (int i = 0; i < n; ++i) {
      for (int j = i + 1; j < n; ++j) {
        if (A[i][j] != -1) {
          pairs.add(new Pair(i, j));
        }
      }
    }
    Collections.sort(pairs, Comparator.comparing(pair -> A[pair.u][pair.v]));

    int result = 0;
    int[] parents = new int[n];
    Arrays.fill(parents, -1);
    for (Pair pair : pairs) {
      int root1 = findRoot(parents, pair.u);
      int root2 = findRoot(parents, pair.v);
      if (root1 != root2) {
        parents[root2] = root1;
        result += A[pair.u][pair.v];
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

class Pair {
  int u;
  int v;

  Pair(int u, int v) {
    this.u = u;
    this.v = v;
  }
}