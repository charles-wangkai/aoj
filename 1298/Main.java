import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      int m = sc.nextInt();
      if (n == 0 && m == 0) {
        break;
      }

      int[] blackXs = new int[n];
      int[] blackYs = new int[n];
      for (int i = 0; i < n; ++i) {
        blackXs[i] = sc.nextInt();
        blackYs[i] = sc.nextInt();
      }
      int[] whiteXs = new int[m];
      int[] whiteYs = new int[m];
      for (int i = 0; i < m; ++i) {
        whiteXs[i] = sc.nextInt();
        whiteYs[i] = sc.nextInt();
      }

      System.out.println(solve(blackXs, blackYs, whiteXs, whiteYs) ? "YES" : "NO");
    }

    sc.close();
  }

  static boolean solve(int[] blackXs, int[] blackYs, int[] whiteXs, int[] whiteYs) {
    return (blackXs.length == 1 && whiteXs.length == 1)
        || (blackXs.length != 1 && canSeparate(blackXs, blackYs, whiteXs, whiteYs))
        || (whiteXs.length != 1 && canSeparate(whiteXs, whiteYs, blackXs, blackYs));
  }

  static boolean canSeparate(int[] xs1, int[] ys1, int[] xs2, int[] ys2) {
    for (int i = 0; i < xs1.length; ++i) {
      for (int j = i + 1; j < xs1.length; ++j) {
        if (check(xs1, ys1, xs2, ys2, i, j)) {
          return true;
        }
      }
    }

    return false;
  }

  static boolean check(int[] xs1, int[] ys1, int[] xs2, int[] ys2, int index1, int index2) {
    boolean leftSeen1 = false;
    boolean rightSeen1 = false;
    int xMinValue = Integer.MAX_VALUE;
    int xMaxValue = Integer.MIN_VALUE;
    int yMinValue = Integer.MAX_VALUE;
    int yMaxValue = Integer.MIN_VALUE;
    for (int k = 0; k < xs1.length; ++k) {
      int cp =
          computeCrossProduct(
              new Point(xs1[index1], ys1[index1]),
              new Point(xs1[index2], ys1[index2]),
              new Point(xs1[k], ys1[k]));
      if (cp < 0) {
        rightSeen1 = true;
      } else if (cp > 0) {
        leftSeen1 = true;
      } else {
        xMinValue = Math.min(xMinValue, xs1[k]);
        xMaxValue = Math.max(xMaxValue, xs1[k]);
        yMinValue = Math.min(yMinValue, ys1[k]);
        yMaxValue = Math.max(yMaxValue, ys1[k]);
      }
    }
    if (leftSeen1 && rightSeen1) {
      return false;
    }

    boolean leftSeen2 = false;
    boolean rightSeen2 = false;
    List<Point> colinears = new ArrayList<>();
    for (int k = 0; k < xs2.length; ++k) {
      int cp =
          computeCrossProduct(
              new Point(xs1[index1], ys1[index1]),
              new Point(xs1[index2], ys1[index2]),
              new Point(xs2[k], ys2[k]));
      if (cp < 0) {
        rightSeen2 = true;
      } else if (cp > 0) {
        leftSeen2 = true;
      } else {
        colinears.add(new Point(xs2[k], ys2[k]));
      }
    }

    return !((leftSeen2 && rightSeen2) || (leftSeen1 && leftSeen2) || (rightSeen1 && rightSeen2))
        && canSplit(xMinValue, xMaxValue, colinears.stream().mapToInt(p -> p.x).toArray())
        && canSplit(yMinValue, yMaxValue, colinears.stream().mapToInt(p -> p.y).toArray());
  }

  static boolean canSplit(int minValue, int maxValue, int[] values) {
    boolean lessSeen = false;
    boolean greaterSeen = false;
    for (int value : values) {
      if (value > minValue && value < maxValue) {
        return false;
      }
      if (value < minValue) {
        lessSeen = true;
      } else if (value > maxValue) {
        greaterSeen = true;
      }
    }

    return !(lessSeen && greaterSeen);
  }

  static int computeCrossProduct(Point o, Point p1, Point p2) {
    return (p1.x - o.x) * (p2.y - o.y) - (p2.x - o.x) * (p1.y - o.y);
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