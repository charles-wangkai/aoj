import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] px = new int[n];
    int[] py = new int[n];
    for (int i = 0; i < n; ++i) {
      px[i] = sc.nextInt();
      py[i] = sc.nextInt();
    }
    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      int x = sc.nextInt();
      int y = sc.nextInt();

      System.out.println(solve(px, py, x, y));
    }

    sc.close();
  }

  static int solve(int[] px, int[] py, int x, int y) {
    Point[] polygon =
        IntStream.range(0, px.length).mapToObj(i -> new Point(px[i], py[i])).toArray(Point[]::new);
    Point p = new Point(x, y);

    int intersectCount = 0;
    for (int i = 0; i < polygon.length; ++i) {
      Point a = polygon[i];
      Point b = polygon[(i + 1) % polygon.length];

      if (isOnSegment(a, b, p)) {
        return 1;
      }

      if (a.y != b.y) {
        if (a.y > b.y) {
          Point temp = a;
          a = b;
          b = temp;
        }

        if (a.y <= p.y && b.y > p.y && computeCrossProduct(a, b, p) < 0) {
          ++intersectCount;
        }
      }
    }

    return (intersectCount % 2 == 0) ? 0 : 2;
  }

  static boolean isOnSegment(Point a, Point b, Point p) {
    return computeCrossProduct(a, b, p) == 0
        && isBetween(p.x, a.x, b.x)
        && isBetween(p.y, a.y, b.y);
  }

  static int computeCrossProduct(Point o, Point p1, Point p2) {
    return (p1.x - o.x) * (p2.y - o.y) - (p2.x - o.x) * (p1.y - o.y);
  }

  static boolean isBetween(int s, int v1, int v2) {
    return s >= Math.min(v1, v2) && s <= Math.max(v1, v2);
  }
}

class Point {
  int x;
  int y;

  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}