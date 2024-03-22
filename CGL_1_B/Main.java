// https://math.stackexchange.com/questions/65503/point-reflection-over-a-line

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int x1 = sc.nextInt();
    int y1 = sc.nextInt();
    int x2 = sc.nextInt();
    int y2 = sc.nextInt();
    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      int x = sc.nextInt();
      int y = sc.nextInt();

      System.out.println(solve(x1, y1, x2, y2, x, y));
    }

    sc.close();
  }

  static String solve(int x1, int y1, int x2, int y2, int x, int y) {
    int dx = x2 - x1;
    int dy = y2 - y1;

    double a = (double) (dx * dx - dy * dy) / (dx * dx + dy * dy);
    double b = 2.0 * dx * dy / (dx * dx + dy * dy);

    double rx = a * (x - x1) + b * (y - y1) + x1;
    double ry = b * (x - x1) - a * (y - y1) + y1;

    return String.format("%.9f %.9f", rx, ry);
  }
}