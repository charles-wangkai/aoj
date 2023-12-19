import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final double EPSILON = 1e-9;
  static final double G = 9.8;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int V = sc.nextInt();
    int X = sc.nextInt();
    int Y = sc.nextInt();
    int[] L = new int[N];
    int[] B = new int[N];
    int[] R = new int[N];
    int[] T = new int[N];
    for (int i = 0; i < N; ++i) {
      L[i] = sc.nextInt();
      B[i] = sc.nextInt();
      R[i] = sc.nextInt();
      T[i] = sc.nextInt();
    }

    System.out.println(solve(L, B, R, T, V, X, Y) ? "Yes" : "No");

    sc.close();
  }

  static boolean solve(int[] L, int[] B, int[] R, int[] T, int V, int X, int Y) {
    Rectangle[] rectangles =
        IntStream.range(0, L.length)
            .filter(i -> L[i] <= X)
            .mapToObj(i -> new Rectangle(L[i], B[i], Math.min(X, R[i]), T[i]))
            .toArray(Rectangle[]::new);

    return check(V, X, Y, rectangles, X, Y)
        || Arrays.stream(rectangles)
            .anyMatch(
                rectangle ->
                    check(V, X, Y, rectangles, rectangle.minX, rectangle.maxY)
                        || check(V, X, Y, rectangles, rectangle.maxX, rectangle.maxY));
  }

  static boolean check(int V, int X, int Y, Rectangle[] rectangles, int qx, int qy) {
    double a = G * G / 4;
    double b = G * qy - V * V;
    int c = qx * qx + qy * qy;
    double D = b * b - 4 * a * c;
    if (D <= 0) {
      return false;
    }

    for (int d : new int[] {-1, 1}) {
      double t2 = (-b + d * Math.sqrt(D)) / (2 * a);
      if (t2 > 0) {
        double t = Math.sqrt(t2);
        double vx = qx / t;
        double vy = (qy + G * t * t / 2) / t;

        double yt = computeY(vy, X / vx);
        if (yt >= Y - EPSILON
            && Arrays.stream(rectangles)
                .allMatch(
                    rectangle -> {
                      int yL =
                          compare(
                              rectangle.minY, rectangle.maxY, computeY(vy, rectangle.minX / vx));
                      int yR =
                          compare(
                              rectangle.minY, rectangle.maxY, computeY(vy, rectangle.maxX / vx));
                      int xH = compare(rectangle.minX, rectangle.maxX, vx * (vy / G));
                      int yH = compare(rectangle.minY, rectangle.maxY, computeY(vy, vy / G));

                      return !(rectangle.maxX == X && rectangle.maxY >= Y && rectangle.minY <= yt)
                          && yL * yR > 0
                          && !(xH == 0 && yH >= 0 && yL < 0);
                    })) {

          return true;
        }
      }
    }

    return false;
  }

  static int compare(double lower, double upper, double a) {
    if (a < lower + EPSILON) {
      return -1;
    }
    if (a > upper - EPSILON) {
      return 1;
    }

    return 0;
  }

  static double computeY(double vy, double t) {
    return vy * t - G * t * t / 2;
  }
}

class Rectangle {
  int minX;
  int minY;
  int maxX;
  int maxY;

  Rectangle(int minX, int minY, int maxX, int maxY) {
    this.minX = minX;
    this.minY = minY;
    this.maxX = maxX;
    this.maxY = maxY;
  }
}