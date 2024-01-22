import static java.util.Map.entry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Scanner;

public class Main {
  static final Map<String, Operation> OPERATOR_TO_OPERATION =
      Map.ofEntries(
          entry("+", (x, y) -> x + y), entry("-", (x, y) -> x - y), entry("*", (x, y) -> x * y));

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    String expression = sc.nextLine();

    System.out.println(solve(expression));

    sc.close();
  }

  static int solve(String expression) {
    String[] parts = expression.split(" ");
    Deque<Integer> stack = new ArrayDeque<>();
    for (String part : parts) {
      if (OPERATOR_TO_OPERATION.containsKey(part)) {
        int operand2 = stack.pop();
        int operand1 = stack.pop();

        stack.push(OPERATOR_TO_OPERATION.get(part).apply(operand1, operand2));
      } else {
        stack.push(Integer.parseInt(part));
      }
    }

    return stack.peek();
  }
}

interface Operation {
  int apply(int x, int y);
}