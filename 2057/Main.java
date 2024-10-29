// https://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=1110780

import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final double ANGLE = 1;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      if (N == 0) {
        break;
      }

      double[] R = new double[N];
      double[] X = new double[N];
      double[] Y = new double[N];
      for (int i = 0; i < N; ++i) {
        R[i] = sc.nextDouble();
        X[i] = sc.nextDouble();
        Y[i] = sc.nextDouble();
      }

      System.out.println(String.format("%.9f", solve(R, X, Y)));
    }

    sc.close();
  }

  static double solve(double[] R, double[] X, double[] Y) {
    for (int i = 0; i < X.length; ++i) {
      double rotatedX = Math.cos(ANGLE) * X[i] - Math.sin(ANGLE) * Y[i];
      double rotatedY = Math.sin(ANGLE) * X[i] + Math.cos(ANGLE) * Y[i];

      X[i] = rotatedX;
      Y[i] = rotatedY;
    }

    int[] sortedIndices =
        IntStream.range(0, R.length)
            .boxed()
            .sorted(Comparator.comparing(i -> X[i] + R[i]))
            .mapToInt(Integer::intValue)
            .toArray();

    double result = Double.MAX_VALUE;
    for (int i = 0; i < sortedIndices.length; ++i) {
      for (int j = i - 1; j >= 0; --j) {
        if (X[sortedIndices[i]] - X[sortedIndices[j]] - R[sortedIndices[i]] - R[sortedIndices[j]]
            > result) {
          break;
        }

        result =
            Math.min(
                result,
                Math.sqrt(
                        (X[sortedIndices[i]] - X[sortedIndices[j]])
                                * (X[sortedIndices[i]] - X[sortedIndices[j]])
                            + (Y[sortedIndices[i]] - Y[sortedIndices[j]])
                                * (Y[sortedIndices[i]] - Y[sortedIndices[j]]))
                    - R[sortedIndices[i]]
                    - R[sortedIndices[j]]);
      }
    }

    return result;
  }
}