import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  static final Map<Character, Integer> SYMBOL_TO_DELTA =
      Map.ofEntries(entry('/', 1), entry('\\', -1), entry('_', 0));

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    String diagram = sc.next();

    System.out.println(solve(diagram));

    sc.close();
  }

  static String solve(String diagram) {
    int[] lefts = new int[diagram.length()];
    int[] rights = new int[diagram.length()];
    for (int i = 0; i < diagram.length(); ++i) {
      if (i != 0) {
        lefts[i] = rights[i - 1];
      }

      rights[i] = lefts[i] + SYMBOL_TO_DELTA.get(diagram.charAt(i));
    }

    int[] leftMaxs = new int[diagram.length()];
    for (int i = 0; i < leftMaxs.length; ++i) {
      leftMaxs[i] = Math.max((i == 0) ? Integer.MIN_VALUE : leftMaxs[i - 1], lefts[i]);
    }

    int[] rightMaxs = new int[diagram.length()];
    for (int i = rightMaxs.length - 1; i >= 0; --i) {
      rightMaxs[i] =
          Math.max((i == rightMaxs.length - 1) ? Integer.MIN_VALUE : rightMaxs[i + 1], rights[i]);
    }

    int[] doubleHeights =
        IntStream.range(0, diagram.length())
            .map(
                i ->
                    Math.max(
                        0,
                        (Math.min(leftMaxs[i], rightMaxs[i]) - Math.min(lefts[i], rights[i])) * 2
                            - ((lefts[i] == rights[i]) ? 0 : 1)))
            .toArray();

    List<Integer> floods = new ArrayList<>();
    int doubleArea = 0;
    for (int i = 0; i <= doubleHeights.length; ++i) {
      if (i != doubleHeights.length
          && doubleHeights[i] != 0
          && !(doubleHeights[i] == 1 && diagram.charAt(i) == '\\')) {
        doubleArea += doubleHeights[i];
      } else {
        if (doubleArea != 0) {
          floods.add(doubleArea / 2);
        }

        if (i != doubleHeights.length) {
          doubleArea = doubleHeights[i];
        }
      }
    }

    return String.format(
        "%d\n%d%s%s",
        floods.stream().mapToInt(Integer::intValue).sum(),
        floods.size(),
        floods.isEmpty() ? "" : " ",
        floods.stream().map(String::valueOf).collect(Collectors.joining(" ")));
  }
}