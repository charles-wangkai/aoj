import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int[][] p = new int[N][N];
    for (int r = 0; r < N; ++r) {
      st = new StringTokenizer(br.readLine());
      for (int c = 0; c < N; ++c) {
        p[r][c] = Integer.parseInt(st.nextToken());
      }
    }

    System.out.println(solve(p));
  }

  static int solve(int[][] p) {
    int N = p.length;

    int result = Integer.MIN_VALUE;

    for (int r = 0; r < N; ++r) {
      for (int beginC = 0; beginC < N; ++beginC) {
        int sum = 0;
        for (int endC = beginC; endC < N; ++endC) {
          sum += p[r][endC];
          result = Math.max(result, sum);
        }
      }
    }

    for (int c = 0; c < N; ++c) {
      for (int beginR = 0; beginR < N; ++beginR) {
        int sum = 0;
        for (int endR = 0; endR < N; ++endR) {
          sum += p[endR][c];
          result = Math.max(result, sum);
        }
      }
    }

    int[][] leftPrefixSums = new int[N][N];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < N; ++c) {
        leftPrefixSums[r][c] = ((c == 0) ? 0 : leftPrefixSums[r][c - 1]) + p[r][c];
      }
    }

    int[][] rightPrefixSums = new int[N][N];
    for (int r = 0; r < N; ++r) {
      for (int c = N - 1; c >= 0; --c) {
        rightPrefixSums[r][c] = ((c == N - 1) ? 0 : rightPrefixSums[r][c + 1]) + p[r][c];
      }
    }

    for (int beginR = 0; beginR < N; ++beginR) {
      int[] columnSums = new int[N];
      for (int endR = beginR + 1; endR < N; ++endR) {
        int[] maxRightDeltas = new int[N];
        for (int c = N - 1; c >= 0; --c) {
          maxRightDeltas[c] =
              Math.max(
                  (c == N - 1) ? Integer.MIN_VALUE : maxRightDeltas[c + 1],
                  columnSums[c]
                      - getPrefixSum(rightPrefixSums, beginR, c + 1)
                      - getPrefixSum(rightPrefixSums, endR, c + 1));
        }

        for (int c = 0; c < N - 1; ++c) {
          int leftDelta =
              columnSums[c]
                  - getPrefixSum(leftPrefixSums, beginR, c - 1)
                  - getPrefixSum(leftPrefixSums, endR, c - 1);

          result =
              Math.max(
                  result,
                  leftPrefixSums[beginR][N - 1]
                      + leftPrefixSums[endR][N - 1]
                      + leftDelta
                      + maxRightDeltas[c + 1]);
        }

        for (int c = 0; c < columnSums.length; ++c) {
          columnSums[c] += p[endR][c];
        }
      }
    }

    return result;
  }

  static int getPrefixSum(int[][] prefixSums, int r, int c) {
    return (c >= 0 && c < prefixSums[r].length) ? prefixSums[r][c] : 0;
  }
}