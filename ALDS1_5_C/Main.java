import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  static final Point BEGIN = new Point(0, 0);
  static final Point END = new Point(100, 0);

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();

    System.out.println(solve(n));

    sc.close();
  }

  static String solve(int n) {
    return Stream.of(Stream.of(BEGIN), divide(BEGIN, END, n).stream(), Stream.of(END))
        .flatMap(x -> x)
        .map(p -> String.format("%.9f %.9f", p.x, p.y))
        .collect(Collectors.joining("\n"));
  }

  static List<Point> divide(Point p1, Point p2, int n) {
    if (n == 0) {
      return List.of();
    }

    double dx = (p2.x - p1.x) / 3;
    double dy = (p2.y - p1.y) / 3;

    Point s = new Point(p1.x + dx, p1.y + dy);
    Point u =
        new Point(
            s.x + (dx * Math.cos(Math.PI / 3) - dy * Math.sin(Math.PI / 3)),
            s.y + (dx * Math.sin(Math.PI / 3) + dy * Math.cos(Math.PI / 3)));
    Point t = new Point(p1.x + 2 * dx, p1.y + 2 * dy);

    return Stream.of(
            divide(p1, s, n - 1).stream(),
            Stream.of(s),
            divide(s, u, n - 1).stream(),
            Stream.of(u),
            divide(u, t, n - 1).stream(),
            Stream.of(t),
            divide(t, p2, n - 1).stream())
        .flatMap(x -> x)
        .collect(Collectors.toList());
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