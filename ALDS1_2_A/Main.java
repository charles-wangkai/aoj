import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int[] a = new int[N];
    for (int i = 0; i < a.length; ++i) {
      a[i] = sc.nextInt();
    }

    System.out.println(solve(a));

    sc.close();
  }

  static String solve(int[] a) {
    int swapCount = 0;
    for (int i = 0; i < a.length; ++i) {
      for (int j = a.length - 1; j > i; --j) {
        if (a[j] < a[j - 1]) {
          int temp = a[j];
          a[j] = a[j - 1];
          a[j - 1] = temp;

          ++swapCount;
        }
      }
    }

    return String.format(
        "%s\n%d",
        Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" ")), swapCount);
  }
}