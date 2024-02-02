import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      String X = sc.next();
      String Y = sc.next();

      System.out.println(solve(X, Y));
    }

    sc.close();
  }

  static int solve(String X, String Y) {
    int[][] dp = new int[X.length() + 1][Y.length() + 1];
    for (int i = 0; i <= X.length(); ++i) {
      for (int j = 0; j <= Y.length(); ++j) {
        if (i != 0 && j != 0) {
          dp[i][j] =
              (X.charAt(i - 1) == Y.charAt(j - 1))
                  ? (dp[i - 1][j - 1] + 1)
                  : Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    return dp[X.length()][Y.length()];
  }
}