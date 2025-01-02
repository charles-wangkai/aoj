// https://www.hankcs.com/program/algorithm/aoj-2212-stolen-jewel.html

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Main {
  static final int[] R_OFFSETS = {-1, 0, 1, 0};
  static final int[] C_OFFSETS = {0, 1, 0, -1};
  static final char[] MOVES = {'U', 'R', 'D', 'L'};

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      if (N == 0 && M == 0) {
        break;
      }

      char[][] cells = new char[N][M];
      for (int r = 0; r < N; ++r) {
        String line = sc.next();
        for (int c = 0; c < M; ++c) {
          cells[r][c] = line.charAt(c);
        }
      }
      int P = sc.nextInt();
      String[] patterns = new String[P];
      for (int i = 0; i < patterns.length; ++i) {
        patterns[i] = sc.next();
      }

      System.out.println(solve(cells, patterns));
    }

    sc.close();
  }

  static int solve(char[][] cells, String[] patterns) {
    int N = cells.length;
    int M = cells[0].length;

    int beginR = -1;
    int beginC = -1;
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        if (cells[r][c] == 'S') {
          beginR = r;
          beginC = c;
        }
      }
    }

    int maxPatternLength = Arrays.stream(patterns).mapToInt(String::length).max().orElse(0);

    Node root = buildAcAutomaton(patterns);

    @SuppressWarnings("unchecked")
    Set<Node>[][] visited = new Set[N][M];
    for (int r = 0; r < N; ++r) {
      for (int c = 0; c < M; ++c) {
        visited[r][c] = new HashSet<>();
      }
    }

    Queue<State> queue = new ArrayDeque<>();
    queue.offer(new State(beginR, beginC, 0, ""));
    while (!queue.isEmpty()) {
      State head = queue.poll();
      if (cells[head.r][head.c] == 'G') {
        return head.cost;
      }

      for (int i = 0; i < R_OFFSETS.length; ++i) {
        int adjR = head.r + R_OFFSETS[i];
        int adjC = head.c + C_OFFSETS[i];
        if (adjR >= 0 && adjR < N && adjC >= 0 && adjC < M && cells[adjR][adjC] != '#') {
          String nextPath = head.path + MOVES[i];
          if (nextPath.length() > maxPatternLength) {
            nextPath = nextPath.substring(1);
          }

          Node p = findNextNode(root, nextPath);
          if (p.matches.isEmpty() && !visited[adjR][adjC].contains(p)) {
            visited[adjR][adjC].add(p);
            queue.offer(new State(adjR, adjC, head.cost + 1, nextPath));
          }
        }
      }
    }

    return -1;
  }

  static Node buildAcAutomaton(String[] patterns) {
    Node root = new Node();
    root.fail = root;
    for (int i = 0; i < patterns.length; ++i) {
      Node p = root;
      for (char letter : patterns[i].toCharArray()) {
        if (!p.letterToChild.containsKey(letter)) {
          p.letterToChild.put(letter, new Node());
        }
        p = p.letterToChild.get(letter);
      }

      p.matches.add(i);
    }

    Queue<Node> queue = new ArrayDeque<>();
    for (char letter : MOVES) {
      if (root.letterToChild.containsKey(letter)) {
        root.letterToChild.get(letter).fail = root;
        queue.offer(root.letterToChild.get(letter));
      } else {
        root.letterToChild.put(letter, root);
      }
    }
    while (!queue.isEmpty()) {
      Node head = queue.poll();
      for (char letter : head.letterToChild.keySet()) {
        Node child = head.letterToChild.get(letter);

        queue.offer(child);

        Node f = head.fail;
        while (!f.letterToChild.containsKey(letter)) {
          f = f.fail;
        }
        child.fail = f.letterToChild.get(letter);

        child.matches.addAll(child.fail.matches);
      }
    }

    return root;
  }

  static Node findNextNode(Node root, String s) {
    Node result = root;
    for (char letter : s.toCharArray()) {
      while (!result.letterToChild.containsKey(letter)) {
        result = result.fail;
      }
      result = result.letterToChild.get(letter);
    }

    return result;
  }
}

class State {
  int r;
  int c;
  int cost;
  String path;

  State(int r, int c, int cost, String path) {
    this.r = r;
    this.c = c;
    this.cost = cost;
    this.path = path;
  }
}

class Node {
  Map<Character, Node> letterToChild = new HashMap<>();
  Node fail;
  List<Integer> matches = new ArrayList<>();
}
