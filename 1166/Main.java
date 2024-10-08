import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class Main {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int w = sc.nextInt();
      int h = sc.nextInt();
      if (w == 0 && h == 0) {
        break;
      }

      int[][] walls = new int[2 * h - 1][];
      for (int i = 0; i < walls.length; ++i) {
        walls[i] = new int[(i % 2 == 0) ? (w - 1) : w];
        for (int j = 0; j < walls[i].length; ++j) {
          walls[i][j] = sc.nextInt();
        }
      }

      System.out.println(solve(w, h, walls));
    }

    sc.close();
  }

  static int solve(int w, int h, int[][] walls) {
    int[][] distances = new int[h][w];
    for (int r = 0; r < distances.length; ++r) {
      Arrays.fill(distances[r], Integer.MAX_VALUE);
    }
    distances[0][0] = 1;

    Queue<Point> queue = new ArrayDeque<>();
    queue.offer(new Point(0, 0));
    while (!queue.isEmpty()) {
      Point head = queue.poll();
      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];
        if (adjR >= 0
            && adjR < h
            && adjC >= 0
            && adjC < w
            && distances[adjR][adjC] == Integer.MAX_VALUE
            && isConnected(walls, head.r, head.c, adjR, adjC)) {
          distances[adjR][adjC] = distances[head.r][head.c] + 1;
          queue.offer(new Point(adjR, adjC));
        }
      }
    }

    return (distances[h - 1][w - 1] == Integer.MAX_VALUE) ? 0 : distances[h - 1][w - 1];
  }

  static boolean isConnected(int[][] walls, int r1, int c1, int r2, int c2) {
    return ((r1 == r2)
            ? walls[r1 * 2][c1 + ((c2 > c1) ? 0 : -1)]
            : walls[(r1 + ((r2 > r1) ? 0 : -1)) * 2 + 1][c1])
        == 0;
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