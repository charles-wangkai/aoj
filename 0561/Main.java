import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    int K = sc.nextInt();
    int[] C = new int[N];
    int[] G = new int[N];
    for (int i = 0; i < N; ++i) {
      C[i] = sc.nextInt();
      G[i] = sc.nextInt();
    }

    System.out.println(solve(C, G, K));

    sc.close();
  }

  static int solve(int[] C, int[] G, int K) {
    Map<Integer, List<Integer>> genreToPrices = new HashMap<>();
    for (int i = 0; i < C.length; ++i) {
      genreToPrices.putIfAbsent(G[i], new ArrayList<>());
      genreToPrices.get(G[i]).add(C[i]);
    }
    for (List<Integer> prices : genreToPrices.values()) {
      Collections.sort(prices, Comparator.reverseOrder());
    }

    int[] dp = new int[K + 1];
    for (List<Integer> prices : genreToPrices.values()) {
      int[] nextDp = dp.clone();

      int sum = 0;
      for (int i = 0; i < prices.size(); ++i) {
        sum += prices.get(i);
        for (int j = 0; j + i + 1 < dp.length; ++j) {
          nextDp[j + i + 1] = Math.max(nextDp[j + i + 1], dp[j] + (sum + (i + 1) * i));
        }
      }

      dp = nextDp;
    }

    return dp[K];
  }
}