import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
  static final int LIMIT = 1_000_000;

  static int[] minAllNums;
  static int[] minOddNums;

  public static void main(String[] args) {
    precompute();

    Scanner sc = new Scanner(System.in);

    while (true) {
      int n = sc.nextInt();
      if (n == 0) {
        break;
      }

      System.out.println(solve(n));
    }

    sc.close();
  }

  static void precompute() {
    List<Integer> tetrahedrals = new ArrayList<>();
    for (int i = 1; ; ++i) {
      int tetrahedral = i * (i + 1) * (i + 2) / 6;
      if (tetrahedral >= LIMIT) {
        break;
      }

      tetrahedrals.add(tetrahedral);
    }

    minAllNums = buildMinNums(tetrahedrals.stream().mapToInt(Integer::intValue).toArray());
    minOddNums =
        buildMinNums(
            tetrahedrals.stream().filter(x -> x % 2 == 1).mapToInt(Integer::intValue).toArray());
  }

  static int[] buildMinNums(int[] values) {
    int[] result = new int[LIMIT];
    Arrays.fill(result, Integer.MAX_VALUE);
    result[0] = 0;
    for (int i = 1; i < result.length; ++i) {
      for (int value : values) {
        if (value <= i) {
          result[i] = Math.min(result[i], result[i - value] + 1);
        }
      }
    }

    return result;
  }

  static String solve(int n) {
    return String.format("%d %d", minAllNums[n], minOddNums[n]);
  }
}