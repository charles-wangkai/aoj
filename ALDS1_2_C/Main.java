import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N = sc.nextInt();
    String[] cards = new String[N];
    for (int i = 0; i < cards.length; ++i) {
      cards[i] = sc.next();
    }

    System.out.println(solve(cards));

    sc.close();
  }

  static String solve(String[] cards) {
    Map<String, Integer> cardToIndex =
        IntStream.range(0, cards.length).boxed().collect(Collectors.toMap(i -> cards[i], i -> i));

    for (int i = 0; i < cards.length; ++i) {
      int index = i;
      for (int j = i; j < cards.length; ++j) {
        if (cards[j].charAt(1) < cards[index].charAt(1)) {
          index = j;
        }
      }

      String temp = cards[i];
      cards[i] = cards[index];
      cards[index] = temp;
    }

    return String.format(
        "%s\nStable\n%s\n%s",
        Arrays.stream(cards)
            .sorted(
                Comparator.<String, Character>comparing(card -> card.charAt(1))
                    .thenComparing(cardToIndex::get))
            .collect(Collectors.joining(" ")),
        String.join(" ", cards),
        IntStream.range(0, cards.length - 1)
                .allMatch(
                    i ->
                        cards[i].charAt(1) != cards[i + 1].charAt(1)
                            || cardToIndex.get(cards[i]) < cardToIndex.get(cards[i + 1]))
            ? "Stable"
            : "Not stable");
  }
}