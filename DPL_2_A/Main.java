import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    int[] d = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
      d[i] = sc.nextInt();
    }

    System.out.println(solve(V, s, t, d));

    sc.close();
  }

  static int solve(int V, int[] s, int[] t, int[] d) {
    @SuppressWarnings("unchecked")
    List<Integer>[] edgeLists = new List[V];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < s.length; ++i) {
      edgeLists[s[i]].add(i);
    }

    int[][] dp = new int[1 << V][V];
    for (int mask = 0; mask < dp.length; ++mask) {
      Arrays.fill(dp[mask], Integer.MAX_VALUE);
    }
    dp[1][0] = 0;

    int result = Integer.MAX_VALUE;
    for (int mask = 1; mask < dp.length; ++mask) {
      for (int last = 0; last < V; ++last) {
        if (dp[mask][last] != Integer.MAX_VALUE) {
          for (int edge : edgeLists[last]) {
            if (((mask >> t[edge]) & 1) == 0) {
              int nextMask = mask + (1 << t[edge]);
              dp[nextMask][t[edge]] = Math.min(dp[nextMask][t[edge]], dp[mask][last] + d[edge]);
            } else if (mask == (1 << V) - 1 && t[edge] == 0) {
              result = Math.min(result, dp[mask][last] + d[edge]);
            }
          }
        }
      }
    }

    return (result == Integer.MAX_VALUE) ? -1 : result;
  }
}