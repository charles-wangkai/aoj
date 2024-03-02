import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int X = sc.nextInt();
    int Y = sc.nextInt();
    int E = sc.nextInt();
    int[] x = new int[E];
    int[] y = new int[E];
    for (int i = 0; i < E; ++i) {
      x[i] = sc.nextInt();
      y[i] = sc.nextInt();
    }

    System.out.println(solve(X, Y, x, y));

    sc.close();
  }

  static int solve(int X, int Y, int[] x, int[] y) {
    Vertex[] leftVertices = new Vertex[X];
    for (int i = 0; i < leftVertices.length; ++i) {
      leftVertices[i] = new Vertex();
    }
    Vertex[] rightVertices = new Vertex[Y];
    for (int i = 0; i < rightVertices.length; ++i) {
      rightVertices[i] = new Vertex();
    }
    for (int i = 0; i < x.length; ++i) {
      leftVertices[x[i]].adjs.add(y[i]);
      rightVertices[y[i]].adjs.add(x[i]);
    }

    int matchingCount = 0;
    for (int i = 0; i < leftVertices.length; ++i) {
      if (leftVertices[i].matching == -1
          && search(leftVertices, rightVertices, new boolean[rightVertices.length], i)) {
        ++matchingCount;
      }
    }

    return matchingCount;
  }

  static boolean search(
      Vertex[] leftVertices, Vertex[] rightVertices, boolean[] rightVisited, int leftIndex) {
    for (int rightIndex : leftVertices[leftIndex].adjs) {
      if (!rightVisited[rightIndex]) {
        rightVisited[rightIndex] = true;

        if (rightVertices[rightIndex].matching == -1
            || search(
                leftVertices, rightVertices, rightVisited, rightVertices[rightIndex].matching)) {
          leftVertices[leftIndex].matching = rightIndex;
          rightVertices[rightIndex].matching = leftIndex;

          return true;
        }
      }
    }

    return false;
  }
}

class Vertex {
  List<Integer> adjs = new ArrayList<>();
  int matching = -1;
}