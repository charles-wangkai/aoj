import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
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

  static String solve(int n) {
    List<List<Integer>> sequences = new ArrayList<>();
    search(sequences, new ArrayList<>(), n, Integer.MAX_VALUE);

    return sequences.stream()
        .map(sequence -> sequence.stream().map(String::valueOf).collect(Collectors.joining(" ")))
        .collect(Collectors.joining("\n"));
  }

  static void search(List<List<Integer>> sequences, List<Integer> sequence, int rest, int last) {
    if (rest == 0) {
      sequences.add(List.copyOf(sequence));
    } else {
      for (int i = Math.min(last, rest); i >= 1; --i) {
        sequence.add(i);
        search(sequences, sequence, rest - i, i);
        sequence.remove(sequence.size() - 1);
      }
    }
  }
}