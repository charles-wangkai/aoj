import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int t = sc.nextInt();
    for (int tc = 0; tc < t; ++tc) {
      int n = sc.nextInt();
      int k = sc.nextInt();
      int[] x = new int[n];
      for (int i = 0; i < x.length; ++i) {
        x[i] = sc.nextInt();
      }

      System.out.println(solve(x, k));
    }

    sc.close();
  }

  static int solve(int[] x, int k) {
    return (x[x.length - 1] - x[0])
        - IntStream.range(0, x.length - 1)
            .map(i -> x[i + 1] - x[i])
            .boxed()
            .sorted(Comparator.reverseOrder())
            .mapToInt(Integer::intValue)
            .limit(k - 1)
            .sum();
  }
}