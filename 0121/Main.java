import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  static final int ROW = 2;
  static final int COL = 4;
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  static Map<String, Integer> stateToMoveNum;

  public static void main(String[] args) {
    preprocess();

    Scanner sc = new Scanner(System.in);

    while (sc.hasNextInt()) {
      int[] puzzle = new int[ROW * COL];
      for (int i = 0; i < puzzle.length; ++i) {
        puzzle[i] = sc.nextInt();
      }

      System.out.println(solve(puzzle));
    }

    sc.close();
  }

  static void preprocess() {
    stateToMoveNum = new HashMap<>();
    stateToMoveNum.put("01234567", 0);
    Queue<String> queue = new ArrayDeque<>();
    queue.offer("01234567");
    while (!queue.isEmpty()) {
      String state = queue.poll();

      char[][] grid = new char[ROW][COL];
      int zeroR = -1;
      int zeroC = -1;
      for (int r = 0; r < grid.length; ++r) {
        for (int c = 0; c < grid[r].length; ++c) {
          grid[r][c] = state.charAt(r * COL + c);

          if (grid[r][c] == '0') {
            zeroR = r;
            zeroC = c;
          }
        }
      }

      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = zeroR + R_OFFSETS[i];
        int adjC = zeroC + C_OFFSETS[i];
        if (adjR >= 0 && adjR < ROW && adjC >= 0 && adjC < COL) {
          swap(grid, zeroR, zeroC, adjR, adjC);

          String nextState = Arrays.stream(grid).map(String::new).collect(Collectors.joining());
          if (!stateToMoveNum.containsKey(nextState)) {
            stateToMoveNum.put(nextState, stateToMoveNum.get(state) + 1);
            queue.offer(nextState);
          }

          swap(grid, zeroR, zeroC, adjR, adjC);
        }
      }
    }
  }

  static void swap(char[][] grid, int r1, int c1, int r2, int c2) {
    char temp = grid[r1][c1];
    grid[r1][c1] = grid[r2][c2];
    grid[r2][c2] = temp;
  }

  static int solve(int[] puzzle) {
    return stateToMoveNum.get(
        Arrays.stream(puzzle).mapToObj(String::valueOf).collect(Collectors.joining()));
  }
}
