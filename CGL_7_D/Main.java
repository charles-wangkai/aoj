// https://mathworld.wolfram.com/Circle-LineIntersection.html

import java.util.Scanner;

public class Main {
  static final double EPSILON = 1e-9;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int cx = sc.nextInt();
    int cy = sc.nextInt();
    int r = sc.nextInt();
    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      int x1 = sc.nextInt();
      int y1 = sc.nextInt();
      int x2 = sc.nextInt();
      int y2 = sc.nextInt();

      System.out.println(solve(cx, cy, r, x1, y1, x2, y2));
    }

    sc.close();
  }

  static String solve(int cx, int cy, int r, int x1, int y1, int x2, int y2) {
    Outcome outcome = computeCrosses(r, x1 - cx, y1 - cy, x2 - cx, y2 - cy);
    outcome.cross1.x += cx;
    outcome.cross1.y += cy;
    outcome.cross2.x += cx;
    outcome.cross2.y += cy;

    Point c1;
    Point c2;
    if (outcome.cross1.x < outcome.cross2.x - EPSILON
        || (Math.abs(outcome.cross1.x - outcome.cross2.x) < EPSILON
            && outcome.cross1.y < outcome.cross2.y)) {
      c1 = outcome.cross1;
      c2 = outcome.cross2;
    } else {
      c1 = outcome.cross2;
      c2 = outcome.cross1;
    }

    return String.format("%.9f %.9f %.9f %.9f", c1.x, c1.y, c2.x, c2.y);
  }

  static Outcome computeCrosses(int r, int x1, int y1, int x2, int y2) {
    int dx = x2 - x1;
    int dy = y2 - y1;
    double dr = Math.sqrt(dx * dx + dy * dy);
    int D = x1 * y2 - x2 * y1;
    double discriminant = r * r * dr * dr - (long) D * D;

    return new Outcome(
        new Point(
            ((long) D * dy - sgn(dy) * dx * Math.sqrt(discriminant)) / (dr * dr),
            (-(long) D * dx - Math.abs(dy) * Math.sqrt(discriminant)) / (dr * dr)),
        new Point(
            ((long) D * dy + sgn(dy) * dx * Math.sqrt(discriminant)) / (dr * dr),
            (-(long) D * dx + Math.abs(dy) * Math.sqrt(discriminant)) / (dr * dr)));
  }

  static int sgn(int x) {
    return (x < 0) ? -1 : 1;
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

class Outcome {
  Point cross1;
  Point cross2;

  Outcome(Point cross1, Point cross2) {
    this.cross1 = cross1;
    this.cross2 = cross2;
  }
}