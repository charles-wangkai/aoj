import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    List<String> result = new ArrayList<>();
    for (int i = 0; i < a.length; ++i) {
      int index = i;
      while (index != 0 && a[index - 1] > a[index]) {
        int temp = a[index];
        a[index] = a[index - 1];
        a[index - 1] = temp;

        --index;
      }

      result.add(Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" ")));
    }

    return String.join("\n", result);
  }
}