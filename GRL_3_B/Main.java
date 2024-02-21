// https://www.geeksforgeeks.org/bridge-in-a-graph/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main implements Runnable {
  static int time;

  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 28).start();
  }

  @Override
  public void run() {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
    }

    System.out.print(solve(V, s, t));

    sc.close();
  }

  static String solve(int V, int[] s, int[] t) {
    @SuppressWarnings("unchecked")
    List<Integer>[] adjLists = new List[V];
    for (int i = 0; i < adjLists.length; ++i) {
      adjLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < s.length; ++i) {
      adjLists[s[i]].add(t[i]);
      adjLists[t[i]].add(s[i]);
    }

    List<Bridge> bridges = new ArrayList<>();
    int[] times = new int[V];
    int[] lows = new int[V];

    time = 0;
    tarjan(bridges, adjLists, times, lows, -1, 0);

    return bridges.stream()
        .sorted(
            Comparator.<Bridge, Integer>comparing(bridge -> bridge.node1)
                .thenComparing(bridge -> bridge.node2))
        .map(bridge -> String.format("%d %d", bridge.node1, bridge.node2))
        .map(x -> x + "\n")
        .collect(Collectors.joining());
  }

  static void tarjan(
      List<Bridge> bridges,
      List<Integer>[] adjLists,
      int[] times,
      int[] lows,
      int parent,
      int node) {
    ++time;
    times[node] = time;
    lows[node] = time;

    for (int adj : adjLists[node]) {
      if (times[adj] == 0) {
        tarjan(bridges, adjLists, times, lows, node, adj);
        lows[node] = Math.min(lows[node], lows[adj]);

        if (lows[adj] > times[node]) {
          bridges.add(new Bridge(Math.min(node, adj), Math.max(node, adj)));
        }
      } else if (adj != parent) {
        lows[node] = Math.min(lows[node], times[adj]);
      }
    }
  }
}

class Bridge {
  int node1;
  int node2;

  Bridge(int node1, int node2) {
    this.node1 = node1;
    this.node2 = node2;
  }
}