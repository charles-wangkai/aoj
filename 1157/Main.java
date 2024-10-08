// https://stackoverflow.com/questions/2824478/shortest-distance-between-two-line-segments/2824596#2824596

import java.util.Scanner;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Main {
  static final int ITERATION_NUM = 100;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      if (N == 0) {
        break;
      }

      int sx = sc.nextInt();
      int sy = sc.nextInt();
      int ex = sc.nextInt();
      int ey = sc.nextInt();
      int[] minX = new int[N];
      int[] minY = new int[N];
      int[] maxX = new int[N];
      int[] maxY = new int[N];
      int[] h = new int[N];
      for (int i = 0; i < N; ++i) {
        minX[i] = sc.nextInt();
        minY[i] = sc.nextInt();
        maxX[i] = sc.nextInt();
        maxY[i] = sc.nextInt();
        h[i] = sc.nextInt();
      }

      System.out.println(String.format("%.9f", solve(sx, sy, ex, ey, minX, minY, maxX, maxY, h)));
    }

    sc.close();
  }

  static double solve(
      int sx, int sy, int ex, int ey, int[] minX, int[] minY, int[] maxX, int[] maxY, int[] h) {
    if (IntStream.range(0, minX.length)
        .anyMatch(
            i ->
                isIn(sx, sy, minX[i], minY[i], maxX[i], maxY[i])
                    || isIn(ex, ey, minX[i], minY[i], maxX[i], maxY[i]))) {
      return 0;
    }

    double[] distances =
        IntStream.range(0, minX.length)
            .mapToDouble(
                i ->
                    DoubleStream.of(
                            computeDistanceBetweenSegments(
                                sx, sy, ex, ey, minX[i], minY[i], maxX[i], minY[i]),
                            computeDistanceBetweenSegments(
                                sx, sy, ex, ey, maxX[i], minY[i], maxX[i], maxY[i]),
                            computeDistanceBetweenSegments(
                                sx, sy, ex, ey, maxX[i], maxY[i], minX[i], maxY[i]),
                            computeDistanceBetweenSegments(
                                sx, sy, ex, ey, minX[i], maxY[i], minX[i], minY[i]))
                        .min()
                        .getAsDouble())
            .toArray();

    double result = 0;
    double lower = 0;
    double upper = 1000;
    for (int i = 0; i < ITERATION_NUM; ++i) {
      double middle = (lower + upper) / 2;
      if (check(h, distances, middle)) {
        result = middle;
        lower = middle;
      } else {
        upper = middle;
      }
    }

    return result;
  }

  static boolean check(int[] h, double[] distances, double r) {
    return IntStream.range(0, h.length)
        .allMatch(
            i -> distances[i] >= ((r <= h[i]) ? r : (r * Math.sin(Math.acos((r - h[i]) / r)))));
  }

  static double computeDistanceBetweenSegments(
      int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
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

  static boolean isIn(int x, int y, int xMin, int yMin, int xMax, int yMax) {
    return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
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
}

class Point {
  int x;
  int y;

  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}