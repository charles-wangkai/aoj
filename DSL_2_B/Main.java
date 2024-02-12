import java.util.ArrayList;
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
    List<Integer> result = new ArrayList<>();
    int[] binaryIndexedTree = new int[Integer.highestOneBit(n) * 2 + 1];
    for (int i = 0; i < commands.length; ++i) {
      if (commands[i].equals("0")) {
        add(binaryIndexedTree, x[i], y[i]);
      } else {
        result.add(computeSum(binaryIndexedTree, y[i]) - computeSum(binaryIndexedTree, x[i] - 1));
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
  }

  static void add(int[] binaryIndexedTree, int i, int x) {
    while (i < binaryIndexedTree.length) {
      binaryIndexedTree[i] += x;
      i += i & -i;
    }
  }

  static int computeSum(int[] binaryIndexedTree, int i) {
    int result = 0;
    while (i != 0) {
      result += binaryIndexedTree[i];
      i -= i & -i;
    }

    return result;
  }
}