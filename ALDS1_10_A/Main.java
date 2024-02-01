import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();

    System.out.println(solve(n));

    sc.close();
  }

  static int solve(int n) {
    int prev = 0;
    int curr = 1;
    for (int i = 0; i < n; ++i) {
      int next = prev + curr;

      prev = curr;
      curr = next;
    }

    return curr;
  }
}