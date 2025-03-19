// https://www.hankcs.com/program/algorithm/aoj-2164-revenge-of-the-round-table.html

import java.math.BigInteger;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int MODULUS = 1_000_003;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      int k = sc.nextInt();
      if (n == 0 && k == 0) {
        break;
      }

      System.out.println(solve(n, k));
    }

    sc.close();
  }

  static int solve(int n, int k) {
    int[] dp = buildDp(n, k);

    int result =
        IntStream.rangeClosed(1, n)
            .map(i -> multiplyMod(2, dp[gcd(i, n)]))
            .reduce(Main::addMod)
            .getAsInt();
    result = divideMod(result, n);
    if (k >= n) {
      result = addMod(result, 2);
    }

    return result;
  }

  static int[] buildDp(int n, int k) {
    k = Math.min(k, n - 1);

    int[][] aDp = new int[n + 1][n + 1];
    aDp[1][1] = 1;

    int[][] bDp = new int[n + 1][n + 1];

    int aSum = 1;
    int bSum = 0;

    int[] dp = new int[n + 1];

    for (int i = 2; i <= n; ++i) {
      aDp[i][1] = bSum;
      bDp[i][1] = aSum;

      int temp = aSum;
      aSum = bSum;
      bSum = temp;

      for (int j = 2; j <= k; ++j) {
        aDp[i][j] = aDp[i - 1][j - 1];
        aSum = addMod(aSum, aDp[i][j]);

        bDp[i][j] = bDp[i - 1][j - 1];
        bSum = addMod(bSum, bDp[i][j]);
      }
    }

    for (int i = 1; i <= n; ++i) {
      for (int j = 1; j <= k; ++j) {
        dp[i] = addMod(dp[i], bDp[i][j]);
      }
    }

    for (int i = 1; i <= n; ++i) {
      for (int j = 2; j <= k; ++j) {
        bDp[i][j] = addMod(bDp[i][j], bDp[i][j - 1]);
      }
    }

    for (int i = 1; i <= n; ++i) {
      for (int p = 1; p < i && p < k; ++p) {
        dp[i] = addMod(dp[i], bDp[i - p][k - p]);
      }
    }

    return dp;
  }

  static int gcd(int x, int y) {
    return (y == 0) ? x : gcd(y, x % y);
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }

  static int divideMod(int x, int y) {
    return multiplyMod(x, BigInteger.valueOf(y).modInverse(BigInteger.valueOf(MODULUS)).intValue());
  }
}