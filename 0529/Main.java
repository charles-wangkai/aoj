import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int N = Integer.parseInt(st.nextToken());
      int M = Integer.parseInt(st.nextToken());
      if (N == 0 && M == 0) {
        break;
      }

      int[] P = new int[N];
      for (int i = 0; i < P.length; ++i) {
        st = new StringTokenizer(br.readLine());
        P[i] = Integer.parseInt(st.nextToken());
      }

      System.out.println(solve(P, M));
    }
  }

  static int solve(int[] P, int M) {
    List<Integer> doubleSums = new ArrayList<>();
    doubleSums.add(0);
    for (int p : P) {
      doubleSums.add(p);
    }
    for (int i = 0; i < P.length; ++i) {
      for (int j = i; j < P.length; ++j) {
        doubleSums.add(P[i] + P[j]);
      }
    }
    Collections.sort(doubleSums);

    int result = 0;
    int leftIndex = 0;
    int rightIndex = doubleSums.size() - 1;
    while (leftIndex <= rightIndex) {
      if (doubleSums.get(leftIndex) + doubleSums.get(rightIndex) <= M) {
        result = Math.max(result, doubleSums.get(leftIndex) + doubleSums.get(rightIndex));
        ++leftIndex;
      } else {
        --rightIndex;
      }
    }

    return result;
  }
}