// https://blog.csdn.net/accrazypt/article/details/106220267

import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      if (N == 0) {
        break;
      }

      int[] x = new int[M];
      int[] y = new int[M];
      int[] t = new int[M];
      char[] sl = new char[M];
      for (int i = 0; i < M; ++i) {
        x[i] = sc.nextInt() - 1;
        y[i] = sc.nextInt() - 1;
        t[i] = sc.nextInt();
        sl[i] = sc.next().charAt(0);
      }
      int R = sc.nextInt();
      int[] z = new int[R];
      for (int i = 0; i < z.length; ++i) {
        z[i] = sc.nextInt() - 1;
      }

      System.out.println(solve(N, x, y, t, sl, z));
    }

    sc.close();
  }

  static int solve(int N, int[] x, int[] y, int[] t, char[] sl, int[] z) {
    int[][] landDistances = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        landDistances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }

    int[][] seaDistances = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        seaDistances[i][j] = (i == j) ? 0 : Integer.MAX_VALUE;
      }
    }

    for (int i = 0; i < x.length; ++i) {
      if (sl[i] == 'L') {
        landDistances[x[i]][y[i]] = Math.min(landDistances[x[i]][y[i]], t[i]);
        landDistances[y[i]][x[i]] = Math.min(landDistances[y[i]][x[i]], t[i]);
      } else {
        seaDistances[x[i]][y[i]] = Math.min(seaDistances[x[i]][y[i]], t[i]);
        seaDistances[y[i]][x[i]] = Math.min(seaDistances[y[i]][x[i]], t[i]);
      }
    }

    floyd(landDistances);
    floyd(seaDistances);

    int[] dp = new int[N];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[z[0]] = 0;

    for (int i = 0; i < z.length; ++i) {
      int prev = (i == 0) ? z[0] : z[i - 1];

      int[] nextDp = new int[N];
      Arrays.fill(nextDp, Integer.MAX_VALUE);
      for (int j = 0; j < nextDp.length; ++j) {
        if (dp[j] != Integer.MAX_VALUE && landDistances[prev][z[i]] != Integer.MAX_VALUE) {
          nextDp[j] = Math.min(nextDp[j], dp[j] + landDistances[prev][z[i]]);
        }
        for (int k = 0; k < N; ++k) {
          if (dp[k] != Integer.MAX_VALUE
              && landDistances[prev][k] != Integer.MAX_VALUE
              && seaDistances[k][j] != Integer.MAX_VALUE
              && landDistances[j][z[i]] != Integer.MAX_VALUE) {
            nextDp[j] =
                Math.min(
                    nextDp[j],
                    dp[k] + landDistances[prev][k] + seaDistances[k][j] + landDistances[j][z[i]]);
          }
        }
      }

      dp = nextDp;
    }

    return Arrays.stream(dp).min().getAsInt();
  }

  static void floyd(int[][] distances) {
    int N = distances.length;

    for (int k = 0; k < N; ++k) {
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }
  }
}
