import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int LIMIT = 10000;

  static int[] counts;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      System.out.println(solve(n));
    }

    sc.close();
  }

  static void precompute() {
    boolean[] isPrimes = new boolean[LIMIT + 1];
    Arrays.fill(isPrimes, true);
    for (int i = 2; i < isPrimes.length; ++i) {
      if (isPrimes[i]) {
        for (int j = i * i; j < isPrimes.length; j += i) {
          isPrimes[j] = false;
        }
      }
    }

    int[] primes = IntStream.range(2, isPrimes.length).filter(i -> isPrimes[i]).toArray();

    counts = new int[LIMIT + 1];
    for (int i = 0; i < primes.length; ++i) {
      int sum = 0;
      for (int j = i; j < primes.length; ++j) {
        sum += primes[j];
        if (sum > LIMIT) {
          break;
        }

        ++counts[sum];
      }
    }
  }

  static int solve(int n) {
    return counts[n];
  }
}