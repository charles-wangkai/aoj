import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int d = sc.nextInt();
      if (d == 0) {
        break;
      }

      int n = sc.nextInt();
      int m = sc.nextInt();
      int[] ds = new int[n - 1];
      for (int i = 0; i < ds.length; ++i) {
        ds[i] = sc.nextInt();
      }
      int[] k = new int[m];
      for (int i = 0; i < k.length; ++i) {
        k[i] = sc.nextInt();
      }

      System.out.println(solve(d, ds, k));
    }

    sc.close();
  }

  static int solve(int d, int[] ds, int[] k) {
    int[] stores = IntStream.concat(Arrays.stream(ds), IntStream.of(0, d)).sorted().toArray();

    return Arrays.stream(k)
        .map(
            ki -> {
              int index = Arrays.binarySearch(stores, ki);
              if (index >= 0) {
                return 0;
              }

              index = -index - 1;

              return Math.min(ki - stores[index - 1], stores[index] - ki);
            })
        .sum();
  }
}