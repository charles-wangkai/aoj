import java.util.Scanner;

public class Main implements Runnable {
  static final int[] X_OFFSETS = {-1, 0, 1, 0};
  static final int[] Y_OFFSETS = {0, 1, 0, -1};

  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 28).start();
  }

  public void run() {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int w = sc.nextInt();
      int h = sc.nextInt();
      if (w == 0 && h == 0) {
        break;
      }

      int n = sc.nextInt();
      int[] x1 = new int[n];
      int[] y1 = new int[n];
      int[] x2 = new int[n];
      int[] y2 = new int[n];
      for (int i = 0; i < n; ++i) {
        x1[i] = sc.nextInt();
        y1[i] = sc.nextInt();
        x2[i] = sc.nextInt();
        y2[i] = sc.nextInt();
      }

      System.out.println(solve(w, h, x1, y1, x2, y2));
    }

    sc.close();
  }

  static int solve(int w, int h, int[] x1, int[] y1, int[] x2, int[] y2) {
    boolean[][] painted = new boolean[w][h];
    for (int i = 0; i < x1.length; ++i) {
      for (int x = x1[i]; x < x2[i]; ++x) {
        for (int y = y1[i]; y < y2[i]; ++y) {
          painted[x][y] = true;
        }
      }
    }

    int result = 0;
    for (int x = 0; x < w; ++x) {
      for (int y = 0; y < h; ++y) {
        if (!painted[x][y]) {
          search(painted, x, y);
          ++result;
        }
      }
    }

    return result;
  }

  static void search(boolean[][] painted, int x, int y) {
    int w = painted.length;
    int h = painted[0].length;

    painted[x][y] = true;

    for (int i = 0; i < X_OFFSETS.length; ++i) {
      int adjX = x + X_OFFSETS[i];
      int adjY = y + Y_OFFSETS[i];
      if (adjX >= 0 && adjX < w && adjY >= 0 && adjY < h && !painted[adjX][adjY]) {
        search(painted, adjX, adjY);
      }
    }
  }
}