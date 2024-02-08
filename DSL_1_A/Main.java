import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int q = sc.nextInt();
    String[] commands = new String[q];
    int[] x = new int[q];
    int[] y = new int[q];
    for (int i = 0; i < q; ++i) {
      commands[i] = sc.next();
      x[i] = sc.nextInt();
      y[i] = sc.nextInt();
    }

    System.out.println(solve(n, commands, x, y));

    sc.close();
  }

  static String solve(int n, String[] commands, int[] x, int[] y) {
    int[] parents = new int[n];
    Arrays.fill(parents, -1);

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < commands.length; ++i) {
      int root1 = findRoot(parents, x[i]);
      int root2 = findRoot(parents, y[i]);

      if (commands[i].equals("0")) {
        if (root1 != root2) {
          parents[root2] = root1;
        }
      } else {
        result.add((root1 == root2) ? 1 : 0);
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
  }

  static int findRoot(int[] parents, int node) {
    if (parents[node] == -1) {
      return node;
    }

    parents[node] = findRoot(parents, parents[node]);

    return parents[node];
  }
}