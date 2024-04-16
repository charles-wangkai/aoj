// https://en.wikibooks.org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
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

    System.out.println(solve(x, y));

    sc.close();
  }

  static String solve(int[] x, int[] y) {
    Point[] points =
        IntStream.range(0, x.length)
            .mapToObj(i -> new Point(x[i], y[i]))
            .sorted(Comparator.<Point, Integer>comparing(p -> p.x).thenComparing(p -> p.y))
            .toArray(Point[]::new);

    List<Point> upper = new ArrayList<>();
    upper.add(points[0]);
    upper.add(points[1]);
    for (int i = 2; i < points.length; ++i) {
      while (upper.size() >= 2
          && computeCrossProduct(
                  upper.get(upper.size() - 2), upper.get(upper.size() - 1), points[i])
              > 0) {
        upper.remove(upper.size() - 1);
      }

      upper.add(points[i]);
    }

    List<Point> lower = new ArrayList<>();
    lower.add(points[points.length - 1]);
    lower.add(points[points.length - 2]);
    for (int i = points.length - 3; i >= 0; --i) {
      while (lower.size() >= 2
          && computeCrossProduct(
                  lower.get(lower.size() - 2), lower.get(lower.size() - 1), points[i])
              > 0) {
        lower.remove(lower.size() - 1);
      }

      lower.add(points[i]);
    }

    List<Point> convexHull = new ArrayList<>();
    for (int i = lower.size() - 1; i >= 0; --i) {
      convexHull.add(lower.get(i));
    }
    for (int i = upper.size() - 2; i >= 1; --i) {
      convexHull.add(upper.get(i));
    }

    int beginIndex =
        IntStream.range(0, convexHull.size())
            .boxed()
            .min(
                Comparator.<Integer, Integer>comparing(i -> convexHull.get(i).y)
                    .thenComparing(i -> convexHull.get(i).x))
            .get();

    return String.format(
        "%d\n%s",
        convexHull.size(),
        IntStream.range(0, convexHull.size())
            .mapToObj(
                i ->
                    String.format(
                        "%d %d",
                        convexHull.get((beginIndex + i) % convexHull.size()).x,
                        convexHull.get((beginIndex + i) % convexHull.size()).y))
            .collect(Collectors.joining("\n")));
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