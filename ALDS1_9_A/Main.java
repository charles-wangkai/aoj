import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int H = sc.nextInt();
    int[] a = new int[H];
    for (int i = 0; i < a.length; ++i) {
      a[i] = sc.nextInt();
    }

    System.out.println(solve(a));

    sc.close();
  }

  static String solve(int[] a) {
    return IntStream.range(0, a.length)
        .mapToObj(
            i -> {
              StringBuilder result =
                  new StringBuilder(String.format("node %d: key = %d, ", i + 1, a[i]));
              if (i != 0) {
                result.append(String.format("parent key = %d, ", a[(i - 1) / 2]));
              }
              if (i * 2 + 1 < a.length) {
                result.append(String.format("left key = %d, ", a[i * 2 + 1]));
              }
              if (i * 2 + 2 < a.length) {
                result.append(String.format("right key = %d, ", a[i * 2 + 2]));
              }

              return result.toString();
            })
        .collect(Collectors.joining("\n"));
  }
}