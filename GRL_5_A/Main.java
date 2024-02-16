import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main implements Runnable {
  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 28).start();
  }

  @Override
  public void run() {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] s = new int[n - 1];
    int[] t = new int[n - 1];
    int[] w = new int[n - 1];
    for (int i = 0; i < n - 1; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
      w[i] = sc.nextInt();
    }

    System.out.println(solve(s, t, w));

    sc.close();
  }

  static int solve(int[] s, int[] t, int[] w) {
    int n = s.length + 1;

    @SuppressWarnings("unchecked")
    List<Integer>[] edgeLists = new List[n];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < s.length; ++i) {
      edgeLists[s[i]].add(i);
      edgeLists[t[i]].add(i);
    }

    int[] distances = computeDistances(s, t, w, edgeLists, 0);
    int maxDistance = Arrays.stream(distances).max().getAsInt();
    int root =
        IntStream.range(0, distances.length)
            .filter(i -> distances[i] == maxDistance)
            .findAny()
            .getAsInt();

    return Arrays.stream(computeDistances(s, t, w, edgeLists, root)).max().getAsInt();
  }

  static int[] computeDistances(int[] s, int[] t, int[] w, List<Integer>[] edgeLists, int root) {
    int[] distances = new int[edgeLists.length];
    search(distances, s, t, w, edgeLists, -1, root, 0);

    return distances;
  }

  static void search(
      int[] distances,
      int[] s,
      int[] t,
      int[] w,
      List<Integer>[] edgeLists,
      int parent,
      int node,
      int distance) {
    distances[node] = distance;

    for (int edge : edgeLists[node]) {
      int other = (s[edge] == node) ? t[edge] : s[edge];
      if (other != parent) {
        search(distances, s, t, w, edgeLists, node, other, distance + w[edge]);
      }
    }
  }
}