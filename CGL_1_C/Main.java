import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int x0 = sc.nextInt();
    int y0 = sc.nextInt();
    int x1 = sc.nextInt();
    int y1 = sc.nextInt();
    int q = sc.nextInt();
    for (int i = 0; i < q; ++i) {
      int x2 = sc.nextInt();
      int y2 = sc.nextInt();

      System.out.println(solve(x0, y0, x1, y1, x2, y2));
    }

    sc.close();
  }

  static String solve(int x0, int y0, int x1, int y1, int x2, int y2) {
    long crossProduct =
        computeCrossProduct(new Point(x0, y0), new Point(x1, y1), new Point(x2, y2));
    if (crossProduct < 0) {
      return "CLOCKWISE";
    }
    if (crossProduct > 0) {
      return "COUNTER_CLOCKWISE";
    }

    if (x2 >= Math.min(x0, x1)
        && x2 <= Math.max(x0, x1)
        && y2 >= Math.min(y0, y1)
        && y2 <= Math.max(y0, y1)) {
      return "ON_SEGMENT";
    }

    return ((long) (x2 - x0) * (x2 - x0) + (long) (y2 - y0) * (y2 - y0)
            < (long) (x2 - x1) * (x2 - x1) + (long) (y2 - y1) * (y2 - y1))
        ? "ONLINE_BACK"
        : "ONLINE_FRONT";
  }

  static long computeCrossProduct(Point o, Point p1, Point p2) {
    return (long) (p1.x - o.x) * (p2.y - o.y) - (long) (p2.x - o.x) * (p1.y - o.y);
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