// https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=3213715

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  static final double EPSILON = 1e-9;
  static final int FACTOR = 200;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      int[] x1 = new int[n];
      int[] y1 = new int[n];
      int[] x2 = new int[n];
      int[] y2 = new int[n];
      for (int i = 0; i < n; ++i) {
        x1[i] = sc.nextInt();
        y1[i] = sc.nextInt();
        x2[i] = sc.nextInt();
        y2[i] = sc.nextInt();
      }

      System.out.println(solve(x1, y1, x2, y2) ? "yes" : "no");
    }

    sc.close();
  }

  static boolean solve(int[] x1, int[] y1, int[] x2, int[] y2) {
    Segment[] segments =
        IntStream.range(0, x1.length)
            .mapToObj(
                i -> {
                  Point p1 = new Point(x1[i] * FACTOR, y1[i] * FACTOR);
                  Point p2 = new Point(x2[i] * FACTOR, y2[i] * FACTOR);

                  double dx = p2.x - p1.x;
                  double dy = p2.y - p1.y;
                  double length = Math.sqrt(dx * dx + dy * dy);
                  dx /= length;
                  dy /= length;

                  p1.x -= dx;
                  p1.y -= dy;
                  p2.x += dx;
                  p2.y += dy;

                  return new Segment(p1, p2);
                })
            .toArray(Segment[]::new);

    Point[] points =
        Stream.concat(
                Arrays.stream(segments).flatMap(segment -> Stream.of(segment.p1, segment.p2)),
                Stream.of(new Point(0, 0), new Point(1000 * FACTOR, 1000 * FACTOR)))
            .toArray(Point[]::new);

    boolean[][] adjs = new boolean[points.length][points.length];
    for (int i = 0; i < adjs.length; ++i) {
      Arrays.fill(adjs[i], true);
    }

    for (int i = 0; i < points.length; ++i) {
      for (int j = i + 1; j < points.length; ++j) {
        for (int k = 0; k < segments.length; ++k) {
          if (isCross(new Segment(points[i], points[j]), segments[k])) {
            adjs[i][j] = false;
            adjs[j][i] = false;
          }
        }
      }
    }

    for (int k = 0; k < points.length; ++k) {
      for (int i = 0; i < points.length; ++i) {
        for (int j = 0; j < points.length; ++j) {
          adjs[i][j] |= adjs[i][k] && adjs[k][j];
        }
      }
    }

    return !adjs[points.length - 2][points.length - 1];
  }

  static boolean isCross(Segment s1, Segment s2) {
    return (equals(s1.p1, s2.p1) && equals(s1.p2, s2.p2))
        || (equals(s1.p1, s2.p2) && equals(s1.p2, s2.p1))
        || (!equals(s1.p1, s2.p1)
            && !equals(s1.p1, s2.p2)
            && !equals(s1.p2, s2.p1)
            && !equals(s1.p2, s2.p2)
            && Math.max(s1.p1.x, s1.p2.x) >= Math.min(s2.p1.x, s2.p2.x) - EPSILON
            && Math.max(s2.p1.x, s2.p2.x) >= Math.min(s1.p1.x, s1.p2.x) - EPSILON
            && Math.max(s1.p1.y, s1.p2.y) >= Math.min(s2.p1.y, s2.p2.y) - EPSILON
            && Math.max(s2.p1.y, s2.p2.y) >= Math.min(s1.p1.y, s1.p2.y) - EPSILON
            && computeCrossProductSign(s1.p1, s1.p2, s2.p1)
                    * computeCrossProductSign(s1.p1, s1.p2, s2.p2)
                <= 0
            && computeCrossProductSign(s2.p1, s2.p2, s1.p1)
                    * computeCrossProductSign(s2.p1, s2.p2, s1.p2)
                <= 0);
  }

  static int computeCrossProductSign(Point o, Point p1, Point p2) {
    double crossProduct = (p1.x - o.x) * (p2.y - o.y) - (p2.x - o.x) * (p1.y - o.y);

    return (Math.abs(crossProduct) < EPSILON) ? 0 : (int) Math.signum(crossProduct);
  }

  static boolean equals(Point p1, Point p2) {
    return Math.abs(p1.x - p2.x) < EPSILON && Math.abs(p1.y - p2.y) < EPSILON;
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

class Segment {
  Point p1;
  Point p2;

  Segment(Point p1, Point p2) {
    this.p1 = p1;
    this.p2 = p2;
  }
}