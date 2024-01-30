import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] preorderIds = new int[n];
    for (int i = 0; i < preorderIds.length; ++i) {
      preorderIds[i] = sc.nextInt();
    }
    int[] inorderIds = new int[n];
    for (int i = 0; i < inorderIds.length; ++i) {
      inorderIds[i] = sc.nextInt();
    }

    System.out.println(solve(preorderIds, inorderIds));

    sc.close();
  }

  static String solve(int[] preorderIds, int[] inorderIds) {
    List<Integer> postorderIds = new ArrayList<>();
    search(
        postorderIds, preorderIds, 0, preorderIds.length - 1, inorderIds, 0, inorderIds.length - 1);

    return postorderIds.stream().map(String::valueOf).collect(Collectors.joining(" "));
  }

  static void search(
      List<Integer> postorderIds,
      int[] preorderIds,
      int preorderBeginIndex,
      int preorderEndIndex,
      int[] inorderIds,
      int inorderBeginIndex,
      int inorderEndIndex) {
    int rootInorderIndex = inorderBeginIndex;
    while (inorderIds[rootInorderIndex] != preorderIds[preorderBeginIndex]) {
      ++rootInorderIndex;
    }

    int leftLength = rootInorderIndex - inorderBeginIndex;
    int rightLength = preorderEndIndex - preorderBeginIndex - leftLength;

    if (leftLength != 0) {
      search(
          postorderIds,
          preorderIds,
          preorderBeginIndex + 1,
          preorderBeginIndex + leftLength,
          inorderIds,
          inorderBeginIndex,
          inorderBeginIndex + leftLength - 1);
    }
    if (rightLength != 0) {
      search(
          postorderIds,
          preorderIds,
          preorderBeginIndex + leftLength + 1,
          preorderEndIndex,
          inorderIds,
          inorderBeginIndex + leftLength + 1,
          inorderEndIndex);
    }

    postorderIds.add(preorderIds[preorderBeginIndex]);
  }
}