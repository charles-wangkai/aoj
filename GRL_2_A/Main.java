import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    int[] w = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
      w[i] = sc.nextInt();
    }

    System.out.println(solve(V, s, t, w));

    sc.close();
  }

  static int solve(int V, int[] s, int[] t, int[] w) {
    int[] parents = new int[V];
    Arrays.fill(parents, -1);

    int[] sortedIndices =
        IntStream.range(0, s.length)
            .boxed()
            .sorted(Comparator.comparing(i -> w[i]))
            .mapToInt(Integer::intValue)
            .toArray();

    int result = 0;
    for (int index : sortedIndices) {
      int root1 = findRoot(parents, s[index]);
      int root2 = findRoot(parents, t[index]);
      if (root1 != root2) {
        result += w[index];
        parents[root2] = root1;
      }
    }

    return result;
  }

  static int findRoot(int[] parents, int node) {
    if (parents[node] == -1) {
      return node;
    }

    parents[node] = findRoot(parents, parents[node]);

    return parents[node];
  }
}