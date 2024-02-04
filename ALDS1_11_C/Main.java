import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] u = new int[n];
    int[][] v = new int[n][];
    for (int i = 0; i < u.length; ++i) {
      u[i] = sc.nextInt();
      int k = sc.nextInt();
      v[i] = new int[k];
      for (int j = 0; j < v[i].length; ++j) {
        v[i][j] = sc.nextInt();
      }
    }

    System.out.println(solve(u, v));

    sc.close();
  }

  static String solve(int[] u, int[][] v) {
    int n = u.length;

    int[][] adjLists = new int[n][];
    for (int i = 0; i < u.length; ++i) {
      adjLists[u[i] - 1] = new int[v[i].length];
      for (int j = 0; j < adjLists[u[i] - 1].length; ++j) {
        adjLists[u[i] - 1][j] = v[i][j] - 1;
      }
    }

    int[] distances = new int[n];
    Arrays.fill(distances, -1);
    distances[0] = 0;
    Deque<Integer> queue = new ArrayDeque<>();
    queue.offer(0);
    while (!queue.isEmpty()) {
      int head = queue.poll();
      for (int adj : adjLists[head]) {
        if (distances[adj] == -1) {
          distances[adj] = distances[head] + 1;
          queue.offer(adj);
        }
      }
    }

    return IntStream.range(0, n)
        .mapToObj(i -> String.format("%d %d", i + 1, distances[i]))
        .collect(Collectors.joining("\n"));
  }
}