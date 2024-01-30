import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int m = sc.nextInt();
    sc.nextLine();
    String[] operations = new String[m];
    for (int i = 0; i < operations.length; ++i) {
      operations[i] = sc.nextLine();
    }

    System.out.print(solve(operations));

    sc.close();
  }

  static String solve(String[] operations) {
    StringBuilder result = new StringBuilder();
    Node root = null;
    for (String operation : operations) {
      if (operation.equals("print")) {
        List<Integer> inorderValues = new ArrayList<>();
        inorderSearch(inorderValues, root);

        List<Integer> preorderValues = new ArrayList<>();
        preorderSearch(preorderValues, root);

        result.append(
            String.format(
                "%s\n%s\n",
                inorderValues.stream()
                    .map(String::valueOf)
                    .map(s -> " " + s)
                    .collect(Collectors.joining()),
                preorderValues.stream()
                    .map(String::valueOf)
                    .map(s -> " " + s)
                    .collect(Collectors.joining())));
      } else {
        String[] parts = operation.split(" ");
        int value = Integer.parseInt(parts[1]);

        Node inserted = new Node(value);
        if (root == null) {
          root = inserted;
        } else {
          insert(inserted, root);
        }
      }
    }

    return result.toString();
  }

  static void insert(Node inserted, Node node) {
    if (inserted.value < node.value) {
      if (node.left == null) {
        node.left = inserted;
      } else {
        insert(inserted, node.left);
      }
    } else if (node.right == null) {
      node.right = inserted;
    } else {
      insert(inserted, node.right);
    }
  }

  static void inorderSearch(List<Integer> inorderValues, Node node) {
    if (node != null) {
      inorderSearch(inorderValues, node.left);
      inorderValues.add(node.value);
      inorderSearch(inorderValues, node.right);
    }
  }

  static void preorderSearch(List<Integer> preorderValues, Node node) {
    if (node != null) {
      preorderValues.add(node.value);
      preorderSearch(preorderValues, node.left);
      preorderSearch(preorderValues, node.right);
    }
  }
}

class Node {
  int value;
  Node left;
  Node right;

  Node(int value) {
    this.value = value;
  }
}