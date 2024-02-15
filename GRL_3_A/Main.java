// https://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    boolean[] cuts = new boolean[V];
    int[] times = new int[V];
    int[] lows = new int[V];

    time = 0;
    tarjan(adjLists, cuts, times, lows, -1, 0);

    return IntStream.range(0, cuts.length)
        .filter(i -> cuts[i])
        .mapToObj(String::valueOf)
        .map(x -> x + "\n")
        .collect(Collectors.joining());
  }

  static void tarjan(
      List<Integer>[] adjLists, boolean[] cuts, int[] times, int[] lows, int parent, int node) {
    ++time;
    times[node] = time;
    lows[node] = time;

    int childCount = 0;
    for (int adj : adjLists[node]) {
      if (times[adj] == 0) {
        ++childCount;

        tarjan(adjLists, cuts, times, lows, node, adj);
        lows[node] = Math.min(lows[node], lows[adj]);

        if (parent != -1 && lows[adj] >= times[node]) {
          cuts[node] = true;
        }
      } else if (adj != parent) {
        lows[node] = Math.min(lows[node], times[adj]);
      }
    }

    if (parent == -1 && childCount >= 2) {
      cuts[node] = true;
    }
  }
}