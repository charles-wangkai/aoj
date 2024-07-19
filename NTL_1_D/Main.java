// https://en.wikipedia.org/wiki/Euler%27s_totient_function

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();

    System.out.println(solve(n));

    sc.close();
  }

  static int solve(int n) {
    int result = n;

    int rest = n;
    for (int i = 2; i * i <= rest; ++i) {
      if (rest % i == 0) {
        result = result / i * (i - 1);

        while (rest % i == 0) {
          rest /= i;
        }
      }
    }
    if (rest != 1) {
      result = result / rest * (rest - 1);
    }

    return result;
  }
}