// https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=6253862

import java.util.ArrayList;
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

      int[] x = new int[n];
      int[] y = new int[n];
      int[] r = new int[n];
      for (int i = 0; i < n; ++i) {
        x[i] = sc.nextInt();
        y[i] = sc.nextInt();
        r[i] = sc.nextInt();
      }

      System.out.println(String.format("%.9f", solve(x, y, r)));
    }

    sc.close();
  }

  static double solve(int[] x, int[] y, int[] r) {
    int n = x.length;

    List<Point> points = new ArrayList<>();
    points.add(new Point(x[0], y[0]));
    for (int i = 0; i < n - 1; ++i) {
      int dx = x[i + 1] - x[i];
      int dy = y[i + 1] - y[i];

      double distance = Math.sqrt(dx * dx + dy * dy);
      double angle = Math.atan2(dy, dx);
      double angleDelta =
          Math.acos(
              (r[i] * r[i] + distance * distance - r[i + 1] * r[i + 1]) / (2 * r[i] * distance));
      points.add(computeIntersection(x[i], y[i], r[i], angle - angleDelta));
      points.add(computeIntersection(x[i], y[i], r[i], angle + angleDelta));
    }
    points.add(new Point(x[n - 1], y[n - 1]));

    Double[][] distances = new Double[points.size()][points.size()];
    for (int i = 0; i < distances.length; ++i) {
      distances[i][i] = 0.0;
    }
    for (int i = 0; i < points.size(); ++i) {
      for (int j = i + 1; j < points.size(); ++j) {
        if (isReachable(points, i, j)) {
          distances[i][j] = computeDistance(points.get(i), points.get(j));
        }
      }
    }

    for (int k = 0; k < points.size(); ++k) {
      for (int i = 0; i < points.size(); ++i) {
        for (int j = 0; j < points.size(); ++j) {
          if (distances[i][k] != null && distances[k][j] != null) {
            distances[i][j] =
                Math.min(
                    (distances[i][j] == null) ? Double.MAX_VALUE : distances[i][j],
                    distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    return distances[0][points.size() - 1];
  }

  static boolean isReachable(List<Point> points, int fromIndex, int toIndex) {
    return IntStream.range(fromIndex + 1, toIndex)
        .allMatch(
            i ->
                ((i % 2 == 1)
                        && computeCrossProduct(
                                points.get(fromIndex), points.get(toIndex), points.get(i))
                            < EPSILON)
                    || ((i % 2 == 0)
                        && computeCrossProduct(
                                points.get(fromIndex), points.get(toIndex), points.get(i))
                            > -EPSILON));
  }

  static double computeCrossProduct(Point o, Point p1, Point p2) {
    return (p1.x - o.x) * (p2.y - o.y) - (p2.x - o.x) * (p1.y - o.y);
  }

  static double computeDistance(Point p1, Point p2) {
    return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
  }

  static Point computeIntersection(int centerX, int centerY, int radius, double theta) {
    return new Point(centerX + radius * Math.cos(theta), centerY + radius * Math.sin(theta));
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