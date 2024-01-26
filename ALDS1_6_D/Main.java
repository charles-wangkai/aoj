import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] w = new int[n];
    for (int i = 0; i < w.length; ++i) {
      w[i] = sc.nextInt();
    }

    System.out.println(solve(w));

    sc.close();
  }

  static int solve(int[] w) {
    int n = w.length;

    int globalMin = Arrays.stream(w).min().getAsInt();
    Map<Integer, Integer> valueToIndex =
        IntStream.range(0, n).boxed().collect(Collectors.toMap(i -> w[i], i -> i));
    int[] sorted = Arrays.stream(w).sorted().toArray();

    int result = 0;
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; ++i) {
      if (!visited[i] && w[i] != sorted[i]) {
        List<Integer> values = new ArrayList<>();
        int index = i;
        while (!visited[index]) {
          visited[index] = true;
          values.add(sorted[index]);
          index = valueToIndex.get(sorted[index]);
        }

        int minValue = values.stream().mapToInt(Integer::intValue).min().getAsInt();
        int valueSum = values.stream().mapToInt(Integer::intValue).sum();

        result +=
            Math.min(
                valueSum + minValue * (values.size() - 2),
                (globalMin + minValue) * 2
                    + (valueSum - minValue + globalMin)
                    + globalMin * (values.size() - 2));
      }
    }

    return result;
  }
}