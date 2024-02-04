import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static int timestamp;

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

    int[][] adjLists = new int[n][];
    for (int i = 0; i < u.length; ++i) {
      adjLists[u[i] - 1] = new int[v[i].length];
      for (int j = 0; j < adjLists[u[i] - 1].length; ++j) {
        adjLists[u[i] - 1][j] = v[i][j] - 1;
      }
    }

    timestamp = 0;
    int[] d = new int[n];
    int[] f = new int[n];
    for (int i = 0; i < n; ++i) {
      if (d[i] == 0) {
        search(d, f, adjLists, i);
      }
    }

    return IntStream.range(0, n)
        .mapToObj(i -> String.format("%d %d %d", i + 1, d[i], f[i]))
        .collect(Collectors.joining("\n"));
  }

  static void search(int[] d, int[] f, int[][] adjLists, int node) {
    ++timestamp;
    d[node] = timestamp;

    for (int adj : adjLists[node]) {
      if (d[adj] == 0) {
        search(d, f, adjLists, adj);
      }
    }

    ++timestamp;
    f[node] = timestamp;
  }
}