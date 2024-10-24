import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      String s = sc.nextLine();
      if (s.equals(".")) {
        break;
      }

      System.out.println(solve(s) ? "yes" : "no");
    }

    sc.close();
  }

  static boolean solve(String s) {
    Deque<Integer> roundStack = new ArrayDeque<>();
    Deque<Integer> squareStack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
      if (c == '(') {
        roundStack.push(squareStack.size());
      } else if (c == '[') {
        squareStack.push(roundStack.size());
      } else if (c == ')') {
        if (roundStack.isEmpty() || squareStack.size() != roundStack.peek()) {
          return false;
        }

        roundStack.pop();
      } else if (c == ']') {
        if (squareStack.isEmpty() || roundStack.size() != squareStack.peek()) {
          return false;
        }

        squareStack.pop();
      }
    }

    return roundStack.isEmpty() && squareStack.isEmpty();
  }
}
