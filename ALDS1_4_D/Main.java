import java.util.Arrays;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int k = sc.nextInt();
    int[] w = new int[n];
    for (int i = 0; i < w.length; ++i) {
      w[i] = sc.nextInt();
    }

    System.out.println(solve(w, k));

    sc.close();
  }

  static int solve(int[] w, int k) {
    int result = -1;
    int lower = Arrays.stream(w).max().getAsInt();
    int upper = Arrays.stream(w).sum();
    while (lower <= upper) {
      int middle = (lower + upper) / 2;
      if (check(w, k, middle)) {
        result = middle;
        upper = middle - 1;
      } else {
        lower = middle + 1;
      }
    }

    return result;
  }

  static boolean check(int[] w, int k, int maxSum) {
    int truckCount = 1;
    int sum = 0;
    for (int wi : w) {
      if (sum + wi <= maxSum) {
        sum += wi;
      } else {
        ++truckCount;
        sum = wi;
      }
    }

    return truckCount <= k;
  }
}