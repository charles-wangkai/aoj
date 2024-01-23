import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] S = new int[n];
    for (int i = 0; i < S.length; ++i) {
      S[i] = sc.nextInt();
    }
    int q = sc.nextInt();
    int[] T = new int[q];
    for (int i = 0; i < T.length; ++i) {
      T[i] = sc.nextInt();
    }

    System.out.println(solve(S, T));

    sc.close();
  }

  static int solve(int[] S, int[] T) {
    return (int) Arrays.stream(T).filter(x -> Arrays.binarySearch(S, x) >= 0).count();
  }
}