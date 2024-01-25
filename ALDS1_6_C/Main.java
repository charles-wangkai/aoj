import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    char[] suits = new char[n];
    int[] numbers = new int[n];
    for (int i = 0; i < n; ++i) {
      suits[i] = sc.next().charAt(0);
      numbers[i] = sc.nextInt();
    }

    System.out.println(solve(suits, numbers));

    sc.close();
  }

  static String solve(char[] suits, int[] numbers) {
    int[] indices = IntStream.range(0, numbers.length).toArray();
    quickSort(numbers, indices, 0, indices.length - 1);

    return String.format(
        "%s\n%s",
        IntStream.range(0, indices.length - 1)
                .allMatch(
                    i ->
                        numbers[indices[i]] != numbers[indices[i + 1]]
                            || indices[i] < indices[i + 1])
            ? "Stable"
            : "Not stable",
        Arrays.stream(indices)
            .mapToObj(index -> String.format("%c %d", suits[index], numbers[index]))
            .collect(Collectors.joining("\n")));
  }

  static void quickSort(int[] numbers, int[] indices, int p, int r) {
    if (p < r) {
      int q = partition(numbers, indices, p, r);

      quickSort(numbers, indices, p, q - 1);
      quickSort(numbers, indices, q + 1, r);
    }
  }

  static int partition(int[] numbers, int[] indices, int p, int r) {
    int x = numbers[indices[r]];
    int i = p - 1;
    for (int j = p; j <= r - 1; ++j) {
      if (numbers[indices[j]] <= x) {
        ++i;
        swap(indices, i, j);
      }
    }
    swap(indices, i + 1, r);

    return i + 1;
  }

  static void swap(int[] a, int index1, int index2) {
    int temp = a[index1];
    a[index1] = a[index2];
    a[index2] = temp;
  }
}