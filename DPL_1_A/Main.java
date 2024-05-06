import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int m = sc.nextInt();
    int[] d = new int[m];
    for (int i = 0; i < d.length; ++i) {
      d[i] = sc.nextInt();
    }

    System.out.println(solve(n, d));

    sc.close();
  }

  static int solve(int n, int[] d) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    for (int i = 0; i < dp.length; ++i) {
      for (int di : d) {
        if (i + di < dp.length) {
          dp[i + di] = Math.min(dp[i + di], dp[i] + 1);
        }
      }
    }

    return dp[n];
  }
}