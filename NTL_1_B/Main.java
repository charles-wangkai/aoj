import java.util.Scanner;

public class Main {
  static final int MODULUS = 1_000_000_007;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int m = sc.nextInt();
    int n = sc.nextInt();

    System.out.println(solve(m, n));

    sc.close();
  }

  static int solve(int m, int n) {
    return (n == 0) ? 1 : multiplyMod((n % 2 == 0) ? 1 : m, solve(multiplyMod(m, m), n / 2));
  }

  static int multiplyMod(int x, int y) {
    return Math.floorMod((long) x * y, MODULUS);
  }
}