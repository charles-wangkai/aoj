import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] A = new int[n];
    for (int i = 0; i < A.length; ++i) {
      A[i] = sc.nextInt();
    }
    int q = sc.nextInt();
    int[] M = new int[q];
    for (int i = 0; i < M.length; ++i) {
      M[i] = sc.nextInt();
    }

    System.out.println(solve(A, M));

    sc.close();
  }

  static String solve(int[] A, int[] M) {
    Set<Integer> sums =
        IntStream.range(0, 1 << A.length)
            .map(
                mask ->
                    IntStream.range(0, A.length)
                        .filter(i -> ((mask >> i) & 1) == 1)
                        .map(i -> A[i])
                        .sum())
            .boxed()
            .collect(Collectors.toSet());

    return Arrays.stream(M)
        .mapToObj(x -> sums.contains(x) ? "yes" : "no")
        .collect(Collectors.joining("\n"));
  }
}