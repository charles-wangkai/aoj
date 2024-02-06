import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int m = sc.nextInt();
    int[] s = new int[m];
    int[] t = new int[m];
    for (int i = 0; i < m; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
    }
    int q = sc.nextInt();
    int[] u = new int[q];
    int[] v = new int[q];
    for (int i = 0; i < q; ++i) {
      u[i] = sc.nextInt();
      v[i] = sc.nextInt();
    }

    System.out.println(solve(n, s, t, u, v));

    sc.close();
  }

  static String solve(int n, int[] s, int[] t, int[] u, int[] v) {
    int[] parents = new int[n];
    Arrays.fill(parents, -1);

    for (int i = 0; i < s.length; ++i) {
      int root1 = findRoot(parents, s[i]);
      int root2 = findRoot(parents, t[i]);
      if (root1 != root2) {
        parents[root2] = root1;
      }
    }

    return IntStream.range(0, u.length)
        .mapToObj(i -> (findRoot(parents, u[i]) == findRoot(parents, v[i])) ? "yes" : "no")
        .collect(Collectors.joining("\n"));
  }

  static int findRoot(int[] parents, int node) {
    if (parents[node] == -1) {
      return node;
    }

    parents[node] = findRoot(parents, parents[node]);

    return parents[node];
  }
}