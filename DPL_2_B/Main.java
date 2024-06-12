// https://baike.baidu.com/item/%E4%B8%AD%E5%9B%BD%E9%82%AE%E8%B7%AF%E9%97%AE%E9%A2%98

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    int[] d = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
      d[i] = sc.nextInt();
    }

    System.out.println(solve(V, s, t, d));

    sc.close();
  }

  static int solve(int V, int[] s, int[] t, int[] d) {
    int[] degrees = new int[V];
    for (int i = 0; i < s.length; ++i) {
      ++degrees[s[i]];
      ++degrees[t[i]];
    }

    Map<Integer, Integer> maskToMinDistance =
        Map.of(
            IntStream.range(0, degrees.length)
                .filter(i -> degrees[i] % 2 == 1)
                .map(i -> 1 << i)
                .sum(),
            Arrays.stream(d).sum());

    int[][] distances = buildDistances(V, s, t, d);

    while (!maskToMinDistance.containsKey(0)) {
      Map<Integer, Integer> nextMaskToMinDistance = new HashMap<>();
      for (int mask : maskToMinDistance.keySet()) {
        int[] nodes = IntStream.range(0, V).filter(i -> ((mask >> i) & 1) == 1).toArray();
        for (int i = 0; i < nodes.length; ++i) {
          for (int j = i + 1; j < nodes.length; ++j) {
            int nextMask = mask - (1 << nodes[i]) - (1 << nodes[j]);
            nextMaskToMinDistance.put(
                nextMask,
                Math.min(
                    nextMaskToMinDistance.getOrDefault(nextMask, Integer.MAX_VALUE),
                    maskToMinDistance.get(mask) + distances[nodes[i]][nodes[j]]));
          }
        }
      }

      maskToMinDistance = nextMaskToMinDistance;
    }

    return maskToMinDistance.get(0);
  }

  static int[][] buildDistances(int V, int[] s, int[] t, int[] d) {
    int[][] distances = new int[V][V];
    for (int i = 0; i < V; ++i) {
      for (int j = 0; j < V; ++j) {
        distances[i][j] = (j == i) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < s.length; ++i) {
      distances[s[i]][t[i]] = Math.min(distances[s[i]][t[i]], d[i]);
      distances[t[i]][s[i]] = Math.min(distances[t[i]][s[i]], d[i]);
    }

    for (int k = 0; k < V; ++k) {
      for (int i = 0; i < V; ++i) {
        for (int j = 0; j < V; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    return distances;
  }
}