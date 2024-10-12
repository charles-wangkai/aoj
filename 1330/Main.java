import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Runnable {
  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 28).start();
  }

  public void run() {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      if (N == 0 && M == 0) {
        break;
      }

      sc.nextLine();
      String[] queries = new String[M];
      for (int i = 0; i < queries.length; ++i) {
        queries[i] = sc.nextLine();
      }

      System.out.println(solve(N, queries));
    }

    sc.close();
  }

  static String solve(int N, String[] queries) {
    Element[] parents = new Element[N];
    for (int i = 0; i < parents.length; ++i) {
      parents[i] = new Element(i, 0);
    }

    List<String> result = new ArrayList<>();
    for (String query : queries) {
      String[] parts = query.split(" ");
      int a = Integer.parseInt(parts[1]);
      int b = Integer.parseInt(parts[2]);

      Element root1 = findRoot(parents, a - 1);
      Element root2 = findRoot(parents, b - 1);

      if (parts[0].equals("!")) {
        int w = Integer.parseInt(parts[3]);

        if (root1.node != root2.node) {
          parents[root2.node] = new Element(root1.node, w + root1.diff - root2.diff);
        }
      } else {
        result.add(
            (root1.node == root2.node) ? String.valueOf(root2.diff - root1.diff) : "UNKNOWN");
      }
    }

    return String.join("\n", result);
  }

  static Element findRoot(Element[] parents, int node) {
    if (parents[node].node == node) {
      return parents[node];
    }

    Element root = findRoot(parents, parents[node].node);
    parents[node] = new Element(root.node, parents[node].diff + root.diff);

    return parents[node];
  }
}

class Element {
  int node;
  int diff;

  Element(int node, int diff) {
    this.node = node;
    this.diff = diff;
  }
}