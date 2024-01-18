// https://www.hankcs.com/program/algorithm/aoj-2251-merry-christmas.html

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int N = sc.nextInt();
      int M = sc.nextInt();
      int L = sc.nextInt();
      if (N == 0 && M == 0 && L == 0) {
        break;
      }

      int[] u = new int[M];
      int[] v = new int[M];
      int[] d = new int[M];
      for (int i = 0; i < M; ++i) {
        u[i] = sc.nextInt();
        v[i] = sc.nextInt();
        d[i] = sc.nextInt();
      }
      int[] p = new int[L];
      int[] t = new int[L];
      for (int i = 0; i < L; ++i) {
        p[i] = sc.nextInt();
        t[i] = sc.nextInt();
      }

      System.out.println(solve(N, u, v, d, p, t));
    }

    sc.close();
  }

  static int solve(int N, int[] u, int[] v, int[] d, int[] p, int[] t) {
    int L = p.length;

    int[][] distances = buildDistances(N, u, v, d);

    Vertex[] leftVertices = new Vertex[L];
    for (int i = 0; i < leftVertices.length; ++i) {
      leftVertices[i] = new Vertex();
    }
    Vertex[] rightVertices = new Vertex[L];
    for (int i = 0; i < rightVertices.length; ++i) {
      rightVertices[i] = new Vertex();
    }
    for (int i = 0; i < L; ++i) {
      for (int j = 0; j < L; ++j) {
        if (t[j] > t[i] && t[j] - t[i] >= distances[p[i]][p[j]]) {
          leftVertices[i].adjs.add(j);
          rightVertices[j].adjs.add(i);
        }
      }
    }

    int matchingCount = 0;
    for (int i = 0; i < leftVertices.length; ++i) {
      if (leftVertices[i].matching == -1
          && search(leftVertices, rightVertices, new boolean[rightVertices.length], i)) {
        ++matchingCount;
      }
    }

    return L - matchingCount;
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

  static int[][] buildDistances(int N, int[] u, int[] v, int[] d) {
    int[][] distances = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        distances[i][j] = (j == i) ? 0 : Integer.MAX_VALUE;
      }
    }
    for (int i = 0; i < u.length; ++i) {
      distances[u[i]][v[i]] = d[i];
      distances[v[i]][u[i]] = d[i];
    }

    for (int k = 0; k < N; ++k) {
      for (int i = 0; i < N; ++i) {
        for (int j = 0; j < N; ++j) {
          if (distances[i][k] != Integer.MAX_VALUE && distances[k][j] != Integer.MAX_VALUE) {
            distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
          }
        }
      }
    }

    return distances;
  }
}

class Vertex {
  List<Integer> adjs = new ArrayList<>();
  int matching = -1;
}