import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] a = new int[n];
    for (int i = 0; i < a.length; ++i) {
      a[i] = sc.nextInt();
    }

    System.out.println(solve(a));

    sc.close();
  }

  static long solve(int[] a) {
    return computeInversionNum(a, 0, a.length - 1);
  }

  static long computeInversionNum(int[] a, int beginIndex, int endIndex) {
    if (beginIndex == endIndex) {
      return 0;
    }

    int middleIndex = (beginIndex + endIndex) / 2;

    long result =
        computeInversionNum(a, beginIndex, middleIndex)
            + computeInversionNum(a, middleIndex + 1, endIndex);

    int leftIndex = beginIndex;
    int rightIndex = middleIndex + 1;
    List<Integer> sorted = new ArrayList<>();
    while (leftIndex != middleIndex + 1 || rightIndex != endIndex + 1) {
      if (rightIndex == endIndex + 1
          || (leftIndex != middleIndex + 1 && a[leftIndex] < a[rightIndex])) {
        sorted.add(a[leftIndex]);
        ++leftIndex;
      } else {
        sorted.add(a[rightIndex]);
        ++rightIndex;

        result += middleIndex + 1 - leftIndex;
      }
    }

    for (int i = 0; i < sorted.size(); ++i) {
      a[beginIndex + i] = sorted.get(i);
    }

    return result;
  }
}