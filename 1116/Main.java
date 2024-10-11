import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int SIZE = 3;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    for (int tc = 0; tc < N; ++tc) {
      String[] pieces = new String[SIZE * SIZE];
      for (int i = 0; i < pieces.length; ++i) {
        pieces[i] = sc.next();
      }

      System.out.println(solve(pieces));
    }

    sc.close();
  }

  static int solve(String[] pieces) {
    return search1(pieces, IntStream.range(0, pieces.length).toArray(), 0) * 4;
  }

  static int search1(String[] pieces, int[] indices, int index) {
    if (index == indices.length) {
      return (Math.min(
                  Math.min(indices[SIZE - 1], indices[indices.length - 1]),
                  indices[indices.length - SIZE])
              > indices[0])
          ? search2(Arrays.stream(indices).mapToObj(i -> pieces[i]).toArray(String[]::new), 0, 0)
          : 0;
    }

    int result = 0;
    for (int i = index; i < indices.length; ++i) {
      swap(indices, i, index);
      result += search1(pieces, indices, index + 1);
      swap(indices, i, index);
    }

    return result;
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }

  static int search2(String[] pieces, int r, int c) {
    if (r == SIZE) {
      return 1;
    }
    if (c == SIZE) {
      return search2(pieces, r + 1, 0);
    }

    int result = 0;
    for (int i = 0; i < 4; ++i) {
      if (isMatched(pieces, r, c)) {
        result += search2(pieces, r, c + 1);
      }

      pieces[r * SIZE + c] = rotate(pieces[r * SIZE + c]);
    }

    return result;
  }

  static String rotate(String piece) {
    return piece.substring(1) + piece.charAt(0);
  }

  static boolean isMatched(String[] pieces, int r, int c) {
    return (r == 0 || canFace(pieces[r * SIZE + c].charAt(0), pieces[(r - 1) * SIZE + c].charAt(2)))
        && (c == 0
            || canFace(pieces[r * SIZE + c].charAt(3), pieces[r * SIZE + (c - 1)].charAt(1)));
  }

  static boolean canFace(char symbol1, char symbol2) {
    return symbol1 != symbol2 && Character.toLowerCase(symbol1) == Character.toLowerCase(symbol2);
  }
}