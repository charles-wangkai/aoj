import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static final Random RANDOM = new Random();

  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int[] x = new int[n];
    int[] y = new int[n];
    for (int i = 0; i < n; ++i) {
      st = new StringTokenizer(br.readLine());
      x[i] = Integer.parseInt(st.nextToken());
      y[i] = Integer.parseInt(st.nextToken());
    }
    st = new StringTokenizer(br.readLine());
    int q = Integer.parseInt(st.nextToken());
    int[] sx = new int[q];
    int[] tx = new int[q];
    int[] sy = new int[q];
    int[] ty = new int[q];
    for (int i = 0; i < q; ++i) {
      st = new StringTokenizer(br.readLine());
      sx[i] = Integer.parseInt(st.nextToken());
      tx[i] = Integer.parseInt(st.nextToken());
      sy[i] = Integer.parseInt(st.nextToken());
      ty[i] = Integer.parseInt(st.nextToken());
    }

    System.out.print(solve(x, y, sx, tx, sy, ty));
  }

  static String solve(int[] x, int[] y, int[] sx, int[] tx, int[] sy, int[] ty) {
    Node kdTree =
        buildKdTree(x, y, IntStream.range(0, x.length).boxed().collect(Collectors.toList()), true);

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < sx.length; ++i) {
      List<Integer> insideIndices = new ArrayList<>();
      query(insideIndices, sx[i], tx[i], sy[i], ty[i], kdTree);
      Collections.sort(insideIndices);

      for (int index : insideIndices) {
        result.append(index).append("\n");
      }
      result.append("\n");
    }

    return result.toString();
  }

  static void query(
      List<Integer> insideIndices, int minX, int maxX, int minY, int maxY, Node node) {
    if (node != null
        && !(node.minX > maxX || node.maxX < minX || node.minY > maxY || node.maxY < minY)) {
      if (node.minX >= minX && node.maxX <= maxX && node.minY >= minY && node.maxY <= maxY) {
        insideIndices.addAll(node.indices);
      } else {
        query(insideIndices, minX, maxX, minY, maxY, node.left);
        query(insideIndices, minX, maxX, minY, maxY, node.right);
      }
    }
  }

  static Node buildKdTree(int[] x, int[] y, List<Integer> indices, boolean splitByXOrY) {
    if (indices.isEmpty()) {
      return null;
    }

    Node left;
    Node right;
    if (indices.size() == 1) {
      left = null;
      right = null;
    } else {
      int pivotIndex = RANDOM.nextInt(indices.size());
      swap(indices, 0, pivotIndex);

      int lower = 1;
      int upper = indices.size() - 1;
      while (lower <= upper) {
        if ((splitByXOrY && x[indices.get(lower)] <= x[indices.get(0)])
            || (!splitByXOrY && y[indices.get(lower)] <= y[indices.get(0)])) {
          ++lower;
        } else {
          swap(indices, lower, upper);
          --upper;
        }
      }

      left = buildKdTree(x, y, indices.subList(0, lower), !splitByXOrY);
      right = buildKdTree(x, y, indices.subList(lower, indices.size()), !splitByXOrY);
    }

    return new Node(x, y, indices, left, right);
  }

  static void swap(List<Integer> a, int index1, int index2) {
    int temp = a.get(index1);
    a.set(index1, a.get(index2));
    a.set(index2, temp);
  }
}

class Node {
  List<Integer> indices;
  int minX = Integer.MAX_VALUE;
  int maxX = Integer.MIN_VALUE;
  int minY = Integer.MAX_VALUE;
  int maxY = Integer.MIN_VALUE;
  Node left;
  Node right;

  Node(int[] x, int[] y, List<Integer> indices, Node left, Node right) {
    this.indices = indices;

    for (int index : indices) {
      minX = Math.min(minX, x[index]);
      maxX = Math.max(maxX, x[index]);
      minY = Math.min(minY, y[index]);
      maxY = Math.max(maxY, y[index]);
    }

    this.left = left;
    this.right = right;
  }
}