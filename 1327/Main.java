import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int A = sc.nextInt();
      int B = sc.nextInt();
      int C = sc.nextInt();
      int T = sc.nextInt();
      if (N == 0 && M == 0 && A == 0 && B == 0 && C == 0 && T == 0) {
        break;
      }

      int[] S = new int[N];
      for (int i = 0; i < S.length; ++i) {
        S[i] = sc.nextInt();
      }

      System.out.println(solve(S, M, A, B, C, T));
    }

    sc.close();
  }

  static String solve(int[] S, int M, int A, int B, int C, int T) {
    int N = S.length;

    int[][] transition = new int[N][N];
    for (int i = 0; i < N; ++i) {
      if (i != 0) {
        transition[i - 1][i] = A;
      }
      transition[i][i] = B;
      if (i != N - 1) {
        transition[i + 1][i] = C;
      }
    }

    return Arrays.stream(multiply(S, pow(transition, T, M), M))
        .mapToObj(String::valueOf)
        .collect(Collectors.joining(" "));
  }

  static int addMod(int x, int y, int modulus) {
    return Math.floorMod(x + y, modulus);
  }

  static int multiplyMod(int x, int y, int modulus) {
    return Math.floorMod(x * y, modulus);
  }

  static int[] multiply(int[] v, int[][] m, int modulus) {
    int N = v.length;

    return IntStream.range(0, N)
        .map(
            i ->
                IntStream.range(0, N)
                    .map(j -> multiplyMod(v[j], m[j][i], modulus))
                    .reduce((acc, x) -> addMod(acc, x, modulus))
                    .getAsInt())
        .toArray();
  }

  static int[][] multiply(int[][] m1, int[][] m2, int modulus) {
    int N = m1.length;

    int[][] result = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        for (int k = 0; k < N; ++k) {
          result[i][j] = addMod(result[i][j], multiplyMod(m1[i][k], m2[k][j], modulus), modulus);
        }
      }
    }

    return result;
  }

  static int[][] pow(int[][] base, int exponent, int modulus) {
    int N = base.length;

    if (exponent == 0) {
      int[][] result = new int[N][N];
      for (int i = 0; i < N; ++i) {
        result[i][i] = 1;
      }

      return result;
    }

    return (exponent % 2 == 0)
        ? pow(multiply(base, base, modulus), exponent / 2, modulus)
        : multiply(base, pow(base, exponent - 1, modulus), modulus);
  }
}