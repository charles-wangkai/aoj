// https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=8333624

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int[] x = new int[N];
    int[] y = new int[N];
    for (int i = 0; i < N; ++i) {
      x[i] = sc.nextInt();
      y[i] = sc.nextInt();
    }
    int Q = sc.nextInt();
    int[] k = new int[Q];
    for (int i = 0; i < k.length; ++i) {
      k[i] = sc.nextInt();
    }

    System.out.println(solve(x, y, k));

    sc.close();
  }

  static String solve(int[] x, int[] y, int[] k) {
    int N = x.length;

    Edge[] edges =
        IntStream.range(0, N)
            .boxed()
            .flatMap(i -> IntStream.range(0, N).filter(j -> j != i).mapToObj(j -> new Edge(i, j)))
            .sorted(
                Comparator.comparing(
                    edge -> {
                      double result =
                          Math.atan2(y[edge.to] - y[edge.from], x[edge.to] - x[edge.from]);
                      if (result < 0) {
                        result += Math.PI * 2;
                      }

                      return result;
                    }))
            .toArray(Edge[]::new);

    int[] minAreas = new int[N + 1];
    Arrays.fill(minAreas, Integer.MAX_VALUE);

    @SuppressWarnings("unchecked")
    List<Integer>[] pathForMinAreas = new List[N + 1];

    for (int i = 0; i < N; ++i) {
      int[][] dp = new int[N][N + 1];
      for (int j = 0; j < dp.length; ++j) {
        Arrays.fill(dp[j], Integer.MAX_VALUE);
      }
      dp[i][0] = 0;

      @SuppressWarnings("unchecked")
      List<Integer>[][] paths = new List[N][N + 1];
      paths[i][0] = List.of(i);

      for (Edge edge : edges) {
        int weight = x[edge.from] * y[edge.to] - y[edge.from] * x[edge.to];

        for (int j = 1; j <= N; ++j) {
          if (dp[edge.from][j - 1] != Integer.MAX_VALUE
              && dp[edge.from][j - 1] + weight < dp[edge.to][j]) {
            dp[edge.to][j] = dp[edge.from][j - 1] + weight;
            paths[edge.to][j] =
                Stream.concat(paths[edge.from][j - 1].stream(), Stream.of(edge.to))
                    .collect(Collectors.toList());
          }
        }
      }

      for (int j = 1; j <= N; ++j) {
        if (dp[i][j] < minAreas[j]) {
          minAreas[j] = dp[i][j];
          pathForMinAreas[j] = paths[i][j];
        }
      }
    }

    return Arrays.stream(k)
        .mapToObj(
            ki ->
                (pathForMinAreas[ki] == null)
                    ? "NA"
                    : IntStream.range(0, ki)
                        .map(i -> pathForMinAreas[ki].get(i) + 1)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")))
        .collect(Collectors.joining("\n"));
  }
}

class Edge {
  int from;
  int to;

  Edge(int from, int to) {
    this.from = from;
    this.to = to;
  }
}