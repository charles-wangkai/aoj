// https://stackoverflow.com/questions/2824478/shortest-distance-between-two-line-segments/2824596#2824596

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.DoubleStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int W = sc.nextInt();
      int N = sc.nextInt();
      if (W == 0 && N == 0) {
        break;
      }

      int[][] x = new int[N][];
      int[][] y = new int[N][];
      for (int i = 0; i < N; ++i) {
        int M = sc.nextInt();
        x[i] = new int[M];
        y[i] = new int[M];
        for (int j = 0; j < M; ++j) {
          x[i][j] = sc.nextInt();
          y[i][j] = sc.nextInt();
        }
      }

      System.out.println(String.format("%.9f", solve(W, x, y)));
    }

    sc.close();
  }

  static double solve(int W, int[][] x, int[][] y) {
    int N = x.length;

    double[][] adjMatrix = new double[N + 2][N + 2];
    adjMatrix[0][N + 1] = W;
    adjMatrix[N + 1][0] = W;
    for (int i = 1; i <= N; ++i) {
      int minX = Arrays.stream(x[i - 1]).min().getAsInt();
      adjMatrix[0][i] = minX;
      adjMatrix[i][0] = minX;

      int maxX = Arrays.stream(x[i - 1]).max().getAsInt();
      adjMatrix[i][N + 1] = W - maxX;
      adjMatrix[N + 1][i] = W - maxX;
    }
    for (int i = 1; i <= N; ++i) {
      for (int j = i + 1; j <= N; ++j) {
        double polygonDistance = computePolygonDistance(x[i - 1], y[i - 1], x[j - 1], y[j - 1]);
        adjMatrix[i][j] = polygonDistance;
        adjMatrix[j][i] = polygonDistance;
      }
    }

    return computeMinDistance(adjMatrix);
  }

  static double computeMinDistance(double[][] adjMatrix) {
    double[] distances = new double[adjMatrix.length];
    Arrays.fill(distances, -1);
    PriorityQueue<Element> pq =
        new PriorityQueue<>(Comparator.comparing(element -> element.distance));
    pq.offer(new Element(0, 0));
    while (!pq.isEmpty()) {
      Element head = pq.poll();
      if (distances[head.node] < 0) {
        distances[head.node] = head.distance;
        for (int i = 0; i < adjMatrix.length; ++i) {
          if (distances[i] < 0) {
            pq.offer(new Element(i, head.distance + adjMatrix[head.node][i]));
          }
        }
      }
    }

    return distances[distances.length - 1];
  }

  static double computePolygonDistance(int[] xs1, int[] ys1, int[] xs2, int[] ys2) {
    double result = Double.MAX_VALUE;
    for (int i = 0; i < xs1.length; ++i) {
      for (int j = i + 1; j < xs1.length; ++j) {
        for (int p = 0; p < xs2.length; ++p) {
          for (int q = p + 1; q < xs2.length; ++q) {
            result =
                Math.min(
                    result,
                    computeDistanceBetweenSegments(
                        xs1[i], ys1[i], xs1[j], ys1[j], xs2[p], ys2[p], xs2[q], ys2[q]));
          }
        }
      }
    }

    return result;
  }

  static double computeDistanceBetweenSegments(
      int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
    Point p0 = new Point(x0, y0);
    Point p1 = new Point(x1, y1);
    Point p2 = new Point(x2, y2);
    Point p3 = new Point(x3, y3);

    if (isIntersect(p0, p1, p2, p3)) {
      return 0;
    }

    return DoubleStream.of(
            computeDistanceBetweenPointAndSegment(p0, p2, p3),
            computeDistanceBetweenPointAndSegment(p1, p2, p3),
            computeDistanceBetweenPointAndSegment(p2, p0, p1),
            computeDistanceBetweenPointAndSegment(p3, p0, p1))
        .min()
        .getAsDouble();
  }

  static double computeDistanceBetweenPointAndSegment(Point p, Point a, Point b) {
    double d = Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));

    double dx = (b.x - a.x) / d;
    double dy = (b.y - a.y) / d;

    double projectionX = ((p.x - a.x) * dx + (p.y - a.y) * dy) * dx + a.x;
    double projectionY = ((p.x - a.x) * dx + (p.y - a.y) * dy) * dy + a.y;

    return (computeDistance(projectionX, projectionY, a.x, a.y)
                <= computeDistance(a.x, a.y, b.x, b.y)
            && computeDistance(projectionX, projectionY, b.x, b.y)
                <= computeDistance(a.x, a.y, b.x, b.y))
        ? computeDistance(p.x, p.y, projectionX, projectionY)
        : Math.min(computeDistance(p.x, p.y, a.x, a.y), computeDistance(p.x, p.y, b.x, b.y));
  }

  static double computeDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
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

class Element {
  int node;
  double distance;

  Element(int node, double distance) {
    this.node = node;
    this.distance = distance;
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