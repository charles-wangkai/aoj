import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    for (int tc = 0; tc < N; ++tc) {
      int[] balls = new int[10];
      for (int i = 0; i < balls.length; ++i) {
        balls[i] = sc.nextInt();
      }

      System.out.println(solve(balls) ? "YES" : "NO");
    }

    sc.close();
  }

  static boolean solve(int[] balls) {
    int lower = 0;
    int upper = 0;
    for (int ball : balls) {
      if (ball < lower) {
        return false;
      }

      if (ball > upper) {
        upper = ball;
      } else {
        lower = ball;
      }
    }

    return true;
  }
}
