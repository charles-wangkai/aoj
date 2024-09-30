import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      String s = sc.next();
      if (s.equals("#END")) {
        break;
      }

      System.out.println(solve(s));
    }

    sc.close();
  }

  static String solve(String s) {
    String result = "";
    for (int i = 1; i < s.length(); ++i) {
      String lcs = findLcs(s.substring(0, i), s.substring(i));
      if (lcs.length() > result.length()) {
        result = lcs;
      }
    }

    return result;
  }

  static String findLcs(String s1, String s2) {
    int[][] dp = new int[s1.length() + 1][s2.length() + 1];
    for (int i = 1; i <= s1.length(); ++i) {
      for (int j = 1; j <= s2.length(); ++j) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          dp[i][j] = 1 + dp[i - 1][j - 1];
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    StringBuilder result = new StringBuilder();
    int i = s1.length();
    int j = s2.length();
    while (dp[i][j] != 0) {
      if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
        result.append(s1.charAt(i - 1));

        --i;
        --j;
      } else if (dp[i][j] == dp[i - 1][j]) {
        --i;
      } else {
        --j;
      }
    }
    result.reverse();

    return result.toString();
  }
}