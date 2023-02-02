import java.util.Scanner;
import java.util.Stack;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int Q = sc.nextInt();
      if (N == 0) {
        break;
      }

      int[] p = new int[N - 1];
      for (int i = 0; i < p.length; ++i) {
        p[i] = sc.nextInt();
      }

      sc.nextLine();
      String[] operations = new String[Q];
      for (int i = 0; i < operations.length; ++i) {
        operations[i] = sc.nextLine();
      }

      System.out.println(solve(p, operations));
    }

    sc.close();
  }

  static long solve(int[] p, String[] operations) {
    int N = p.length + 1;

    @SuppressWarnings("unchecked")
    Stack<Integer>[] parentStacks = new Stack[N + 1];
    for (int i = 1; i < parentStacks.length; ++i) {
      parentStacks[i] = new Stack<>();
    }
    parentStacks[1].push(-1);
    for (int i = 2; i < parentStacks.length; ++i) {
      parentStacks[i].push(p[i - 2]);
    }

    for (String operation : operations) {
      String[] parts = operation.split(" ");
      char command = parts[0].charAt(0);
      int v = Integer.parseInt(parts[1]);

      if (command == 'M') {
        parentStacks[v].push(-1);
      }
    }

    long result = 0;
    for (int i = operations.length - 1; i >= 0; --i) {
      String[] parts = operations[i].split(" ");
      char command = parts[0].charAt(0);
      int v = Integer.parseInt(parts[1]);

      if (command == 'M') {
        parentStacks[v].pop();
      } else {
        result += findRoot(parentStacks, v);
      }
    }

    return result;
  }

  static int findRoot(Stack<Integer>[] parentStacks, int v) {
    int root = v;
    while (parentStacks[root].peek() != -1) {
      root = parentStacks[root].peek();
    }

    int p = v;
    while (p != root) {
      int next = parentStacks[p].peek();
      parentStacks[p].pop();
      parentStacks[p].push(root);

      p = next;
    }

    return root;
  }
}
