import java.util.Scanner;
import java.util.Stack;

public class Main {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (sc.hasNextInt()) {
      int H = sc.nextInt();
      int W = sc.nextInt();
      if (H == 0) {
        break;
      }

      char[][] grid = new char[H][W];
      for (int r = 0; r < H; ++r) {
        String line = sc.next();
        for (int c = 0; c < W; ++c) {
          grid[r][c] = line.charAt(c);
        }
      }

      System.out.println(solve(grid));
    }

    sc.close();
  }

  static int solve(char[][] grid) {
    int H = grid.length;
    int W = grid[0].length;

    int result = 0;
    boolean[][] visited = new boolean[H][W];
    for (int r = 0; r < H; ++r) {
      for (int c = 0; c < W; ++c) {
        if (!visited[r][c]) {
          search(grid, visited, r, c);
          ++result;
        }
      }
    }

    return result;
  }

  static void search(char[][] grid, boolean[][] visited, int r, int c) {
    int H = grid.length;
    int W = grid[0].length;

    Stack<Point> stack = new Stack<>();
    stack.push(new Point(r, c));
    while (!stack.empty()) {
      Point point = stack.pop();

      visited[point.r][point.c] = true;
      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = point.r + R_OFFSETS[i];
        int adjC = point.c + C_OFFSETS[i];
        if (adjR >= 0
            && adjR < H
            && adjC >= 0
            && adjC < W
            && grid[adjR][adjC] == grid[point.r][point.c]
            && !visited[adjR][adjC]) {
          stack.push(new Point(adjR, adjC));
        }
      }
    }
  }
}

class Point {
  int r;
  int c;

  Point(int r, int c) {
    this.r = r;
    this.c = c;
  }
}
