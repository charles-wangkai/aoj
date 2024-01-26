import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] ids = new int[n];
    int[] lefts = new int[n];
    int[] rights = new int[n];
    for (int i = 0; i < n; ++i) {
      ids[i] = sc.nextInt();
      lefts[i] = sc.nextInt();
      rights[i] = sc.nextInt();
    }

    System.out.println(solve(ids, lefts, rights));

    sc.close();
  }

  static String solve(int[] ids, int[] lefts, int[] rights) {
    Set<Integer> nonRoots =
        IntStream.concat(Arrays.stream(lefts), Arrays.stream(rights))
            .filter(x -> x != -1)
            .boxed()
            .collect(Collectors.toSet());
    int root = Arrays.stream(ids).filter(id -> !nonRoots.contains(id)).findAny().getAsInt();

    Map<Integer, Integer> nodeToParent = new HashMap<>();
    for (int i = 0; i < ids.length; ++i) {
      if (lefts[i] != -1) {
        nodeToParent.put(lefts[i], ids[i]);
      }
      if (rights[i] != -1) {
        nodeToParent.put(rights[i], ids[i]);
      }
    }

    Map<Integer, Integer> nodeToLeft =
        IntStream.range(0, ids.length)
            .filter(i -> lefts[i] != -1)
            .boxed()
            .collect(Collectors.toMap(i -> ids[i], i -> lefts[i]));
    Map<Integer, Integer> nodeToRight =
        IntStream.range(0, ids.length)
            .filter(i -> rights[i] != -1)
            .boxed()
            .collect(Collectors.toMap(i -> ids[i], i -> rights[i]));

    Map<Integer, Integer> nodeToSibling = new HashMap<>();
    Map<Integer, Integer> nodeToDepth = new HashMap<>();
    Map<Integer, Integer> nodeToHeight = new HashMap<>();
    search(nodeToSibling, nodeToDepth, nodeToHeight, nodeToLeft, nodeToRight, root, 0);

    return Arrays.stream(ids)
        .sorted()
        .mapToObj(
            node ->
                String.format(
                    "node %d: parent = %d, sibling = %d, degree = %d, depth = %d, height = %d, %s",
                    node,
                    nodeToParent.getOrDefault(node, -1),
                    nodeToSibling.getOrDefault(node, -1),
                    (nodeToLeft.containsKey(node) ? 1 : 0)
                        + (nodeToRight.containsKey(node) ? 1 : 0),
                    nodeToDepth.get(node),
                    nodeToHeight.get(node),
                    (node == root)
                        ? "root"
                        : ((!nodeToLeft.containsKey(node) && !nodeToRight.containsKey(node))
                            ? "leaf"
                            : "internal node")))
        .collect(Collectors.joining("\n"));
  }

  static int search(
      Map<Integer, Integer> nodeToSibling,
      Map<Integer, Integer> nodeToDepth,
      Map<Integer, Integer> nodeToHeight,
      Map<Integer, Integer> nodeToLeft,
      Map<Integer, Integer> nodeToRight,
      int node,
      int depth) {
    nodeToDepth.put(node, depth);

    if (nodeToLeft.containsKey(node) && nodeToRight.containsKey(node)) {
      nodeToSibling.put(nodeToLeft.get(node), nodeToRight.get(node));
      nodeToSibling.put(nodeToRight.get(node), nodeToLeft.get(node));
    }

    int height = 0;
    if (nodeToLeft.containsKey(node)) {
      height =
          Math.max(
              height,
              search(
                      nodeToSibling,
                      nodeToDepth,
                      nodeToHeight,
                      nodeToLeft,
                      nodeToRight,
                      nodeToLeft.get(node),
                      depth + 1)
                  + 1);
    }
    if (nodeToRight.containsKey(node)) {
      height =
          Math.max(
              height,
              search(
                      nodeToSibling,
                      nodeToDepth,
                      nodeToHeight,
                      nodeToLeft,
                      nodeToRight,
                      nodeToRight.get(node),
                      depth + 1)
                  + 1);
    }

    nodeToHeight.put(node, height);

    return height;
  }
}