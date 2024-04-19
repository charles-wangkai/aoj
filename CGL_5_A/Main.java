import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    double[] x = new double[n];
    double[] y = new double[n];
    for (int i = 0; i < n; ++i) {
      st = new StringTokenizer(br.readLine());
      x[i] = Double.parseDouble(st.nextToken());
      y[i] = Double.parseDouble(st.nextToken());
    }

    System.out.println(String.format("%.9f", solve(x, y)));
  }

  static double solve(double[] x, double[] y) {
    return computeMinDistance(
        IntStream.range(0, x.length)
            .mapToObj(i -> new Point(x[i], y[i]))
            .sorted(Comparator.comparing(point -> point.x))
            .toArray(Point[]::new),
        0,
        x.length - 1);
  }

  static double computeMinDistance(Point[] points, int beginIndex, int endIndex) {
    if (beginIndex == endIndex) {
      return Double.MAX_VALUE;
    }

    int middleIndex = (beginIndex + endIndex) / 2;
    double result =
        Math.min(
            computeMinDistance(points, beginIndex, middleIndex),
            computeMinDistance(points, middleIndex + 1, endIndex));

    double result_ = result;
    Point[] candidates =
        IntStream.rangeClosed(beginIndex, endIndex)
            .mapToObj(i -> points[i])
            .filter(point -> Math.abs(point.x - points[middleIndex].x) < result_)
            .sorted(Comparator.comparing(point -> point.y))
            .toArray(Point[]::new);
    for (int i = 0; i < candidates.length; ++i) {
      for (int j = i + 1;
          j < candidates.length && candidates[j].y - candidates[i].y < result;
          ++j) {
        result =
            Math.min(
                result,
                Math.sqrt(
                    (candidates[j].x - candidates[i].x) * (candidates[j].x - candidates[i].x)
                        + (candidates[j].y - candidates[i].y)
                            * (candidates[j].y - candidates[i].y)));
      }
    }

    return result;
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