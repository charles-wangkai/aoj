// https://www.hankcs.com/program/algorithm/aoj-2201-divide-the-cake.html

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
  static final double EPSILON = 1e-9;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int W = sc.nextInt();
      int H = sc.nextInt();
      int N = sc.nextInt();
      if (W == 0 && H == 0 && N == 0) {
        break;
      }

      int[] xs = new int[2 * N];
      int[] ys = new int[2 * N];
      for (int i = 0; i < 2 * N; ++i) {
        xs[i] = sc.nextInt();
        ys[i] = sc.nextInt();
      }

      System.out.println(String.format("%.10f", solve(W, H, xs, ys)));
    }

    sc.close();
  }

  static double solve(int W, int H, int[] xs, int[] ys) {
    Point[] points = new Point[xs.length];
    for (int i = 0; i < points.length; ++i) {
      points[i] = new Point(xs[i], ys[i]);
    }

    List<Double> intercepts = new ArrayList<>();
    intercepts.add(0.0);
    intercepts.add((double) H);
    for (int i = 0; i < xs.length; ++i) {
      intercepts.add(computeY(points[i], new Point(W, 0), 0));
      intercepts.add(computeY(points[i], new Point(W, H), 0));

      for (int j = i + 1; j < xs.length; ++j) {
        intercepts.add(computeY(points[i], points[j], 0));
      }
    }
    Collections.sort(intercepts);

    double result = 0;
    for (int i = 0; i < intercepts.size() - 1; ++i) {
      if (intercepts.get(i) >= 0
          && intercepts.get(i + 1) <= H
          && intercepts.get(i + 1) > intercepts.get(i) + EPSILON) {
        double middleY = (intercepts.get(i) + intercepts.get(i + 1)) / 2;

        Integer[] sortedIndices = new Integer[points.length];
        for (int j = 0; j < sortedIndices.length; ++j) {
          sortedIndices[j] = j;
        }
        double[] angles = new double[points.length];
        for (int j = 0; j < angles.length; ++j) {
          angles[j] = Math.atan2(ys[j] - middleY, xs[j]);
        }
        Arrays.sort(sortedIndices, Comparator.comparing(index -> angles[index]));

        double lower =
            Math.min(
                H,
                Math.max(
                    0,
                    computeY(
                        new Point(-EPSILON, middleY),
                        points[sortedIndices[sortedIndices.length / 2 - 1]],
                        W)));
        double upper =
            Math.min(
                H,
                Math.max(
                    0,
                    computeY(
                        new Point(-EPSILON, middleY),
                        points[sortedIndices[sortedIndices.length / 2]],
                        W)));
        result += ((intercepts.get(i + 1) - intercepts.get(i)) / H) * ((upper - lower) / H);
      }
    }

    return result;
  }

  static double computeY(Point p1, Point p2, double x) {
    return p1.y + (x - p1.x) / (p2.x - p1.x) * (p2.y - p1.y);
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