import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int W = sc.nextInt();
    int[] v = new int[N];
    int[] w = new int[N];
    for (int i = 0; i < N; ++i) {
      v[i] = sc.nextInt();
      w[i] = sc.nextInt();
    }

    System.out.println(solve(v, w, W));

    sc.close();
  }

  static int solve(int[] v, int[] w, int W) {
    int[] dp = new int[W + 1];
    Arrays.fill(dp, -1);
    dp[0] = 0;
    for (int i = 0; i < v.length; ++i) {
      for (int j = 0; j <= W; ++j) {
        if (dp[j] != -1 && j + w[i] <= W) {
          dp[j + w[i]] = Math.max(dp[j + w[i]], dp[j] + v[i]);
        }
      }
    }

    return Arrays.stream(dp).max().getAsInt();
  }
}