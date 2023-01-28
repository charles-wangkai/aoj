import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int R = sc.nextInt();
      int C = sc.nextInt();
      if (R == 0) {
        break;
      }

      int[][] a = new int[R][C];
      for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
          a[r][c] = sc.nextInt();
        }
      }

      System.out.println(solve(a));
    }

    sc.close();
  }

  static int solve(int[][] a) {
    int R = a.length;
    int C = a[0].length;

    int result = -1;
    for (int mask = 0; mask < 1 << R; ++mask) {
      int[][] flipped = new int[R][C];
      for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
          flipped[r][c] = (((mask >> r) & 1) == 1) ? (1 - a[r][c]) : a[r][c];
        }
      }

      result =
          Math.max(
              result,
              IntStream.range(0, C)
                  .map(
                      c -> {
                        int zeroCount =
                            (int) IntStream.range(0, R).filter(r -> flipped[r][c] == 0).count();

                        return Math.max(zeroCount, R - zeroCount);
                      })
                  .sum());
    }

    return result;
  }
}
