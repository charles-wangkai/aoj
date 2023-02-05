import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (sc.hasNextInt()) {
      int a = sc.nextInt();
      int b = sc.nextInt();

      System.out.println(solve(a, b));
    }

    sc.close();
  }

  static String solve(int a, int b) {
    int g = gcd(a, b);

    return String.format("%d %d", g, a / g * b);
  }

  static int gcd(int x, int y) {
    return (y == 0) ? x : gcd(y, x % y);
  }
}
