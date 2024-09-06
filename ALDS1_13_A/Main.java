import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
  static final int SIZE = 8;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int k = sc.nextInt();
    int[] r = new int[k];
    int[] c = new int[k];
    for (int i = 0; i < k; ++i) {
      r[i] = sc.nextInt();
      c[i] = sc.nextInt();
    }

    System.out.println(solve(r, c));

    sc.close();
  }

  static String solve(int[] r, int[] c) {
    char[][] board = new char[SIZE][SIZE];
    for (int row = 0; row < board.length; ++row) {
      Arrays.fill(board[row], '.');
    }
    Set<Integer> rows = new HashSet<>();
    Set<Integer> cols = new HashSet<>();
    Set<Integer> rcSums = new HashSet<>();
    Set<Integer> rcDiffs = new HashSet<>();
    for (int i = 0; i < r.length; ++i) {
      fill(board, rows, cols, rcSums, rcDiffs, r[i], c[i]);
    }

    search(board, rows, cols, rcSums, rcDiffs, 0);

    return Arrays.stream(board).map(String::new).collect(Collectors.joining("\n"));
  }

  static boolean search(
      char[][] board,
      Set<Integer> rows,
      Set<Integer> cols,
      Set<Integer> rcSums,
      Set<Integer> rcDiffs,
      int row) {
    if (row == SIZE) {
      return true;
    }
    if (rows.contains(row)) {
      return search(board, rows, cols, rcSums, rcDiffs, row + 1);
    }

    for (int col = 0; col < SIZE; ++col) {
      if (check(rows, cols, rcSums, rcDiffs, row, col)) {
        fill(board, rows, cols, rcSums, rcDiffs, row, col);

        if (search(board, rows, cols, rcSums, rcDiffs, row + 1)) {
          return true;
        }

        unfill(board, rows, cols, rcSums, rcDiffs, row, col);
      }
    }

    return false;
  }

  static boolean check(
      Set<Integer> rows,
      Set<Integer> cols,
      Set<Integer> rcSums,
      Set<Integer> rcDiffs,
      int row,
      int col) {
    return !rows.contains(row)
        && !cols.contains(col)
        && !rcSums.contains(row + col)
        && !rcDiffs.contains(row - col);
  }

  static void fill(
      char[][] board,
      Set<Integer> rows,
      Set<Integer> cols,
      Set<Integer> rcSums,
      Set<Integer> rcDiffs,
      int row,
      int col) {
    board[row][col] = 'Q';
    rows.add(row);
    cols.add(col);
    rcSums.add(row + col);
    rcDiffs.add(row - col);
  }

  static void unfill(
      char[][] board,
      Set<Integer> rows,
      Set<Integer> cols,
      Set<Integer> rcSums,
      Set<Integer> rcDiffs,
      int row,
      int col) {
    board[row][col] = '.';
    rows.remove(row);
    cols.remove(col);
    rcSums.remove(row + col);
    rcDiffs.remove(row - col);
  }
}