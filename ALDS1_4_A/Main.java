import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

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
    Set<Integer> set = Arrays.stream(S).boxed().collect(Collectors.toSet());

    return (int) Arrays.stream(T).filter(set::contains).count();
  }
}