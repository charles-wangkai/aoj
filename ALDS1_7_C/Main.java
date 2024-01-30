import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();
    int[] ids = new int[n];
    int[] lefts = new int[n];
    int[] rights = new int[n];
    for (int i = 0; i < n; ++i) {
      ids[i] = sc.nextInt();
      lefts[i] = sc.nextInt();
      rights[i] = sc.nextInt();
    }

    System.out.println(solve(ids, lefts, rights));

    sc.close();
  }

  static String solve(int[] ids, int[] lefts, int[] rights) {
    Set<Integer> nonRoots =
        IntStream.concat(Arrays.stream(lefts), Arrays.stream(rights))
            .filter(x -> x != -1)
            .boxed()
            .collect(Collectors.toSet());
    int root = Arrays.stream(ids).filter(id -> !nonRoots.contains(id)).findAny().getAsInt();

    Map<Integer, Integer> idToIndex =
        IntStream.range(0, ids.length).boxed().collect(Collectors.toMap(i -> ids[i], i -> i));

    List<Integer> preorderIds = new ArrayList<>();
    preorderSearch(preorderIds, idToIndex, lefts, rights, root);

    List<Integer> inorderIds = new ArrayList<>();
    inorderSearch(inorderIds, idToIndex, lefts, rights, root);

    List<Integer> postorderIds = new ArrayList<>();
    postorderSearch(postorderIds, idToIndex, lefts, rights, root);

    return String.format(
        "Preorder\n%s\nInorder\n%s\nPostorder\n%s",
        preorderIds.stream().map(String::valueOf).map(s -> " " + s).collect(Collectors.joining()),
        inorderIds.stream().map(String::valueOf).map(s -> " " + s).collect(Collectors.joining()),
        postorderIds.stream().map(String::valueOf).map(s -> " " + s).collect(Collectors.joining()));
  }

  static void preorderSearch(
      List<Integer> preorderIds,
      Map<Integer, Integer> idToIndex,
      int[] lefts,
      int[] rights,
      int id) {
    int index = idToIndex.get(id);

    preorderIds.add(id);

    if (lefts[index] != -1) {
      preorderSearch(preorderIds, idToIndex, lefts, rights, lefts[index]);
    }

    if (rights[index] != -1) {
      preorderSearch(preorderIds, idToIndex, lefts, rights, rights[index]);
    }
  }

  static void inorderSearch(
      List<Integer> inorderIds,
      Map<Integer, Integer> idToIndex,
      int[] lefts,
      int[] rights,
      int id) {
    int index = idToIndex.get(id);

    if (lefts[index] != -1) {
      inorderSearch(inorderIds, idToIndex, lefts, rights, lefts[index]);
    }

    inorderIds.add(id);

    if (rights[index] != -1) {
      inorderSearch(inorderIds, idToIndex, lefts, rights, rights[index]);
    }
  }

  static void postorderSearch(
      List<Integer> postorderIds,
      Map<Integer, Integer> idToIndex,
      int[] lefts,
      int[] rights,
      int id) {
    int index = idToIndex.get(id);

    if (lefts[index] != -1) {
      postorderSearch(postorderIds, idToIndex, lefts, rights, lefts[index]);
    }

    if (rights[index] != -1) {
      postorderSearch(postorderIds, idToIndex, lefts, rights, rights[index]);
    }

    postorderIds.add(id);
  }
}
