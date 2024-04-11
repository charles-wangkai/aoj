// https://math.stackexchange.com/questions/256100/how-can-i-find-the-points-at-which-two-circles-intersect/1367732#1367732

import java.util.Scanner;

public class Main {
  static final double EPSILON = 1e-9;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int x1 = sc.nextInt();
    int y1 = sc.nextInt();
    int r1 = sc.nextInt();
    int x2 = sc.nextInt();
    int y2 = sc.nextInt();
    int r2 = sc.nextInt();

    System.out.println(solve(x1, y1, r1, x2, y2, r2));

    sc.close();
  }

  static String solve(int x1, int y1, int r1, int x2, int y2, int r2) {
    double R = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    double f1 = (r1 * r1 - r2 * r2) / (2 * R * R);
    double f2 =
        0.5
            * Math.sqrt(
                2 * (r1 * r1 + r2 * r2) / (R * R)
                    - (long) (r1 * r1 - r2 * r2) * (r1 * r1 - r2 * r2) / (R * R * R * R)
                    - 1);

    Point cross1 =
        new Point(
            0.5 * (x1 + x2) + f1 * (x2 - x1) - f2 * (y2 - y1),
            0.5 * (y1 + y2) + f1 * (y2 - y1) - f2 * (x1 - x2));
    Point cross2 =
        new Point(
            0.5 * (x1 + x2) + f1 * (x2 - x1) + f2 * (y2 - y1),
            0.5 * (y1 + y2) + f1 * (y2 - y1) + f2 * (x1 - x2));

    Point c1;
    Point c2;
    if (cross1.x < cross2.x - EPSILON
        || (Math.abs(cross1.x - cross2.x) < EPSILON && cross1.y < cross2.y)) {
      c1 = cross1;
      c2 = cross2;
    } else {
      c1 = cross2;
      c2 = cross1;
    }

    return String.format("%.9f %.9f %.9f %.9f", c1.x, c1.y, c2.x, c2.y);
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
