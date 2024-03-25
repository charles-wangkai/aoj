// https://stackoverflow.com/questions/2824478/shortest-distance-between-two-line-segments/2824596#2824596

import java.util.Scanner;
import java.util.stream.DoubleStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      int x0 = sc.nextInt();
      int y0 = sc.nextInt();
      int x1 = sc.nextInt();
      int y1 = sc.nextInt();
      int x2 = sc.nextInt();
      int y2 = sc.nextInt();
      int x3 = sc.nextInt();
      int y3 = sc.nextInt();

      System.out.println(String.format("%.9f", solve(x0, y0, x1, y1, x2, y2, x3, y3)));
    }

    sc.close();
  }

  static double solve(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
    Point p0 = new Point(x0, y0);
    Point p1 = new Point(x1, y1);
    Point p2 = new Point(x2, y2);
    Point p3 = new Point(x3, y3);

    if (isIntersect(p0, p1, p2, p3)) {
      return 0;
    }

    return DoubleStream.of(
            computeDistanceBetweenPointAndSegment(p0, p2, p3),
            computeDistanceBetweenPointAndSegment(p1, p2, p3),
            computeDistanceBetweenPointAndSegment(p2, p0, p1),
            computeDistanceBetweenPointAndSegment(p3, p0, p1))
        .min()
        .getAsDouble();
  }

  static double computeDistanceBetweenPointAndSegment(Point p, Point a, Point b) {
    double d = Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));

    double dx = (b.x - a.x) / d;
    double dy = (b.y - a.y) / d;

    double projectionX = ((p.x - a.x) * dx + (p.y - a.y) * dy) * dx + a.x;
    double projectionY = ((p.x - a.x) * dx + (p.y - a.y) * dy) * dy + a.y;

    return (computeDistance(projectionX, projectionY, a.x, a.y)
                <= computeDistance(a.x, a.y, b.x, b.y)
            && computeDistance(projectionX, projectionY, b.x, b.y)
                <= computeDistance(a.x, a.y, b.x, b.y))
        ? computeDistance(p.x, p.y, projectionX, projectionY)
        : Math.min(computeDistance(p.x, p.y, a.x, a.y), computeDistance(p.x, p.y, b.x, b.y));
  }

  static double computeDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

  static boolean isIntersect(Point a, Point b, Point c, Point d) {
    return Math.max(a.x, b.x) >= Math.min(c.x, d.x)
        && Math.max(c.x, d.x) >= Math.min(a.x, b.x)
        && Math.max(a.y, b.y) >= Math.min(c.y, d.y)
        && Math.max(c.y, d.y) >= Math.min(a.y, b.y)
        && (long) computeCrossProduct(a, b, c) * computeCrossProduct(a, b, d) <= 0
        && (long) computeCrossProduct(c, d, a) * computeCrossProduct(c, d, b) <= 0;
  }

  static int computeCrossProduct(Point o, Point p1, Point p2) {
    return (p1.x - o.x) * (p2.y - o.y) - (p2.x - o.x) * (p1.y - o.y);
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