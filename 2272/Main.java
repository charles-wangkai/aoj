import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int H = sc.nextInt();
    int W = sc.nextInt();
    int[][] blocks = new int[H][W];
    for (int r = 0; r < H; ++r) {
      String line = sc.next();
      for (int c = 0; c < W; ++c) {
        blocks[r][c] = line.charAt(c) - '0';
      }
    }

    System.out.println(solve(blocks));

    sc.close();
  }

  static int solve(int[][] blocks) {
    int H = blocks.length;
    int W = blocks[0].length;

    int[][] dp = new int[H][W];
    for (int r = 0; r < H; ++r) {
      for (int c = 0; c < W; ++c) {
        int prev = Integer.MAX_VALUE;
        if (r != 0) {
          prev = Math.min(prev, dp[r - 1][c]);
        }
        if (c != 0) {
          prev = Math.min(prev, dp[r][c - 1]);
        }
        if (prev == Integer.MAX_VALUE) {
          prev = 0;
        }

        dp[r][c] = prev + blocks[r][c];
      }
    }

    return dp[H - 1][W - 1];
  }
}