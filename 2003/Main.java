import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int t = sc.nextInt();
    for (int tc = 0; tc < t; ++tc) {
      int xa = sc.nextInt();
      int ya = sc.nextInt();
      int xb = sc.nextInt();
      int yb = sc.nextInt();
      int n = sc.nextInt();
      int[] xs = new int[n];
      int[] ys = new int[n];
      int[] xt = new int[n];
      int[] yt = new int[n];
      int[] o = new int[n];
      int[] l = new int[n];
      for (int i = 0; i < n; ++i) {
        xs[i] = sc.nextInt();
        ys[i] = sc.nextInt();
        xt[i] = sc.nextInt();
        yt[i] = sc.nextInt();
        o[i] = sc.nextInt();
        l[i] = sc.nextInt();
      }

      System.out.println(solve(xa, ya, xb, yb, xs, ys, xt, yt, o, l));
    }

    sc.close();
  }

  static int solve(
      int xa, int ya, int xb, int yb, int[] xs, int[] ys, int[] xt, int[] yt, int[] o, int[] l) {
    Element[] elements =
        IntStream.range(0, xs.length)
            .mapToObj(
                i -> {
                  Point intersection =
                      computeIntersection(
                          new IntPoint(xa, ya),
                          new IntPoint(xb, yb),
                          new IntPoint(xs[i], ys[i]),
                          new IntPoint(xt[i], yt[i]));

                  return (intersection == null)
                      ? null
                      : new Element(intersection, (o[i] == 1) ? (l[i] == 1) : (l[i] == 0));
                })
            .filter(Objects::nonNull)
            .sorted(
                Comparator.comparing(
                    element -> computeDistance(xa, ya, element.point.x, element.point.y)))
            .toArray(Element[]::new);

    return (int)
        IntStream.range(0, elements.length - 1)
            .filter(i -> elements[i].elevated != elements[i + 1].elevated)
            .count();
  }

  static double computeDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

  static Point computeIntersection(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
    if (!isIntersect(a, b, c, d)) {
      return null;
    }

    int dx1 = a.x - b.x;
    int dy1 = a.y - b.y;
    int dx2 = c.x - d.x;
    int dy2 = c.y - d.y;
    int denom = dx1 * dy2 - dy1 * dx2;

    double x =
        (double) ((long) dx2 * (dx1 * a.y - dy1 * a.x) - (long) dx1 * (dx2 * c.y - dy2 * c.x))
            / denom;
    double y =
        (double) ((long) dy1 * (dy2 * c.x - dx2 * c.y) - (long) dy2 * (dy1 * a.x - dx1 * a.y))
            / denom;

    return new Point(x, y);
  }

  static boolean isIntersect(IntPoint a, IntPoint b, IntPoint c, IntPoint d) {
    return Math.max(a.x, b.x) >= Math.min(c.x, d.x)
        && Math.max(c.x, d.x) >= Math.min(a.x, b.x)
        && Math.max(a.y, b.y) >= Math.min(c.y, d.y)
        && Math.max(c.y, d.y) >= Math.min(a.y, b.y)
        && (long) computeCrossProduct(a, b, c) * computeCrossProduct(a, b, d) <= 0
        && (long) computeCrossProduct(c, d, a) * computeCrossProduct(c, d, b) <= 0;
  }

  static int computeCrossProduct(IntPoint o, IntPoint p1, IntPoint p2) {
    return (p1.x - o.x) * (p2.y - o.y) - (p2.x - o.x) * (p1.y - o.y);
  }
}

class IntPoint {
  int x;
  int y;

  IntPoint(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

class Point {
  double x;
  double y;

  Point(double x, double y) {
    this.x = x;
    this.y = y;
  }
}

class Element {
  Point point;
  boolean elevated;

  Element(Point point, boolean elevated) {
    this.point = point;
    this.elevated = elevated;
  }
}
