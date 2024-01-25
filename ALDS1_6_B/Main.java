import java.util.Scanner;
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

    System.out.println(solve(A));

    sc.close();
  }

  static String solve(int[] A) {
    int x = A[A.length - 1];
    int i = -1;
    for (int j = 0; j < A.length - 1; ++j) {
      if (A[j] <= x) {
        ++i;
        swap(A, i, j);
      }
    }
    swap(A, i + 1, A.length - 1);

    int i_ = i;
    return IntStream.range(0, A.length)
        .mapToObj(p -> (p == i_ + 1) ? String.format("[%d]", A[p]) : String.valueOf(A[p]))
        .collect(Collectors.joining(" "));
  }

  static void swap(int[] A, int index1, int index2) {
    int temp = A[index1];
    A[index1] = A[index2];
    A[index2] = temp;
  }
}