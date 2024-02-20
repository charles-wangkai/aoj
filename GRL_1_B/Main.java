import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int V = sc.nextInt();
    int E = sc.nextInt();
    int r = sc.nextInt();
    int[] s = new int[E];
    int[] t = new int[E];
    int[] d = new int[E];
    for (int i = 0; i < E; ++i) {
      s[i] = sc.nextInt();
      t[i] = sc.nextInt();
      d[i] = sc.nextInt();
    }

    System.out.println(solve(V, s, t, d, r));

    sc.close();
  }

  static String solve(int V, int[] s, int[] t, int[] d, int r) {
    int[] distances = new int[V];
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[r] = 0;

    int round = 0;
    while (true) {
      boolean updated = false;
      for (int i = 0; i < s.length; ++i) {
        if (distances[s[i]] != Integer.MAX_VALUE && distances[t[i]] > distances[s[i]] + d[i]) {
          distances[t[i]] = distances[s[i]] + d[i];
          updated = true;
        }
      }

      if (!updated) {
        break;
      }
      if (round == V - 1) {
        return "NEGATIVE CYCLE";
      }

      ++round;
    }

    return Arrays.stream(distances)
        .mapToObj(distance -> (distance == Integer.MAX_VALUE) ? "INF" : String.valueOf(distance))
        .collect(Collectors.joining("\n"));
  }
}