// https://stackoverflow.com/questions/8099804/pre-requisite-for-graphs-with-unique-topological-sort

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int m = sc.nextInt();
    int[] a = new int[m];
    int[] b = new int[m];
    for (int i = 0; i < m; ++i) {
      a[i] = sc.nextInt();
      b[i] = sc.nextInt();
    }

    System.out.println(solve(n, a, b));

    sc.close();
  }

  static String solve(int n, int[] a, int[] b) {
    @SuppressWarnings("unchecked")
    Set<Integer>[] adjSets = new Set[n];
    for (int i = 0; i < adjSets.length; ++i) {
      adjSets[i] = new HashSet<>();
    }
    for (int i = 0; i < a.length; ++i) {
      adjSets[a[i] - 1].add(b[i] - 1);
    }

    List<Integer> sorted = topologicalSort(adjSets);

    return String.format(
        "%s\n%d",
        sorted.stream().map(x -> x + 1).map(String::valueOf).collect(Collectors.joining("\n")),
        IntStream.range(0, sorted.size() - 1)
                .allMatch(i -> adjSets[sorted.get(i)].contains(sorted.get(i + 1)))
            ? 0
            : 1);
  }

  static List<Integer> topologicalSort(Set<Integer>[] adjSets) {
    int n = adjSets.length;

    List<Integer> sorted = new ArrayList<>();
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; ++i) {
      if (!visited[i]) {
        search(sorted, adjSets, visited, i);
      }
    }
    Collections.reverse(sorted);

    return sorted;
  }

  static void search(List<Integer> sorted, Set<Integer>[] adjSets, boolean[] visited, int node) {
    visited[node] = true;

    for (int adj : adjSets[node]) {
      if (!visited[adj]) {
        search(sorted, adjSets, visited, adj);
      }
    }

    sorted.add(node);
  }
}