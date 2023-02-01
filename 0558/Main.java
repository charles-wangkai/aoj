import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int H = sc.nextInt();
    int W = sc.nextInt();
    int N = sc.nextInt();
    char[][] grid = new char[H][W];
    for (int r = 0; r < H; ++r) {
      String line = sc.next();
      for (int c = 0; c < W; ++c) {
        grid[r][c] = line.charAt(c);
      }
    }

    System.out.println(solve(grid, N));

    sc.close();
  }

  static int solve(char[][] grid, int N) {
    return IntStream.rangeClosed(1, N)
        .map(i -> computeDistance(grid, (char) ((i == 1) ? 'S' : (i - 1 + '0')), (char) (i + '0')))
        .sum();
  }

  static int computeDistance(char[][] grid, char beginValue, char endValue) {
    int H = grid.length;
    int W = grid[0].length;

    int beginR = -1;
    int beginC = -1;
    int endR = -1;
    int endC = -1;
    for (int r = 0; r < H; ++r) {
      for (int c = 0; c < W; ++c) {
        if (grid[r][c] == beginValue) {
          beginR = r;
          beginC = c;
        } else if (grid[r][c] == endValue) {
          endR = r;
          endC = c;
        }
      }
    }

    int[][] distances = new int[H][W];
    for (int r = 0; r < distances.length; ++r) {
      Arrays.fill(distances[r], -1);
    }
    distances[beginR][beginC] = 0;
    Queue<Point> queue = new ArrayDeque<>();
    queue.offer(new Point(beginR, beginC));
    while (!queue.isEmpty()) {
      Point head = queue.poll();
      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];
        if (adjR >= 0
            && adjR < H
            && adjC >= 0
            && adjC < W
            && grid[adjR][adjC] != 'X'
            && distances[adjR][adjC] == -1) {
          distances[adjR][adjC] = distances[head.r][head.c] + 1;
          queue.offer(new Point(adjR, adjC));
        }
      }
    }

    return distances[endR][endC];
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
