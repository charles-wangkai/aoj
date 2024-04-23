import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    double[] x = new double[n];
    double[] y = new double[n];
    for (int i = 0; i < n; ++i) {
      st = new StringTokenizer(br.readLine());
      x[i] = Double.parseDouble(st.nextToken());
      y[i] = Double.parseDouble(st.nextToken());
    }

    System.out.println(String.format("%.9f", solve(x, y)));
  }

  static double solve(double[] x, double[] y) {
    List<Point> convexHull =
        buildConvexHull(
            IntStream.range(0, x.length)
                .mapToObj(i -> new Point(x[i], y[i]))
                .toArray(Point[]::new));
    if (convexHull.size() == 2) {
      return computeDistance(convexHull.get(0), convexHull.get(1));
    }

    int i = 0;
    int j = 0;
    for (int k = 0; k < convexHull.size(); ++k) {
      if (!compare(convexHull.get(i), convexHull.get(k))) {
        i = k;
      }
      if (compare(convexHull.get(j), convexHull.get(k))) {
        j = k;
      }
    }

    double result = 0;
    int si = i;
    int sj = j;
    do {
      result = Math.max(result, computeDistance(convexHull.get(i), convexHull.get(j)));

      if (computeCrossProduct(
              convexHull.get(i),
              convexHull.get((i + 1) % convexHull.size()),
              convexHull.get(j),
              convexHull.get((j + 1) % convexHull.size()))
          < 0) {
        i = (i + 1) % convexHull.size();
      } else {
        j = (j + 1) % convexHull.size();
      }
    } while (i != si || j != sj);

    return result;
  }

  static boolean compare(Point p1, Point p2) {
    return (p1.x != p2.x) ? (p1.x < p2.x) : (p1.y < p2.y);
  }

  static double computeDistance(Point p1, Point p2) {
    return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
  }

  static List<Point> buildConvexHull(Point[] points) {
    int zIndex = 0;
    for (int i = 1; i < points.length; ++i) {
      if (points[i].y < points[zIndex].y
          || (points[i].y == points[zIndex].y && points[i].x < points[zIndex].x)) {
        zIndex = i;
      }
    }
    final Point z = points[zIndex];

    List<Point> sortedPoints = new ArrayList<>();
    for (int i = 0; i < points.length; ++i) {
      if (i != zIndex) {
        sortedPoints.add(points[i]);
      }
    }
    Collections.sort(sortedPoints, Comparator.comparing(point -> computeAngle(z, point)));
    sortedPoints.add(z);

    List<Point> result = new ArrayList<>();
    result.add(z);
    result.add(sortedPoints.get(0));

    for (int i = 1; i < sortedPoints.size(); ++i) {
      Point p = sortedPoints.get(i);

      if (result.size() >= 2
          && isOnSegment(result.get(result.size() - 2), result.get(result.size() - 1), p)) {
        continue;
      }

      while (result.size() >= 2
          && computeCrossProduct(result.get(result.size() - 2), result.get(result.size() - 1), p)
              <= 0) {
        result.remove(result.size() - 1);
      }

      if (i != sortedPoints.size() - 1) {
        result.add(p);
      }
    }

    return result;
  }

  static double computeAngle(Point o, Point p) {
    double result = Math.atan2(p.y - o.y, p.x - o.x);
    if (result < 0) {
      result += 2 * Math.PI;
    }

    return result;
  }

  static boolean isOnSegment(Point a, Point b, Point p) {
    return computeCrossProduct(a, b, p) == 0
        && isBetween(p.x, a.x, b.x)
        && isBetween(p.y, a.y, b.y);
  }

  static double computeCrossProduct(Point o, Point p1, Point p2) {
    return (p1.x - o.x) * (p2.y - o.y) - (p2.x - o.x) * (p1.y - o.y);
  }

  static double computeCrossProduct(Point o1, Point p1, Point o2, Point p2) {
    return (p1.x - o1.x) * (p2.y - o2.y) - (p2.x - o2.x) * (p1.y - o1.y);
  }

  static boolean isBetween(double s, double v1, double v2) {
    return s >= Math.min(v1, v2) && s <= Math.max(v1, v2);
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