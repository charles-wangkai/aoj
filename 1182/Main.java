import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      int m = sc.nextInt();
      int c = sc.nextInt();
      int s = sc.nextInt();
      int g = sc.nextInt();
      if (n == 0 && m == 0 && c == 0 && s == 0 && g == 0) {
        break;
      }

      int[] x = new int[m];
      int[] y = new int[m];
      int[] d = new int[m];
      int[] cs = new int[m];
      for (int i = 0; i < m; ++i) {
        x[i] = sc.nextInt();
        y[i] = sc.nextInt();
        d[i] = sc.nextInt();
        cs[i] = sc.nextInt();
      }
      int[] p = new int[c];
      for (int i = 0; i < p.length; ++i) {
        p[i] = sc.nextInt();
      }
      int[][] q = new int[c][];
      int[][] r = new int[c][];
      for (int i = 0; i < c; ++i) {
        q[i] = new int[p[i] - 1];
        for (int j = 0; j < q[i].length; ++j) {
          q[i][j] = sc.nextInt();
        }
        r[i] = new int[p[i]];
        for (int j = 0; j < r[i].length; ++j) {
          r[i][j] = sc.nextInt();
        }
      }

      System.out.println(solve(n, x, y, d, cs, p, q, r, s, g));
    }

    sc.close();
  }

  static int solve(
      int n, int[] x, int[] y, int[] d, int[] cs, int[] p, int[][] q, int[][] r, int s, int g) {
    int c = p.length;

    int[][] costs = initTable(n);
    for (int company = 0; company < c; ++company) {
      int[][] distances = initTable(n);
      for (int i = 0; i < x.length; ++i) {
        if (cs[i] == company + 1) {
          distances[x[i] - 1][y[i] - 1] = Math.min(distances[x[i] - 1][y[i] - 1], d[i]);
          distances[y[i] - 1][x[i] - 1] = Math.min(distances[y[i] - 1][x[i] - 1], d[i]);
        }
      }

      floyd(distances);

      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          costs[i][j] =
              Math.min(
                  costs[i][j], computeCost(p[company], q[company], r[company], distances[i][j]));
        }
      }
    }

    floyd(costs);

    return (costs[s - 1][g - 1] == Integer.MAX_VALUE) ? -1 : costs[s - 1][g - 1];
  }

  static int computeCost(int pc, int[] qc, int[] rc, int distance) {
    if (distance == Integer.MAX_VALUE) {
      return Integer.MAX_VALUE;
    }

    int result = 0;
    for (int i = pc - 1; i >= 0; --i) {
      int d = Math.max(0, distance - ((i == 0) ? 0 : qc[i - 1]));
      result += rc[i] * d;
      distance -= d;
    }

    return result;
  }

  static int[][] initTable(int n) {
    int[][] result = new int[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        result[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }

    return result;
  }

  static void floyd(int[][] d) {
    int n = d.length;

    for (int k = 0; k < n; ++k) {
      for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
          if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE) {
            d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
          }
        }
      }
    }
  }
}