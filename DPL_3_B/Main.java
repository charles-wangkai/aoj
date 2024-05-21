import java.util.ArrayDeque;
import java.util.Deque;
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

    int result = 0;
    int[] heights = new int[W];
    for (int r = 0; r < H; ++r) {
      for (int c = 0; c < heights.length; ++c) {
        if (a[r][c] == 0) {
          ++heights[c];
        } else {
          heights[c] = 0;
        }
      }

      result = Math.max(result, computeLargestRectangleInHistogram(heights));
    }

    return result;
  }

  static int computeLargestRectangleInHistogram(int[] heights) {
    int result = 0;
    Deque<Integer> indices = new ArrayDeque<>();
    for (int i = 0; i <= heights.length; ++i) {
      while (!indices.isEmpty() && (i == heights.length || heights[i] <= heights[indices.peek()])) {
        int h = heights[indices.pop()];
        result = Math.max(result, h * (i - (indices.isEmpty() ? -1 : indices.peek()) - 1));
      }

      indices.push(i);
    }

    return result;
  }
}