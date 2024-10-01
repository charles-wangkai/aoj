import java.util.Arrays;
import java.util.Scanner;

public class Main {
  static final int LIMIT = 1_002_001;
  static final int[] R_OFFSETS = {0, -1, 0, 1};
  static final int[] C_OFFSETS = {1, 0, -1, 0};

  static boolean[] primes;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    while (true) {
      int m = sc.nextInt();
      int n = sc.nextInt();
      if (m == 0 && n == 0) {
        break;
      }

      System.out.println(solve(m, n));
    }

    sc.close();
  }

  static void precompute() {
    primes = new boolean[LIMIT + 1];
    Arrays.fill(primes, true);
    primes[1] = false;
    for (int i = 2; i < primes.length; ++i) {
      if (primes[i]) {
        for (int j = i * 2; j < primes.length; j += i) {
          primes[j] = false;
        }
      }
    }
  }

  static String solve(int m, int n) {
    int size = 1;
    while (size * size < m) {
      size += 2;
    }

    int[][] caves = buildCaves(size);

    int maxCaveNum = 0;
    int lastCave = 0;
    int[][] dp = new int[size][size];
    for (int r = 0; r < size; ++r) {
      for (int c = 0; c < size; ++c) {
        if (caves[r][c] == n) {
          dp[r][c] = primes[caves[r][c]] ? 1 : 0;
        } else if (caves[r][c] > m) {
          dp[r][c] = -1;
        } else {
          int prevMax =
              Math.max(
                  Math.max(getValue(dp, r - 1, c - 1), getValue(dp, r - 1, c)),
                  getValue(dp, r - 1, c + 1));
          dp[r][c] = (prevMax == -1) ? -1 : (prevMax + (primes[caves[r][c]] ? 1 : 0));
        }

        if (primes[caves[r][c]]) {
          if (dp[r][c] > maxCaveNum) {
            maxCaveNum = dp[r][c];
            lastCave = caves[r][c];
          } else if (dp[r][c] == maxCaveNum) {
            lastCave = Math.max(lastCave, caves[r][c]);
          }
        }
      }
    }

    return String.format("%d %d", maxCaveNum, lastCave);
  }

  static int getValue(int[][] dp, int r, int c) {
    return (r >= 0 && c >= 0 && c < dp[0].length) ? dp[r][c] : -1;
  }

  static int[][] buildCaves(int size) {
    int[][] result = new int[size][size];
    int r = size / 2;
    int c = size / 2;
    int direction = 0;
    int count = 0;
    int step = 1;
    boolean first = true;
    for (int i = 1; i <= size * size; ++i) {
      result[r][c] = i;

      r += R_OFFSETS[direction];
      c += C_OFFSETS[direction];

      ++count;
      if (count == step) {
        count = 0;
        direction = (direction + 1) % R_OFFSETS.length;

        if (!first) {
          ++step;
        }
        first ^= true;
      }
    }

    return result;
  }
}