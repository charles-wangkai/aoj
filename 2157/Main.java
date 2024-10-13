import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int k = sc.nextInt();
      if (k == 0) {
        break;
      }

      String initial = sc.next();
      String unlocking = sc.next();

      System.out.println(solve(initial, unlocking));
    }

    sc.close();
  }

  static int solve(String initial, String unlocking) {
    return search(unlocking, initial.toCharArray(), 0);
  }

  static int search(String unlocking, char[] digits, int index) {
    if (index == digits.length) {
      return 0;
    }
    if (digits[index] == unlocking.charAt(index)) {
      return search(unlocking, digits, index + 1);
    }

    int result = Integer.MAX_VALUE;
    int diff = unlocking.charAt(index) - digits[index];
    for (int i = index; i < digits.length; ++i) {
      digits[i] = (char) (Math.floorMod(digits[i] - '0' + diff, 10) + '0');
      result = Math.min(result, 1 + search(unlocking, digits, index + 1));
    }
    for (int i = index; i < digits.length; ++i) {
      digits[i] = (char) (Math.floorMod(digits[i] - '0' - diff, 10) + '0');
    }

    return result;
  }
}