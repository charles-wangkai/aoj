// https://en.wikipedia.org/wiki/Shellsort

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    List<Integer> gaps = new ArrayList<>();
    gaps.add(1);
    for (int i = 3; i < A.length; i = i * 2 + 1) {
      gaps.add(i);
    }
    Collections.reverse(gaps);

    int swapCount = 0;
    for (int gap : gaps) {
      for (int i = gap; i < A.length; ++i) {
        int index = i;
        while (index >= gap && A[index] < A[index - gap]) {
          int temp = A[index];
          A[index] = A[index - gap];
          A[index - gap] = temp;

          ++swapCount;

          index -= gap;
        }
      }
    }

    return String.format(
        "%d\n%s\n%d\n%s",
        gaps.size(),
        gaps.stream().map(String::valueOf).collect(Collectors.joining(" ")),
        swapCount,
        Arrays.stream(A).mapToObj(String::valueOf).collect(Collectors.joining("\n")));
  }
}