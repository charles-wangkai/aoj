import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int x1 = sc.nextInt();
    int y1 = sc.nextInt();
    int x2 = sc.nextInt();
    int y2 = sc.nextInt();
    int q = sc.nextInt();
    for (int i = 0; i < q; ++i) {
      int x = sc.nextInt();
      int y = sc.nextInt();

      System.out.println(solve(x1, y1, x2, y2, x, y));
    }

    sc.close();
  }

  static String solve(int x1, int y1, int x2, int y2, int x, int y) {
    double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

    double dx = (x2 - x1) / d;
    double dy = (y2 - y1) / d;

    double px = ((x - x1) * dx + (y - y1) * dy) * dx + x1;
    double py = ((x - x1) * dx + (y - y1) * dy) * dy + y1;

    return String.format("%.9f %.9f", px, py);
  }
}