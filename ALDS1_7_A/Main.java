import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    Map<Integer, int[]> nodeToChildren = new HashMap<>();
    for (int i = 0; i < n; ++i) {
      int id = sc.nextInt();
      int k = sc.nextInt();
      int[] children = new int[k];
      for (int j = 0; j < children.length; ++j) {
        children[j] = sc.nextInt();
      }

      nodeToChildren.put(id, children);
    }

    System.out.println(solve(nodeToChildren));

    sc.close();
  }

  static String solve(Map<Integer, int[]> nodeToChildren) {
    Set<Integer> nonRoots =
        nodeToChildren.values().stream()
            .flatMapToInt(Arrays::stream)
            .boxed()
            .collect(Collectors.toSet());
    int root =
        nodeToChildren.keySet().stream().filter(node -> !nonRoots.contains(node)).findAny().get();

    Map<Integer, Integer> nodeToParent = new HashMap<>();
    for (int node : nodeToChildren.keySet()) {
      for (int child : nodeToChildren.get(node)) {
        nodeToParent.put(child, node);
      }
    }

    Map<Integer, Integer> nodeToDepth = new HashMap<>();
    search(nodeToDepth, nodeToChildren, root, 0);

    return nodeToChildren.keySet().stream()
        .sorted()
        .map(
            node ->
                String.format(
                    "node %d: parent = %d, depth = %d, %s, [%s]",
                    node,
                    nodeToParent.getOrDefault(node, -1),
                    nodeToDepth.get(node),
                    (node == root)
                        ? "root"
                        : ((nodeToChildren.get(node).length == 0) ? "leaf" : "internal node"),
                    Arrays.stream(nodeToChildren.get(node))
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(", "))))
        .collect(Collectors.joining("\n"));
  }

  static void search(
      Map<Integer, Integer> nodeToDepth, Map<Integer, int[]> nodeToChildren, int node, int depth) {
    nodeToDepth.put(node, depth);

    for (int child : nodeToChildren.get(node)) {
      search(nodeToDepth, nodeToChildren, child, depth + 1);
    }
  }
}