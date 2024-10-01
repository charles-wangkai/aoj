import java.util.Arrays;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] x = new int[n];
    for (int i = 0; i < x.length; ++i) {
      x[i] = sc.nextInt();
    }

    System.out.println(solve(x));

    sc.close();
  }

  static long solve(int[] x) {
    NavigableMap<Integer, Long> lastToSum = new TreeMap<>();
    for (int xi : x) {
      Integer prevLast = lastToSum.lowerKey(xi);
      long sum = xi + ((prevLast == null) ? 0 : lastToSum.get(prevLast));

      while (true) {
        Integer nextLast = lastToSum.higherKey(xi);
        if (nextLast == null || lastToSum.get(nextLast) > sum) {
          break;
        }

        lastToSum.remove(nextLast);
      }

      lastToSum.put(xi, sum);
    }

    return Arrays.stream(x).asLongStream().sum() - lastToSum.lastEntry().getValue();
  }
}