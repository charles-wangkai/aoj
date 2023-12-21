// https://en.wikipedia.org/wiki/Simpson%27s_rule

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final double EPSILON = 1e-12;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int m = sc.nextInt();
      int n = sc.nextInt();
      if (m == 0 && n == 0) {
        break;
      }

      int[] x1 = new int[m];
      int[] y1 = new int[m];
      for (int i = 0; i < m; ++i) {
        x1[i] = sc.nextInt();
        y1[i] = sc.nextInt();
      }
      int[] x2 = new int[n];
      int[] y2 = new int[n];
      for (int i = 0; i < n; ++i) {
        x2[i] = sc.nextInt();
        y2[i] = sc.nextInt();
      }

      System.out.println(String.format("%.9f", solve(x1, y1, x2, y2)));
    }

    sc.close();
  }

  static double solve(int[] x1, int[] y1, int[] x2, int[] y2) {
    int[] xs = IntStream.concat(Arrays.stream(x1), Arrays.stream(x2)).distinct().sorted().toArray();

    return IntStream.range(0, xs.length - 1)
        .mapToDouble(
            i -> {
              double a = xs[i] + EPSILON;
              double b = xs[i + 1] - EPSILON;
              double c = (a + b) / 2.0;
              double fa = computeWidth(x1, y1, a) * computeWidth(x2, y2, a);
              double fb = computeWidth(x1, y1, b) * computeWidth(x2, y2, b);
              double fc = computeWidth(x1, y1, c) * computeWidth(x2, y2, c);

              return (b - a) / 6.0 * (fa + 4 * fc + fb);
            })
        .sum();
  }

  static double computeWidth(int[] x, int[] y, double X) {
    double minY = Double.MAX_VALUE;
    double maxY = -Double.MAX_VALUE;
    for (int i = 0; i < x.length; ++i) {
      int x1 = x[i];
      int y1 = y[i];
      int x2 = x[(i + 1) % x.length];
      int y2 = y[(i + 1) % x.length];
      if ((x1 - X) * (x2 - X) <= 0 && x1 != x2) {
        double Y = y1 + (y2 - y1) * (X - x1) / (x2 - x1);
        minY = Math.min(minY, Y);
        maxY = Math.max(maxY, Y);
      }
    }

    return Math.max(0, maxY - minY);
  }
}