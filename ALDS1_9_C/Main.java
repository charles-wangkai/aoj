import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    List<String> operations = new ArrayList<>();
    while (true) {
      String operation = br.readLine();
      if (operation.equals("end")) {
        break;
      }

      operations.add(operation);
    }

    System.out.println(solve(operations));
  }

  static String solve(List<String> operations) {
    List<Integer> result = new ArrayList<>();
    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
    for (String operation : operations) {
      if (operation.equals("extract")) {
        result.add(pq.poll());
      } else {
        String[] parts = operation.split(" ");
        int value = Integer.parseInt(parts[1]);

        pq.offer(value);
      }
    }

    return result.stream().map(String::valueOf).collect(Collectors.joining("\n"));
  }
}