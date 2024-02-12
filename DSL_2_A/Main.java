import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  static final int INIT_VALUE = (1 << 31) - 1;

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
    Node segmentTree = buildNode(0, n - 1);

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < commands.length; ++i) {
      if (commands[i].equals("0")) {
        update(x[i], y[i], segmentTree);
      } else {
        result.add(query(x[i], y[i], segmentTree));
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
  }

  static int query(int beginIndex, int endIndex, Node node) {
    if (node.beginIndex > endIndex || node.endIndex < beginIndex) {
      return Integer.MAX_VALUE;
    }
    if (node.beginIndex >= beginIndex && node.endIndex <= endIndex) {
      return node.minValue;
    }

    return Math.min(
        query(beginIndex, endIndex, node.left), query(beginIndex, endIndex, node.right));
  }

  static void update(int index, int value, Node node) {
    if (node.beginIndex <= index && node.endIndex >= index) {
      if (node.beginIndex == node.endIndex) {
        node.minValue = value;
      } else {
        update(index, value, node.left);
        update(index, value, node.right);
        node.minValue = Math.min(node.left.minValue, node.right.minValue);
      }
    }
  }

  static Node buildNode(int beginIndex, int endIndex) {
    Node left;
    Node right;
    if (beginIndex == endIndex) {
      left = null;
      right = null;
    } else {
      int middleIndex = (beginIndex + endIndex) / 2;
      left = buildNode(beginIndex, middleIndex);
      right = buildNode(middleIndex + 1, endIndex);
    }

    return new Node(beginIndex, endIndex, INIT_VALUE, left, right);
  }
}

class Node {
  int beginIndex;
  int endIndex;
  int minValue;
  Node left;
  Node right;

  Node(int beginIndex, int endIndex, int minValue, Node left, Node right) {
    this.beginIndex = beginIndex;
    this.endIndex = endIndex;
    this.minValue = minValue;
    this.left = left;
    this.right = right;
  }
}