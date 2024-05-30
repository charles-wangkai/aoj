import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    String s1 = sc.next();
    String s2 = sc.next();

    System.out.println(solve(s1, s2));

    sc.close();
  }

  static int solve(String s1, String s2) {
    int[][] dp = new int[s1.length() + 1][s2.length() + 1];
    for (int i = 0; i <= s1.length(); ++i) {
      for (int j = 0; j <= s2.length(); ++j) {
        if (i == 0) {
          dp[i][j] = j;
        } else if (j == 0) {
          dp[i][j] = i;
        } else {
          dp[i][j] =
              Math.min(
                  Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                  dp[i - 1][j - 1] + ((s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1));
        }
      }
    }

    return dp[s1.length()][s2.length()];
  }
}