import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] x1 = new int[n];
    int[] y1 = new int[n];
    int[] x2 = new int[n];
    int[] y2 = new int[n];
    for (int i = 0; i < n; ++i) {
      x1[i] = sc.nextInt();
      y1[i] = sc.nextInt();
      x2[i] = sc.nextInt();
      y2[i] = sc.nextInt();
    }

    System.out.println(solve(x1, y1, x2, y2));

    sc.close();
  }

  static int solve(int[] x1, int[] y1, int[] x2, int[] y2) {
    Map<Integer, Integer> xToCompressed = buildXToCompressed(x1, x2);

    int result = 0;
    int[] horizontalIndices =
        IntStream.range(0, x1.length)
            .filter(i -> y1[i] == y2[i])
            .boxed()
            .sorted(Comparator.comparing(i -> y1[i]))
            .mapToInt(Integer::intValue)
            .toArray();
    int[] verticalIndices =
        IntStream.range(0, x1.length)
            .filter(i -> y1[i] != y2[i])
            .boxed()
            .sorted(Comparator.comparing(i -> Math.min(y1[i], y2[i])))
            .mapToInt(Integer::intValue)
            .toArray();
    int verticalPos = 0;
    PriorityQueue<Integer> pq =
        new PriorityQueue<>(Comparator.comparing(i -> Math.max(y1[i], y2[i])));
    int[] binaryIndexedTree = new int[Integer.highestOneBit(xToCompressed.size()) * 2 + 1];
    for (int horizontalIndex : horizontalIndices) {
      while (verticalPos != verticalIndices.length
          && Math.min(y1[verticalIndices[verticalPos]], y2[verticalIndices[verticalPos]])
              <= y1[horizontalIndex]) {
        add(binaryIndexedTree, xToCompressed.get(x1[verticalIndices[verticalPos]]), 1);
        pq.offer(verticalIndices[verticalPos]);
        ++verticalPos;
      }

      while (!pq.isEmpty() && Math.max(y1[pq.peek()], y2[pq.peek()]) < y1[horizontalIndex]) {
        add(binaryIndexedTree, xToCompressed.get(x1[pq.peek()]), -1);
        pq.poll();
      }

      result +=
          computeSum(
                  binaryIndexedTree,
                  xToCompressed.get(Math.max(x1[horizontalIndex], x2[horizontalIndex])))
              - computeSum(
                  binaryIndexedTree,
                  xToCompressed.get(Math.min(x1[horizontalIndex], x2[horizontalIndex])) - 1);
    }

    return result;
  }

  static void add(int[] binaryIndexedTree, int i, int x) {
    while (i < binaryIndexedTree.length) {
      binaryIndexedTree[i] += x;
      i += i & -i;
    }
  }

  static int computeSum(int[] binaryIndexedTree, int i) {
    int result = 0;
    while (i != 0) {
      result += binaryIndexedTree[i];
      i -= i & -i;
    }

    return result;
  }

  static Map<Integer, Integer> buildXToCompressed(int[] x1, int[] x2) {
    int[] sorted =
        IntStream.concat(Arrays.stream(x1), Arrays.stream(x2)).sorted().distinct().toArray();

    return IntStream.range(0, sorted.length)
        .boxed()
        .collect(Collectors.toMap(i -> sorted[i], i -> i + 1));
  }
}