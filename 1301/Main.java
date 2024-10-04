// https://en.wikipedia.org/wiki/Malfatti_circles#Radius_formula
// https://en.wikipedia.org/wiki/Incircle_and_excircles#Cartesian_coordinates

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int x1 = sc.nextInt();
      int y1 = sc.nextInt();
      int x2 = sc.nextInt();
      int y2 = sc.nextInt();
      int x3 = sc.nextInt();
      int y3 = sc.nextInt();
      if (x1 == 0 && y1 == 0 && x2 == 0 && y2 == 0 && x3 == 0 && y3 == 0) {
        break;
      }

      System.out.println(solve(x1, y1, x2, y2, x3, y3));
    }

    sc.close();
  }

  static String solve(int x1, int y1, int x2, int y2, int x3, int y3) {
    double a = computeDistance(x2, y2, x3, y3);
    double b = computeDistance(x3, y3, x1, y1);
    double c = computeDistance(x1, y1, x2, y2);
    double s = (a + b + c) / 2;
    double r = Math.sqrt((s - a) * (s - b) * (s - c) / s);

    double centerX = (a * x1 + b * x2 + c * x3) / (a + b + c);
    double centerY = (a * y1 + b * y2 + c * y3) / (a + b + c);

    double d = computeDistance(centerX, centerY, x1, y1);
    double e = computeDistance(centerX, centerY, x2, y2);
    double f = computeDistance(centerX, centerY, x3, y3);

    double r1 = r / (2 * (s - a)) * (s - r + d - e - f);
    double r2 = r / (2 * (s - b)) * (s - r - d + e - f);
    double r3 = r / (2 * (s - c)) * (s - r - d - e + f);

    return String.format("%.9f %.9f %.9f", r1, r2, r3);
  }

  static double computeDistance(double ax, double ay, double bx, double by) {
    return Math.sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by));
  }
}