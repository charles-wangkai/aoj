import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int H = sc.nextInt();
    int[] A = new int[H];
    for (int i = 0; i < A.length; ++i) {
      A[i] = sc.nextInt();
    }

    System.out.println(solve(A));

    sc.close();
  }

  static String solve(int[] A) {
    for (int i = A.length - 1; i >= 0; --i) {
      maxHeapify(A, i);
    }

    return Arrays.stream(A)
        .mapToObj(String::valueOf)
        .map(s -> " " + s)
        .collect(Collectors.joining());
  }

  static void maxHeapify(int[] A, int index) {
    int maxIndex = index;
    if (index * 2 + 1 < A.length && A[index * 2 + 1] > A[maxIndex]) {
      maxIndex = index * 2 + 1;
    }
    if (index * 2 + 2 < A.length && A[index * 2 + 2] > A[maxIndex]) {
      maxIndex = index * 2 + 2;
    }

    if (maxIndex != index) {
      int temp = A[maxIndex];
      A[maxIndex] = A[index];
      A[index] = temp;

      maxHeapify(A, maxIndex);
    }
  }
}