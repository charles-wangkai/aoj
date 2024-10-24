import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class Main {
  static final int SIZE = 4;
  static final char[] COLORS = {'R', 'G', 'B'};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int[] H = new int[N];
    int[] W = new int[N];
    for (int i = 0; i < N; ++i) {
      H[i] = sc.nextInt();
      W[i] = sc.nextInt();
    }
    char[][] grid = new char[SIZE][SIZE];
    for (int r = 0; r < SIZE; ++r) {
      String line = sc.next();
      for (int c = 0; c < SIZE; ++c) {
        grid[r][c] = line.charAt(c);
      }
    }

    System.out.println(solve(H, W, grid));

    sc.close();
  }

  static int solve(int[] H, int[] W, char[][] grid) {
    int[] distances = new int[1 << (SIZE * SIZE)];
    Arrays.fill(distances, -1);
    distances[0] = 0;

    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(0);

    while (!queue.isEmpty()) {
      int head = queue.poll();
      for (int i = 0; i < H.length; ++i) {
        for (int beginR = 1 - H[i]; beginR < SIZE; ++beginR) {
          for (int beginC = 1 - W[i]; beginC < SIZE; ++beginC) {
            for (char color : COLORS) {
              int nextState = computeNextState(grid, head, beginR, H[i], beginC, W[i], color);
              if (distances[nextState] == -1) {
                distances[nextState] = distances[head] + 1;
                queue.offer(nextState);
              }
            }
          }
        }
      }
    }

    return distances[distances.length - 1];
  }

  static int computeNextState(
      char[][] grid, int state, int beginR, int height, int beginC, int width, char color) {
    int result = 0;
    for (int i = 0; i < SIZE * SIZE; ++i) {
      int r = i / SIZE;
      int c = i % SIZE;

      int bit;
      if (r >= beginR && r < beginR + height && c >= beginC && c < beginC + width) {
        bit = (color == grid[r][c]) ? 1 : 0;
      } else {
        bit = (state >> i) & 1;
      }

      result += bit << i;
    }

    return result;
  }
}