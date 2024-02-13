import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    int[] d = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
      d[i] = sc.nextInt();
    }

    System.out.println(solve(V, s, t, d));

    sc.close();
  }

  static String solve(int V, int[] s, int[] t, int[] d) {
    int[][] distances = new int[V][V];
    for (int i = 0; i < V; ++i) {
      for (int j = 0; j < V; ++j) {
        distances[i][j] = (j == i) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < s.length; ++i) {
      distances[s[i]][t[i]] = d[i];
    }

    for (int k = 0; k < V; ++k) {
      for (int i = 0; i < V; ++i) {
        for (int j = 0; j < V; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    if (IntStream.range(0, V).anyMatch(i -> distances[i][i] < 0)) {
      return "NEGATIVE CYCLE";
    }

    return Arrays.stream(distances)
        .map(
            dist ->
                Arrays.stream(dist)
                    .mapToObj(x -> (x == Integer.MAX_VALUE) ? "INF" : String.valueOf(x))
                    .collect(Collectors.joining(" ")))
        .collect(Collectors.joining("\n"));
  }
}