// https://blog.csdn.net/u014688145/article/details/78125515

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final double EPSILON = 1e-9;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      if (N == 0) {
        break;
      }

      int[] x = new int[N];
      int[] y = new int[N];
      int[] r = new int[N];
      int[] m = new int[N];
      for (int i = 0; i < N; ++i) {
        x[i] = sc.nextInt();
        y[i] = sc.nextInt();
        r[i] = sc.nextInt();
        m[i] = sc.nextInt();
      }

      System.out.println(solve(x, y, r, m));
    }

    sc.close();
  }

  static int solve(int[] x, int[] y, int[] r, int[] m) {
    List<Line> lines = new ArrayList<>();
    for (int i = 0; i < x.length; ++i) {
      for (int j = i + 1; j < x.length; ++j) {
        addLines(
            lines,
            new Circle(new Point(x[i], y[i]), r[i]),
            new Circle(new Point(x[j], y[j]), r[j]));
        addLines(
            lines,
            new Circle(new Point(x[i], y[i]), r[i]),
            new Circle(new Point(x[j], y[j]), r[j] + m[j]));
        addLines(
            lines,
            new Circle(new Point(x[i], y[i]), r[i] + m[i]),
            new Circle(new Point(x[j], y[j]), r[j]));
        addLines(
            lines,
            new Circle(new Point(x[i], y[i]), r[i] + m[i]),
            new Circle(new Point(x[j], y[j]), r[j] + m[j]));
      }
    }

    int result = 1;
    for (Line line : lines) {
      result = Math.max(result, computeCollectedCount(x, y, r, m, line));
    }

    return result;
  }

  static void addLines(List<Line> lines, Circle c1, Circle c2) {
    if (isOutside(c1, c2)) {
      lines.addAll(computeOuterTangentLines(c1, c2));
      lines.addAll(computeInnerTangentLines(c1, c2));
    }
    if (isOverlap(c2, c2)) {
      lines.addAll(computeOuterTangentLines(c1, c2));
      lines.add(computeIntersection(c1, c2));
    }
  }

  static List<Line> computeOuterTangentLines(Circle c1, Circle c2) {
    if (Math.abs(c1.r - c2.r) < EPSILON) {
      return computeOuterTangentParallelLines(c1, c2);
    }
    if (c1.r > c2.r + EPSILON) {
      return computeOuterTangentLines(c2, c1);
    }

    Point base = add(c1.o, scale(subtract(c1.o, c2.o), c1.r / (c2.r - c1.r)));
    List<Point> tangentPoints = tangent(c1, base);

    return List.of(new Line(base, tangentPoints.get(0)), new Line(base, tangentPoints.get(1)));
  }

  static List<Line> computeOuterTangentParallelLines(Circle c1, Circle c2) {
    Point v = scale(rotate(subtract(c2.o, c1.o), Math.PI / 2), c1.r / computeDistance(c1.o, c2.o));

    return List.of(
        new Line(add(c1.o, v), add(c2.o, v)), new Line(subtract(c1.o, v), subtract(c2.o, v)));
  }

  static List<Line> computeInnerTangentLines(Circle c1, Circle c2) {
    Point base = add(c1.o, scale(subtract(c2.o, c1.o), c1.r / (c1.r + c2.r)));
    List<Point> tangentPoints = tangent(c1, base);

    return List.of(new Line(base, tangentPoints.get(0)), new Line(base, tangentPoints.get(1)));
  }

  static List<Point> tangent(Circle c, Point p) {
    double L = computeDistance(c.o, p);
    double M = Math.sqrt(L * L - c.r * c.r);
    Point v = scale(subtract(c.o, p), 1 / L);
    double angle = Math.asin(c.r / L);

    return List.of(add(p, scale(rotate(v, angle), M)), add(p, scale(rotate(v, -angle), M)));
  }

  static Line computeIntersection(Circle c1, Circle c2) {
    double d = computeDistance(c1.o, c2.o);
    Point e = scale(subtract(c2.o, c1.o), 1 / d);
    double angle = Math.acos((d * d + c1.r * c1.r - c2.r * c2.r) / (2 * d * c1.r));

    return new Line(
        add(c1.o, scale(rotate(e, angle), c1.r)), add(c1.o, scale(rotate(e, -angle), c1.r)));
  }

  static boolean isOutside(Circle c1, Circle c2) {
    return computeDistance(c1.o, c2.o) > c1.r + c2.r + EPSILON;
  }

  static boolean isOverlap(Circle c1, Circle c2) {
    return !contains(c1, c2) && !contains(c2, c1) && !isOutside(c1, c2);
  }

  static boolean contains(Circle c1, Circle c2) {
    return computeDistance(c1.o, c2.o) + c2.r < c1.r - EPSILON;
  }

  static int computeCollectedCount(int[] x, int[] y, int[] r, int[] m, Line line) {
    return (int)
        IntStream.range(0, x.length)
            .filter(
                i -> {
                  double distance = computeDistance(new Point(x[i], y[i]), line);

                  return distance >= r[i] - EPSILON && distance <= r[i] + m[i] + EPSILON;
                })
            .count();
  }

  static double computeDistance(Point p, Line l) {
    return Math.abs(computeCrossProduct(subtract(p, l.a), subtract(l.b, l.a)))
        / computeDistance(l.a, l.b);
  }

  static Point add(Point p1, Point p2) {
    return new Point(p1.x + p2.x, p1.y + p2.y);
  }

  static Point subtract(Point p1, Point p2) {
    return new Point(p1.x - p2.x, p1.y - p2.y);
  }

  static Point scale(Point p, double factor) {
    return new Point(p.x * factor, p.y * factor);
  }

  static Point rotate(Point p, double angle) {
    return new Point(
        p.x * Math.cos(angle) - p.y * Math.sin(angle),
        p.x * Math.sin(angle) + p.y * Math.cos(angle));
  }

  static double computeCrossProduct(Point p1, Point p2) {
    return p1.x * p2.y - p1.y * p2.x;
  }

  static double computeDistance(Point p1, Point p2) {
    return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
  }
}

class Circle {
  Point o;
  double r;

  Circle(Point o, double r) {
    this.o = o;
    this.r = r;
  }
}

class Line {
  Point a;
  Point b;

  Line(Point a, Point b) {
    this.a = a;
    this.b = b;
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