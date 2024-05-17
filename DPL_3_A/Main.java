import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int H = sc.nextInt();
    int W = sc.nextInt();
    int[][] a = new int[H][W];
    for (int r = 0; r < H; ++r) {
      for (int c = 0; c < W; ++c) {
        a[r][c] = sc.nextInt();
      }
    }

    System.out.println(solve(a));

    sc.close();
  }

  static int solve(int[][] a) {
    int H = a.length;
    int W = a[0].length;

    int[][] prefixSums = new int[H + 1][W + 1];
    for (int r = 1; r <= H; ++r) {
      for (int c = 1; c <= W; ++c) {
        prefixSums[r][c] =
            prefixSums[r - 1][c]
                + prefixSums[r][c - 1]
                - prefixSums[r - 1][c - 1]
                + a[r - 1][c - 1];
      }
    }

    int result = 0;
    for (int r = 1; r <= H; ++r) {
      for (int c = 1; c <= W; ++c) {
        result = Math.max(result, computeMaxSquareArea(prefixSums, r, c));
      }
    }

    return result;
  }

  static int computeMaxSquareArea(int[][] prefixSums, int maxR, int maxC) {
    int result = -1;
    int lower = 1;
    int upper = Math.min(maxR, maxC);
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (computeRangeSum(prefixSums, maxR - middle + 1, maxC - middle + 1, maxR, maxC) == 0) {
        result = middle * middle;
        lower = middle + 1;
      } else {
        upper = middle - 1;
      }
    }

    return result;
  }

  static int computeRangeSum(int[][] prefixSums, int minR, int minC, int maxR, int maxC) {
    return prefixSums[maxR][maxC]
        - prefixSums[minR - 1][maxC]
        - prefixSums[maxR][minC - 1]
        + prefixSums[minR - 1][minC - 1];
  }
}