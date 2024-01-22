import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    String[] commands = new String[n];
    for (int i = 0; i < commands.length; ++i) {
      commands[i] = br.readLine();
    }

    System.out.println(solve(commands));
  }

  static String solve(String[] commands) {
    Deque<Integer> deque = new ArrayDeque<>();
    for (String command : commands) {
      String[] parts = command.split(" ");
      if (parts[0].equals("insert")) {
        deque.offerFirst(Integer.parseInt(parts[1]));
      } else if (parts[0].equals("delete")) {
        int x = Integer.parseInt(parts[1]);

        Deque<Integer> deleted = new ArrayDeque<>();
        boolean seen = false;
        while (!deque.isEmpty()) {
          int head = deque.pollFirst();
          if (head == x && !seen) {
            seen = true;
          } else {
            deleted.offerLast(head);
          }
        }

        deque = deleted;
      } else if (parts[0].equals("deleteFirst")) {
        deque.pollFirst();
      } else {
        deque.pollLast();
      }
    }

    return deque.stream().map(String::valueOf).collect(Collectors.joining(" "));
  }
}