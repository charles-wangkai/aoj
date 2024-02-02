import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] r = new int[n];
    int[] c = new int[n];
    for (int i = 0; i < n; ++i) {
      r[i] = sc.nextInt();
      c[i] = sc.nextInt();
    }

    System.out.println(solve(r, c));

    sc.close();
  }

  static int solve(int[] r, int[] c) {
    int n = r.length;

    int[][] dp = new int[n][n];
    for (int i = 0; i < dp.length; ++i) {
      for (int j = i; j < dp.length; ++j) {
        dp[i][j] = (j == i) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int length = 2; length <= n; ++length) {
      for (int beginIndex = 0; beginIndex + length - 1 < n; ++beginIndex) {
        int endIndex = beginIndex + length - 1;
        for (int middleIndex = beginIndex; middleIndex < endIndex; ++middleIndex) {
          dp[beginIndex][endIndex] =
              Math.min(
                  dp[beginIndex][endIndex],
                  dp[beginIndex][middleIndex]
                      + dp[middleIndex + 1][endIndex]
                      + r[beginIndex] * c[middleIndex] * c[endIndex]);
        }
      }
    }

    return dp[0][n - 1];
  }
}