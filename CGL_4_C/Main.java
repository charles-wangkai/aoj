// https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection#Given_two_points_on_each_line

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] x = new int[n];
    int[] y = new int[n];
    for (int i = 0; i < n; ++i) {
      x[i] = sc.nextInt();
      y[i] = sc.nextInt();
    }
    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      int p1x = sc.nextInt();
      int p1y = sc.nextInt();
      int p2x = sc.nextInt();
      int p2y = sc.nextInt();

      System.out.println(String.format("%.9f", solve(x, y, p1x, p1y, p2x, p2y)));
    }

    sc.close();
  }

  static double solve(int[] x, int[] y, int p1x, int p1y, int p2x, int p2y) {
    Point p1 = new Point(p1x, p1y);
    Point p2 = new Point(p2x, p2y);

    List<Point> cuts = new ArrayList<>();
    for (int i = 0; i < x.length; ++i) {
      Point from = new Point(x[i], y[i]);
      Point to = new Point(x[(i + 1) % x.length], y[(i + 1) % x.length]);

      long cpFrom = computeCrossProduct(p1x, p1y, p2x, p2y, x[i], y[i]);
      long cpTo =
          computeCrossProduct(p1x, p1y, p2x, p2y, x[(i + 1) % x.length], y[(i + 1) % x.length]);

      if (cpFrom >= 0) {
        cuts.add(from);

        if (cpTo >= 0) {
          cuts.add(to);
        } else {
          cuts.add(computeIntersection(p1, p2, from, to));
        }
      } else if (cpTo >= 0) {
        cuts.add(computeIntersection(p1, p2, from, to));
        cuts.add(to);
      }
    }

    return IntStream.range(0, cuts.size())
            .mapToDouble(
                i ->
                    cuts.get(i).x * cuts.get((i + 1) % cuts.size()).y
                        - cuts.get(i).y * cuts.get((i + 1) % cuts.size()).x)
            .sum()
        / 2;
  }

  static Point computeIntersection(Point p1, Point p2, Point p3, Point p4) {
    double denom = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);

    return new Point(
        ((p1.x * p2.y - p1.y * p2.x) * (p3.x - p4.x) - (p1.x - p2.x) * (p3.x * p4.y - p3.y * p4.x))
            / denom,
        ((p1.x * p2.y - p1.y * p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x * p4.y - p3.y * p4.x))
            / denom);
  }

  static long computeCrossProduct(int x0, int y0, int x1, int y1, int x2, int y2) {
    return (x1 - x0) * (y2 - y0) - (x2 - x0) * (y1 - y0);
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