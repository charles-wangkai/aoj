import java.util.Scanner;

public class Main {
  static final double EPSILON = 1e-9;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      double D = sc.nextDouble();
      if (Math.abs(D) < EPSILON) {
        break;
      }

      double px = sc.nextDouble();
      double py = sc.nextDouble();
      double vx = sc.nextDouble();
      double vy = sc.nextDouble();

      System.out.println(solve(D, px, py, vx, vy));
    }

    sc.close();
  }

  static String solve(double D, double px, double py, double vx, double vy) {
    Point v = normalize(new Point(vx, vy));
    Point direction = normalize(new Point(-px, -py));

    double distance = Double.MAX_VALUE;
    if (Math.abs(v.x - direction.x) < EPSILON && Math.abs(v.y - direction.y) < EPSILON) {
      distance = computeDistance(new Point(px, py), new Point(0, 0));
    } else if (Math.abs(v.x + direction.x) < EPSILON && Math.abs(v.y + direction.y) < EPSILON) {
      distance = 2 - computeDistance(new Point(px, py), new Point(0, 0));
    }

    return (distance < D) ? String.format("%.9f", distance) : "impossible";
  }

  static Point normalize(Point p) {
    double distance = computeDistance(p, new Point(0, 0));

    return new Point(p.x / distance, p.y / distance);
  }

  static double computeDistance(Point p1, Point p2) {
    return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
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