// https://math.stackexchange.com/questions/256100/how-can-i-find-the-points-at-which-two-circles-intersect/1367732#1367732
// https://matthew-brett.github.io/teaching/rotation_2d.html

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final double EPSILON = 1e-9;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      double[] x = new double[n];
      double[] y = new double[n];
      double[] r = new double[n];
      for (int i = 0; i < n; ++i) {
        x[i] = sc.nextDouble();
        y[i] = sc.nextDouble();
        r[i] = sc.nextDouble();
      }

      System.out.println(String.format("%.9f", solve(x, y, r)));
    }

    sc.close();
  }

  static double solve(double[] x, double[] y, double[] r) {
    double result = 0;
    for (int i = 0; i < x.length; ++i) {
      List<Double> angles = new ArrayList<>();
      angles.add(0.0);
      for (int j = 0; j < x.length; ++j) {
        if (j != i) {
          for (Point intersection : computeIntersections(x[i], y[i], r[i], x[j], y[j], r[j])) {
            angles.add(Math.atan2(intersection.y - y[i], intersection.x - x[i]));
          }
        }
      }
      Collections.sort(angles);

      for (int j = 0; j < angles.size(); ++j) {
        double span =
            ((j == angles.size() - 1) ? (angles.get(0) + 2 * Math.PI) : angles.get(j + 1))
                - angles.get(j);
        Point middle =
            transform(rotate(scale(new Point(1, 0), r[i]), angles.get(j) + span / 2), x[i], y[i]);
        if (!isCovered(x, y, r, i, middle)) {
          result += r[i] * span;
        }
      }
    }

    return result;
  }

  static boolean isCovered(double[] x, double[] y, double[] r, int index, Point p) {
    return IntStream.range(0, x.length)
        .anyMatch(i -> i != index && computeDistance(p.x, p.y, x[i], y[i]) < r[i] - EPSILON);
  }

  static Point rotate(Point p, double angle) {
    return new Point(
        Math.cos(angle) * p.x - Math.sin(angle) * p.y,
        Math.sin(angle) * p.x + Math.cos(angle) * p.y);
  }

  static Point scale(Point p, double factor) {
    return new Point(p.x * factor, p.y * factor);
  }

  static Point transform(Point p, double dx, double dy) {
    return new Point(p.x + dx, p.y + dy);
  }

  static Point[] computeIntersections(
      double x1, double y1, double r1, double x2, double y2, double r2) {
    double R = computeDistance(x1, y1, x2, y2);
    if (R > r1 + r2 + EPSILON || R < Math.abs(r1 - r2) - EPSILON) {
      return new Point[0];
    }

    double f1 = (r1 * r1 - r2 * r2) / (2 * R * R);
    double f2 =
        0.5
            * Math.sqrt(
                2 * (r1 * r1 + r2 * r2) / (R * R)
                    - (long) (r1 * r1 - r2 * r2) * (r1 * r1 - r2 * r2) / (R * R * R * R)
                    - 1);

    return new Point[] {
      new Point(
          0.5 * (x1 + x2) + f1 * (x2 - x1) - f2 * (y2 - y1),
          0.5 * (y1 + y2) + f1 * (y2 - y1) - f2 * (x1 - x2)),
      new Point(
          0.5 * (x1 + x2) + f1 * (x2 - x1) + f2 * (y2 - y1),
          0.5 * (y1 + y2) + f1 * (y2 - y1) + f2 * (x1 - x2))
    };
  }

  static double computeDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
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