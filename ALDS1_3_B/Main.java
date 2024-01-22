import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int q = sc.nextInt();
    String[] names = new String[n];
    int[] times = new int[n];
    for (int i = 0; i < n; ++i) {
      names[i] = sc.next();
      times[i] = sc.nextInt();
    }

    System.out.println(solve(names, times, q));

    sc.close();
  }

  static String solve(String[] names, int[] times, int q) {
    Queue<Element> queue = new ArrayDeque<>();
    for (int i = 0; i < names.length; ++i) {
      queue.offer(new Element(names[i], times[i]));
    }

    List<String> result = new ArrayList<>();
    int time = 0;
    while (!queue.isEmpty()) {
      Element head = queue.poll();
      if (head.neededTime <= q) {
        time += head.neededTime;
        result.add(String.format("%s %d", head.name, time));
      } else {
        time += q;
        queue.offer(new Element(head.name, head.neededTime - q));
      }
    }

    return String.join("\n", result);
  }
}

class Element {
  String name;
  int neededTime;

  Element(String name, int neededTime) {
    this.name = name;
    this.neededTime = neededTime;
  }
}