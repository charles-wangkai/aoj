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

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      int[] p = new int[n - 1];
      for (int i = 0; i < p.length; ++i) {
        p[i] = sc.nextInt();
      }
      int[] d = new int[n - 1];
      for (int i = 0; i < d.length; ++i) {
        d[i] = sc.nextInt();
      }

      System.out.println(solve(p, d));
    }

    sc.close();
  }

  static int solve(int[] p, int[] d) {
    int n = p.length + 1;

    @SuppressWarnings("unchecked")
    Map<Integer, Integer>[] edgeMaps = new Map[n];
    for (int i = 0; i < edgeMaps.length; ++i) {
      edgeMaps[i] = new HashMap<>();
    }
    for (int i = 0; i < p.length; ++i) {
      edgeMaps[i + 1].put(p[i] - 1, d[i]);
      edgeMaps[p[i] - 1].put(i + 1, d[i]);
    }

    Set<Integer> leaves =
        IntStream.range(0, edgeMaps.length)
            .filter(i -> edgeMaps[i].size() == 1)
            .boxed()
            .collect(Collectors.toSet());

    Map<Integer, Map<Integer, Integer>> nodeToEdges =
        IntStream.range(0, n)
            .filter(i -> !leaves.contains(i))
            .boxed()
            .collect(
                Collectors.toMap(
                    i -> i,
                    i ->
                        edgeMaps[i].keySet().stream()
                            .filter(adj -> !leaves.contains(adj))
                            .collect(Collectors.toMap(adj -> adj, adj -> edgeMaps[i].get(adj)))));

    return Arrays.stream(d).sum()
        + nodeToEdges.values().stream()
            .mapToInt(edges -> edges.values().stream().mapToInt(Integer::intValue).sum())
            .sum()
        - nodeToEdges.keySet().stream()
            .mapToInt(node -> computeMaxPath(nodeToEdges, -1, node))
            .max()
            .orElse(0);
  }

  static int computeMaxPath(Map<Integer, Map<Integer, Integer>> nodeToEdges, int parent, int node) {
    int result = 0;
    Map<Integer, Integer> edges = nodeToEdges.get(node);
    for (int adj : edges.keySet()) {
      if (adj != parent) {
        result = Math.max(result, edges.get(adj) + computeMaxPath(nodeToEdges, node, adj));
      }
    }

    return result;
  }
}