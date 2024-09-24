import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Main {
  static final int SIZE = 3;
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int[][] puzzle = new int[SIZE][SIZE];
    for (int r = 0; r < SIZE; ++r) {
      for (int c = 0; c < SIZE; ++c) {
        puzzle[r][c] = sc.nextInt();
      }
    }

    System.out.println(solve(puzzle));

    sc.close();
  }

  static int solve(int[][] puzzle) {
    String state = toState(puzzle);

    Map<String, Integer> stateToDistance = new HashMap<>();
    stateToDistance.put(state, 0);

    Queue<String> queue = new ArrayDeque<>();
    queue.offer(state);

    while (!queue.isEmpty()) {
      String head = queue.poll();
      for (String nextState : findNextStates(head)) {
        if (!stateToDistance.containsKey(nextState)) {
          stateToDistance.put(nextState, stateToDistance.get(head) + 1);
          queue.offer(nextState);
        }
      }
    }

    return stateToDistance.get(toState(new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}));
  }

  static List<String> findNextStates(String state) {
    int[][] puzzle = new int[SIZE][SIZE];
    int spaceR = -1;
    int spaceC = -1;
    for (int r = 0; r < SIZE; ++r) {
      for (int c = 0; c < SIZE; ++c) {
        puzzle[r][c] = state.charAt(r * SIZE + c) - '0';

        if (puzzle[r][c] == 0) {
          spaceR = r;
          spaceC = c;
        }
      }
    }

    List<String> result = new ArrayList<>();
    for (int i = 0; i < R_OFFSETS.length; ++i) {
      int adjR = spaceR + R_OFFSETS[i];
      int adjC = spaceC + C_OFFSETS[i];
      if (adjR >= 0 && adjR < SIZE && adjC >= 0 && adjC < SIZE) {
        swap(puzzle, spaceR, spaceC, adjR, adjC);
        result.add(toState(puzzle));
        swap(puzzle, spaceR, spaceC, adjR, adjC);
      }
    }

    return result;
  }

  static void swap(int[][] puzzle, int r1, int c1, int r2, int c2) {
    int temp = puzzle[r1][c1];
    puzzle[r1][c1] = puzzle[r2][c2];
    puzzle[r2][c2] = temp;
  }

  static String toState(int[][] puzzle) {
    StringBuilder result = new StringBuilder();
    for (int[] line : puzzle) {
      for (int x : line) {
        result.append(x);
      }
    }

    return result.toString();
  }
}