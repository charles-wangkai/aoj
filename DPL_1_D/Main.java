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

  static int solve(int[] a) {
    List<Integer> sorted = new ArrayList<>();
    for (int ai : a) {
      int index = findIndex(sorted, ai);
      if (index == -1) {
        sorted.add(ai);
      } else {
        sorted.set(index, ai);
      }
    }

    return sorted.size();
  }

  static int findIndex(List<Integer> sorted, int target) {
    int result = -1;
    int lower = 0;
    int upper = sorted.size() - 1;
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (sorted.get(middle) >= target) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return result;
  }
}