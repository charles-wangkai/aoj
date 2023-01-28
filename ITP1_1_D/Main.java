import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int S = sc.nextInt();

    System.out.println(solve(S));

    sc.close();
  }

  static String solve(int S) {
    return String.format("%d:%d:%d", S / 3600, S / 60 % 60, S % 60);
  }
}
