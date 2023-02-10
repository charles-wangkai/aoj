import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int M = sc.nextInt();
    int[] x = new int[N];
    int[] y = new int[N];
    for (int i = 0; i < N; ++i) {
      x[i] = sc.nextInt();
      y[i] = sc.nextInt();
    }
    int[] p = new int[M];
    int[] q = new int[M];
    for (int i = 0; i < M; ++i) {
      p[i] = sc.nextInt() - 1;
      q[i] = sc.nextInt() - 1;
    }

    System.out.println(String.format("%.9f", solve(x, y, p, q)));

    sc.close();
  }

  static double solve(int[] x, int[] y, int[] p, int[] q) {
    double[] distances =
        IntStream.range(0, p.length)
            .mapToDouble(i -> computeDistance(x[p[i]], y[p[i]], x[q[i]], y[q[i]]))
            .toArray();

    double result = Arrays.stream(distances).sum();

    int[] sortedIndices =
        IntStream.range(0, p.length)
            .boxed()
            .sorted(Comparator.comparing((Integer i) -> distances[i]).reversed())
            .mapToInt(Integer::intValue)
            .toArray();
    int[] parents = new int[x.length];
    Arrays.fill(parents, -1);
    for (int index : sortedIndices) {
      int root1 = findRoot(parents, p[index]);
      int root2 = findRoot(parents, q[index]);
      if (root1 != root2) {
        parents[root2] = root1;
        result -= distances[index];
      }
    }

    return result;
  }

  static double computeDistance(int x1, int y1, int x2, int y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

  static int findRoot(int[] parents, int node) {
    if (parents[node] == -1) {
      return node;
    }

    parents[node] = findRoot(parents, parents[node]);

    return parents[node];
  }
}
