import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());
    int R = Integer.parseInt(st.nextToken());
    int L = Integer.parseInt(st.nextToken());
    int[] d = new int[R];
    int[] t = new int[R];
    int[] x = new int[R];
    for (int i = 0; i < R; ++i) {
      st = new StringTokenizer(br.readLine());
      d[i] = Integer.parseInt(st.nextToken());
      t[i] = Integer.parseInt(st.nextToken());
      x[i] = Integer.parseInt(st.nextToken());
    }

    System.out.println(solve(N, d, t, x, L));
  }

  static int solve(int N, int[] d, int[] t, int[] x, int L) {
    if (d.length == 0) {
      return 1;
    }

    int[] scores = new int[N];

    SortedSet<Integer> teams =
        new TreeSet<>(
            Comparator.<Integer, Integer>comparing(i -> scores[i])
                .thenComparing(Comparator.<Integer, Integer>comparing(i -> i).reversed()));
    for (int i = 0; i < N; ++i) {
      teams.add(i);
    }

    int[] times = new int[N];
    times[teams.last()] += t[0];
    for (int i = 0; i < d.length; ++i) {
      teams.remove(d[i] - 1);
      scores[d[i] - 1] += x[i];
      teams.add(d[i] - 1);

      times[teams.last()] += ((i == d.length - 1) ? L : t[i + 1]) - t[i];
    }

    int maxTime = Arrays.stream(times).max().getAsInt();

    return IntStream.range(0, times.length).filter(i -> times[i] == maxTime).findFirst().getAsInt()
        + 1;
  }
}