import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int M = sc.nextInt();
      int T = sc.nextInt();
      int P = sc.nextInt();
      int R = sc.nextInt();
      if (M == 0 && T == 0 && P == 0 && R == 0) {
        break;
      }

      int[] m = new int[R];
      int[] t = new int[R];
      int[] p = new int[R];
      int[] j = new int[R];
      for (int i = 0; i < R; ++i) {
        m[i] = sc.nextInt();
        t[i] = sc.nextInt();
        p[i] = sc.nextInt();
        j[i] = sc.nextInt();
      }

      System.out.println(solve(T, P, m, t, p, j));
    }

    sc.close();
  }

  static String solve(int T, int P, int[] m, int[] t, int[] p, int[] j) {
    int[][] rejectedCounts = new int[T][P];
    int[] solvedCounts = new int[T];
    int[] penalties = new int[T];
    for (int i = 0; i < m.length; ++i) {
      if (j[i] == 0) {
        ++solvedCounts[t[i] - 1];
        penalties[t[i] - 1] += m[i] + 20 * rejectedCounts[t[i] - 1][p[i] - 1];
      } else {
        ++rejectedCounts[t[i] - 1][p[i] - 1];
      }
    }

    int[] sortedIndices =
        IntStream.range(0, T)
            .boxed()
            .sorted(
                Comparator.<Integer, Integer>comparing(i -> solvedCounts[i])
                    .reversed()
                    .thenComparing(i -> penalties[i])
                    .thenComparing(Comparator.<Integer, Integer>comparing(i -> i).reversed()))
            .mapToInt(Integer::intValue)
            .toArray();

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < sortedIndices.length; ++i) {
      if (i != 0) {
        result.append(
            (solvedCounts[sortedIndices[i]] == solvedCounts[sortedIndices[i - 1]]
                    && penalties[sortedIndices[i]] == penalties[sortedIndices[i - 1]])
                ? '='
                : ',');
      }
      result.append(sortedIndices[i] + 1);
    }

    return result.toString();
  }
}