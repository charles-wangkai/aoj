import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      int m = sc.nextInt();
      if (n == 0 && m == 0) {
        break;
      }

      int s = sc.nextInt();
      int g = sc.nextInt();
      int[] x = new int[m];
      int[] y = new int[m];
      int[] d = new int[m];
      int[] c = new int[m];
      for (int i = 0; i < m; ++i) {
        x[i] = sc.nextInt();
        y[i] = sc.nextInt();
        d[i] = sc.nextInt();
        c[i] = sc.nextInt();
      }

      System.out.println(solve(n, s, g, x, y, d, c));
    }

    sc.close();
  }

  static String solve(int n, int s, int g, int[] x, int[] y, int[] d, int[] c) {
    @SuppressWarnings("unchecked")
    List<Integer>[] edgeLists = new List[n];
    for (int i = 0; i < edgeLists.length; ++i) {
      edgeLists[i] = new ArrayList<>();
    }
    for (int i = 0; i < x.length; ++i) {
      edgeLists[x[i] - 1].add(i);
      edgeLists[y[i] - 1].add(i);
    }

    Double[][][] times = new Double[n][n][Arrays.stream(c).max().orElse(0) + 1];
    PriorityQueue<Element> pq = new PriorityQueue<>(Comparator.comparing(element -> element.time));
    pq.offer(new Element(s - 1, s - 1, 0, 0));
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (head.node == g - 1 && head.speed == 1) {
        return String.format("%.9f", head.time);
      }

      if (times[head.node][head.prev][head.speed] == null) {
        times[head.node][head.prev][head.speed] = head.time;

        for (int edge : edgeLists[head.node]) {
          int other = (head.node == x[edge] - 1) ? (y[edge] - 1) : (x[edge] - 1);
          if (other != head.prev) {
            for (int i = -1; i <= 1; ++i) {
              int nextSpeed = head.speed + i;
              if (nextSpeed > 0
                  && nextSpeed <= c[edge]
                  && times[other][head.node][nextSpeed] == null) {
                pq.offer(
                    new Element(
                        other, head.node, nextSpeed, head.time + (double) d[edge] / nextSpeed));
              }
            }
          }
        }
      }
    }

    return "unreachable";
  }
}

class Element {
  int node;
  int prev;
  int speed;
  double time;

  Element(int node, int prev, int speed, double time) {
    this.node = node;
    this.prev = prev;
    this.speed = speed;
    this.time = time;
  }
}