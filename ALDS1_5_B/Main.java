import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

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

  static String solve(int[] a) {
    return String.format(
        "%s\n%d",
        Arrays.stream(a).sorted().mapToObj(String::valueOf).collect(Collectors.joining(" ")),
        computeComparisonNum(a.length));
  }

  static int computeComparisonNum(int length) {
    return (length <= 1)
        ? 0
        : (length + computeComparisonNum(length / 2) + computeComparisonNum(length - length / 2));
  }
}