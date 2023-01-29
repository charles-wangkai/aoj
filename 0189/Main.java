import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      int[] a = new int[n];
      int[] b = new int[n];
      int[] c = new int[n];
      for (int i = 0; i < n; ++i) {
        a[i] = sc.nextInt();
        b[i] = sc.nextInt();
        c[i] = sc.nextInt();
      }

      System.out.println(solve(a, b, c));
    }

    sc.close();
  }

  static String solve(int[] a, int[] b, int[] c) {
    Map<Integer, List<Integer>> nodeToEdges = new HashMap<>();
    for (int i = 0; i < a.length; ++i) {
      nodeToEdges.putIfAbsent(a[i], new ArrayList<>());
      nodeToEdges.get(a[i]).add(i);

      nodeToEdges.putIfAbsent(b[i], new ArrayList<>());
      nodeToEdges.get(b[i]).add(i);
    }

    int bestNode = -1;
    int minDistanceSum = Integer.MAX_VALUE;
    for (int node : nodeToEdges.keySet()) {
      int distanceSum =
          computeDistances(a, b, c, nodeToEdges, node).values().stream().mapToInt(x -> x).sum();
      if (distanceSum < minDistanceSum || (distanceSum == minDistanceSum && node < bestNode)) {
        bestNode = node;
        minDistanceSum = distanceSum;
      }
    }

    return String.format("%d %d", bestNode, minDistanceSum);
  }

  static Map<Integer, Integer> computeDistances(
      int[] a, int[] b, int[] c, Map<Integer, List<Integer>> nodeToEdges, int startNode) {
    Map<Integer, Integer> nodeToDistance = new HashMap<>();
    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(e -> e.distance));
    pq.offer(new Element(startNode, 0));
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (!nodeToDistance.containsKey(head.node)) {
        nodeToDistance.put(head.node, head.distance);

        for (int edge : nodeToEdges.get(head.node)) {
          int other = (head.node == a[edge]) ? b[edge] : a[edge];
          if (!nodeToDistance.containsKey(other)) {
            pq.offer(new Element(other, head.distance + c[edge]));
          }
        }
      }
    }

    return nodeToDistance;
  }
}

class Element {
  int node;
  int distance;

  Element(int node, int distance) {
    this.node = node;
    this.distance = distance;
  }
}
