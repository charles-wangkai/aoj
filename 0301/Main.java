import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int M = sc.nextInt();
    int Q = sc.nextInt();
    int[] a = new int[M];
    for (int i = 0; i < a.length; ++i) {
      a[i] = sc.nextInt();
    }
    int[] q = new int[Q];
    for (int i = 0; i < q.length; ++i) {
      q[i] = sc.nextInt();
    }

    System.out.println(solve(N, a, q));

    sc.close();
  }

  static String solve(int N, int[] a, int[] q) {
    Deque<Integer> deque = new ArrayDeque<>();
    for (int i = 0; i < N; ++i) {
      deque.offer(i);
    }
    for (int ai : a) {
      if (ai % 2 == 0) {
        for (int i = 0; i < ai; ++i) {
          deque.offerLast(deque.pollFirst());
        }
      } else {
        for (int i = 0; i < ai; ++i) {
          deque.offerFirst(deque.pollLast());
        }
      }
      deque.pollFirst();
    }

    Set<Integer> rests = deque.stream().collect(Collectors.toSet());

    return Arrays.stream(q)
        .map(qi -> rests.contains(qi) ? 1 : 0)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining("\n"));
  }
}