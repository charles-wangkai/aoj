import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      int x0 = sc.nextInt();
      int y0 = sc.nextInt();
      int x1 = sc.nextInt();
      int y1 = sc.nextInt();
      int x2 = sc.nextInt();
      int y2 = sc.nextInt();
      int x3 = sc.nextInt();
      int y3 = sc.nextInt();

      System.out.println(solve(x0, y0, x1, y1, x2, y2, x3, y3));
    }

    sc.close();
  }

  static int solve(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
    return isIntersect(new Point(x0, y0), new Point(x1, y1), new Point(x2, y2), new Point(x3, y3))
        ? 1
        : 0;
  }

  static boolean isIntersect(Point a, Point b, Point c, Point d) {
    return Math.max(a.x, b.x) >= Math.min(c.x, d.x)
        && Math.max(c.x, d.x) >= Math.min(a.x, b.x)
        && Math.max(a.y, b.y) >= Math.min(c.y, d.y)
        && Math.max(c.y, d.y) >= Math.min(a.y, b.y)
        && (long) computeCrossProduct(a, b, c) * computeCrossProduct(a, b, d) <= 0
        && (long) computeCrossProduct(c, d, a) * computeCrossProduct(c, d, b) <= 0;
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