import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] R = new int[n];
    for (int i = 0; i < R.length; ++i) {
      R[i] = sc.nextInt();
    }

    System.out.println(solve(R));

    sc.close();
  }

  static int solve(int[] R) {
    int result = Integer.MIN_VALUE;
    int min = R[0];
    for (int i = 1; i < R.length; ++i) {
      result = Math.max(result, R[i] - min);
      min = Math.min(min, R[i]);
    }

    return result;
  }
}