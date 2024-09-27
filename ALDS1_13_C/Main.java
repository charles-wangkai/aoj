// https://en.wikipedia.org/wiki/Iterative_deepening_A*

import java.util.Scanner;

public class Main {
  static final int SIZE = 4;
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
    State state = new State(puzzle);

    int result = state.totalDistance;
    while (!search(state, result, -R_OFFSETS.length)) {
      ++result;
    }

    return result;
  }

  static boolean search(State state, int rest, int lastDirection) {
    if (state.totalDistance == 0) {
      return true;
    }
    if (state.totalDistance > rest) {
      return false;
    }

    for (int i = 0; i < R_OFFSETS.length; ++i) {
      int spaceR = state.spaceR;
      int spaceC = state.spaceC;
      int adjR = spaceR + R_OFFSETS[i];
      int adjC = spaceC + C_OFFSETS[i];
      if (adjR >= 0
          && adjR < SIZE
          && adjC >= 0
          && adjC < SIZE
          && Math.abs(i - lastDirection) != 2) {
        state.move(adjR, adjC);

        boolean subResult = search(state, rest - 1, i);
        state.move(spaceR, spaceC);

        if (subResult) {
          return true;
        }
      }
    }

    return false;
  }

  static class State {
    int[][] puzzle;
    int spaceR;
    int spaceC;
    int totalDistance;

    State(int[][] puzzle) {
      this.puzzle = puzzle;

      for (int r = 0; r < SIZE; ++r) {
        for (int c = 0; c < SIZE; ++c) {
          if (puzzle[r][c] == 0) {
            spaceR = r;
            spaceC = c;
          } else {
            totalDistance += computeDistance(r, c);
          }
        }
      }
    }

    int computeDistance(int r, int c) {
      return Math.abs(r - (puzzle[r][c] - 1) / SIZE) + Math.abs(c - (puzzle[r][c] - 1) % SIZE);
    }

    void move(int adjR, int adjC) {
      totalDistance -= computeDistance(adjR, adjC);

      puzzle[spaceR][spaceC] = puzzle[adjR][adjC];
      puzzle[adjR][adjC] = 0;

      totalDistance += computeDistance(spaceR, spaceC);

      spaceR = adjR;
      spaceC = adjC;
    }
  }
}
