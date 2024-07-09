import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int n = sc.nextInt();

    System.out.println(solve(n));

    sc.close();
  }

  static String solve(int n) {
    List<Integer> primeFactors = new ArrayList<>();
    int rest = n;
    for (int i = 2; i * i <= rest; ++i) {
      while (rest % i == 0) {
        primeFactors.add(i);
        rest /= i;
      }
    }
    if (rest != 1) {
      primeFactors.add(rest);
    }

    return String.format(
        "%d: %s", n, primeFactors.stream().map(String::valueOf).collect(Collectors.joining(" ")));
  }
}