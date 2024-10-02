import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      int[] d = new int[n];
      int[] v = new int[n];
      for (int i = 0; i < n; ++i) {
        d[i] = sc.nextInt();
        v[i] = sc.nextInt();
      }

      System.out.println(solve(d, v));
    }

    sc.close();
  }

  static String solve(int[] d, int[] v) {
    int n = d.length;

    BigInteger[] values =
        IntStream.range(0, n)
            .mapToObj(
                i ->
                    IntStream.range(0, n)
                        .map(j -> (j == i) ? d[j] : v[j])
                        .mapToObj(BigInteger::valueOf)
                        .reduce(BigInteger::multiply)
                        .get())
            .toArray(BigInteger[]::new);
    BigInteger lcm =
        Arrays.stream(values).reduce((acc, x) -> acc.divide(acc.gcd(x)).multiply(x)).get();

    return Arrays.stream(values)
        .mapToInt(value -> lcm.divide(value).intValue())
        .mapToObj(String::valueOf)
        .collect(Collectors.joining("\n"));
  }
}