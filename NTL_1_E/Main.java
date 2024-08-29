// https://cp-algorithms.com/algebra/extended-euclid-algorithm.html
// https://blog.csdn.net/lovecyr/article/details/105372427

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int a = sc.nextInt();
    int b = sc.nextInt();

    System.out.println(solve(a, b));

    sc.close();
  }

  static String solve(int a, int b) {
    Outcome outcome = gcd(a, b);

    return String.format("%d %d", outcome.x, outcome.y);
  }

  static Outcome gcd(int a, int b) {
    if (b == 0) {
      return new Outcome(a, 1, 0);
    }

    Outcome outcome = gcd(b, a % b);

    return new Outcome(outcome.g, outcome.y, outcome.x - outcome.y * (a / b));
  }
}

class Outcome {
  int g;
  int x;
  int y;

  Outcome(int g, int x, int y) {
    this.g = g;
    this.x = x;
    this.y = y;
  }
}