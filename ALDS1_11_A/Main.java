import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] u = new int[n];
    int[][] v = new int[n][];
    for (int i = 0; i < n; ++i) {
      u[i] = sc.nextInt();
      int k = sc.nextInt();
      v[i] = new int[k];
      for (int j = 0; j < v[i].length; ++j) {
        v[i][j] = sc.nextInt();
      }
    }

    System.out.println(solve(u, v));

    sc.close();
  }

  static String solve(int[] u, int[][] v) {
    int n = u.length;

    boolean[][] matrix = new boolean[n][n];
    for (int i = 0; i < u.length; ++i) {
      for (int vi : v[i]) {
        matrix[u[i] - 1][vi - 1] = true;
      }
    }

    return IntStream.range(0, n)
        .mapToObj(
            i ->
                IntStream.range(0, n)
                    .map(j -> matrix[i][j] ? 1 : 0)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(" ")))
        .collect(Collectors.joining("\n"));
  }
}