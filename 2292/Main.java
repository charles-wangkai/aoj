// https://oi-wiki.org/string/pam/

import java.util.Scanner;

public class Main implements Runnable {
  public static void main(String[] args) {
    new Thread(null, new Main(), "Main", 1 << 26).start();
  }

  public void run() {
    Scanner sc = new Scanner(System.in);

    String S = sc.next();
    String T = sc.next();

    System.out.println(solve(S, T));

    sc.close();
  }

  static long solve(String S, String T) {
    PAM pamS = new PAM(S);
    PAM pamT = new PAM(T);

    return search(pamS, 0, pamT, 0) + search(pamS, 1, pamT, 1);
  }

  static long search(PAM pamS, int indexS, PAM pamT, int indexT) {
    long result = (indexS >= 2) ? ((long) pamS.cnt[indexS] * pamT.cnt[indexT]) : 0;

    for (int i = 0; i < 26; ++i) {
      if (pamS.ch[indexS][i] != 0 && pamT.ch[indexT][i] != 0) {
        result += search(pamS, pamS.ch[indexS][i], pamT, pamT.ch[indexT][i]);
      }
    }

    return result;
  }
}

class PAM {
  static final int LIMIT = 50000 + 5;

  int sz = -1;
  int tot;
  int last;
  int[] cnt = new int[LIMIT];
  int[][] ch = new int[LIMIT][26];
  int[] len = new int[LIMIT];
  int[] fail = new int[LIMIT];
  char[] s = new char[LIMIT];

  PAM(String str) {
    s[0] = '$';
    node(0);
    node(-1);
    fail[0] = 1;

    for (char c : str.toCharArray()) {
      insert(c);
    }
    for (int i = sz; i >= 0; --i) {
      cnt[fail[i]] += cnt[i];
    }
  }

  int node(int l) {
    ++sz;
    len[sz] = l;

    return sz;
  }

  void insert(char c) {
    ++tot;
    s[tot] = c;

    int now = getFail(last);
    if (ch[now][c - 'A'] == 0) {
      int x = node(len[now] + 2);
      fail[x] = ch[getFail(fail[now])][c - 'A'];
      ch[now][c - 'A'] = x;
    }
    last = ch[now][c - 'A'];
    ++cnt[last];
  }

  int getFail(int x) {
    while (s[tot - len[x] - 1] != s[tot]) {
      x = fail[x];
    }

    return x;
  }
}