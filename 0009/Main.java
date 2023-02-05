import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
  static final int LIMIT = 1_000_000;

  static int[] primes;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    while (sc.hasNextInt()) {
      int n = sc.nextInt();

      System.out.println(solve(n));
    }

    sc.close();
  }

  static void precompute() {
    boolean[] isPrimes = new boolean[LIMIT];
    Arrays.fill(isPrimes, true);
    for (int i = 2; i < isPrimes.length; ++i) {
      if (isPrimes[i]) {
        for (int j = i + i; j < isPrimes.length; j += i) {
          isPrimes[j] = false;
        }
      }
    }

    primes = IntStream.range(2, isPrimes.length).filter(i -> isPrimes[i]).toArray();
  }

  static int solve(int n) {
    return (int) Arrays.stream(primes).filter(prime -> prime <= n).count();
  }
}
