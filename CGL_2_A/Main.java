import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int q = sc.nextInt();
    for (int tc = 0; tc < q; ++tc) {
      int[] x = new int[4];
      int[] y = new int[4];
      for (int i = 0; i < 4; ++i) {
        x[i] = sc.nextInt();
        y[i] = sc.nextInt();
      }

      System.out.println(solve(x, y));
    }

    sc.close();
  }

  static int solve(int[] x, int[] y) {
    int vx1 = x[1] - x[0];
    int vy1 = y[1] - y[0];
    int vx2 = x[3] - x[2];
    int vy2 = y[3] - y[2];

    if (vx1 * vx2 + vy1 * vy2 == 0) {
      return 1;
    }
    if (vx1 * vy2 - vy1 * vx2 == 0) {
      return 2;
    }

    return 0;
  }
}