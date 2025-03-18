// https://www.hankcs.com/program/algorithm/aoj-2214-hall-warp.html

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  static final int MODULUS = 1_000_000_007;
  static final int LIMIT = 200000;

  static int[] factorials;
  static int[] factorialInvs;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int K = sc.nextInt();
      if (N == 0 && M == 0 && K == 0) {
        break;
      }

      int[] a = new int[K];
      int[] b = new int[K];
      int[] c = new int[K];
      int[] d = new int[K];
      for (int i = 0; i < K; ++i) {
        a[i] = sc.nextInt();
        b[i] = sc.nextInt();
        c[i] = sc.nextInt();
        d[i] = sc.nextInt();
      }

      System.out.println(solve(N, M, a, b, c, d));
    }

    sc.close();
  }

  static void precompute() {
    factorials = new int[LIMIT + 1];
    factorials[0] = 1;
    for (int i = 1; i < factorials.length; ++i) {
      factorials[i] = multiplyMod(factorials[i - 1], i);
    }

    factorialInvs =
        Arrays.stream(factorials)
            .map(
                factorial ->
                    BigInteger.valueOf(factorial)
                        .modInverse(BigInteger.valueOf(MODULUS))
                        .intValue())
            .toArray();
  }

  static int solve(int N, int M, int[] a, int[] b, int[] c, int[] d) {
    WarpHole[] warpHoles =
        Stream.concat(
                IntStream.range(0, a.length).mapToObj(i -> new WarpHole(a[i], b[i], c[i], d[i])),
                Stream.of(new WarpHole(N, M, Integer.MAX_VALUE, Integer.MAX_VALUE)))
            .sorted(
                Comparator.<WarpHole, Integer>comparing(warpHole -> warpHole.fromX)
                    .thenComparing(warpHole -> warpHole.fromY))
            .toArray(WarpHole[]::new);

    int[] dp = new int[warpHoles.length];
    for (int i = 0; i < dp.length; ++i) {
      dp[i] = computeWayNum(1, 1, warpHoles[i].fromX, warpHoles[i].fromY);
      for (int j = 0; j < i; ++j) {
        dp[i] =
            addMod(
                dp[i],
                multiplyMod(
                    dp[j],
                    addMod(
                        computeWayNum(
                            warpHoles[j].toX,
                            warpHoles[j].toY,
                            warpHoles[i].fromX,
                            warpHoles[i].fromY),
                        -computeWayNum(
                            warpHoles[j].fromX,
                            warpHoles[j].fromY,
                            warpHoles[i].fromX,
                            warpHoles[i].fromY))));
      }
    }

    return dp[dp.length - 1];
  }

  static int computeWayNum(int x1, int y1, int x2, int y2) {
    return (x1 <= x2 && y1 <= y2) ? CMod((x2 - x1) + (y2 - y1), x2 - x1) : 0;
  }

  static int addMod(int x, int y) {
    return Math.floorMod(x + y, MODULUS);
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }

  static int CMod(int n, int r) {
    return multiplyMod(factorials[n], multiplyMod(factorialInvs[r], factorialInvs[n - r]));
  }
}

class WarpHole {
  int fromX;
  int fromY;
  int toX;
  int toY;

  WarpHole(int fromX, int fromY, int toX, int toY) {
    this.fromX = fromX;
    this.fromY = fromY;
    this.toX = toX;
    this.toY = toY;
  }
}