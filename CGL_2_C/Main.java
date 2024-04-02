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

  static String solve(int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
    int dx1 = x1 - x0;
    int dy1 = y1 - y0;
    int dx2 = x3 - x2;
    int dy2 = y3 - y2;
    int denom = dx1 * dy2 - dy1 * dx2;

    double x =
        ((double) ((long) dx2 * (dx1 * y0 - dy1 * x0) - (long) dx1 * (dx2 * y2 - dy2 * x2))
            / denom);
    double y =
        ((double) ((long) dy1 * (dy2 * x2 - dx2 * y2) - (long) dy2 * (dy1 * x0 - dx1 * y0))
            / denom);

    return String.format("%.9f %.9f", x, y);
  }
}