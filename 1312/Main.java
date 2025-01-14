// https://www.hankcs.com/program/algorithm/aoj-1312-wheres-wally.html

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
  static final int BASE_1 = 31;
  static final int BASE_2 = 37;

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int w = sc.nextInt();
      int h = sc.nextInt();
      int p = sc.nextInt();
      if (w == 0 && h == 0 && p == 0) {
        break;
      }

      String[] encodedImage = new String[h];
      for (int r = 0; r < encodedImage.length; ++r) {
        encodedImage[r] = sc.next();
      }
      String[] encodedPattern = new String[p];
      for (int r = 0; r < encodedPattern.length; ++r) {
        encodedPattern[r] = sc.next();
      }

      System.out.println(solve(w, h, p, encodedImage, encodedPattern));

      System.gc();
    }

    sc.close();
  }

  static int solve(int w, int h, int p, String[] encodedImage, String[] encodedPattern) {
    int[][] pattern = decode(p, p, encodedPattern);

    Set<Integer> extendedPatternHashes = new HashSet<>();
    for (int i = 0; i < 4; ++i) {
      extendedPatternHashes.add(buildHashToCount(pattern, p).keySet().iterator().next());
      rotate(pattern);
    }
    mirror(pattern);
    for (int i = 0; i < 4; ++i) {
      extendedPatternHashes.add(buildHashToCount(pattern, p).keySet().iterator().next());
      rotate(pattern);
    }

    int[][] image = decode(w, h, encodedImage);

    Map<Integer, Integer> hashToCount = buildHashToCount(image, p);

    return hashToCount.keySet().stream()
        .filter(extendedPatternHashes::contains)
        .mapToInt(hashToCount::get)
        .sum();
  }

  static Map<Integer, Integer> buildHashToCount(int[][] v, int size) {
    int placeValue1 = 1;
    for (int i = 0; i < size - 1; ++i) {
      placeValue1 *= BASE_1;
    }

    int placeValue2 = 1;
    for (int i = 0; i < size - 1; ++i) {
      placeValue2 *= BASE_2;
    }

    int height = v.length;
    int width = v[0].length;

    int[][] rowHashes = new int[height][width];
    for (int r = 0; r < height; ++r) {
      int rowHash = 0;
      for (int c = 0; c < width; ++c) {
        rowHash = rowHash * BASE_1 + v[r][c];

        if (c >= size - 1) {
          rowHashes[r][c - size + 1] = rowHash;

          rowHash -= v[r][c - size + 1] * placeValue1;
        }
      }
    }

    Map<Integer, Integer> hashToCount = new HashMap<>();
    for (int c = 0; c < width - size + 1; ++c) {
      int hash = 0;
      for (int r = 0; r < height; ++r) {
        hash = hash * BASE_2 + rowHashes[r][c];

        if (r >= size - 1) {
          hashToCount.put(hash, hashToCount.getOrDefault(hash, 0) + 1);

          hash -= rowHashes[r - size + 1][c] * placeValue2;
        }
      }
    }

    return hashToCount;
  }

  static void rotate(int[][] pattern) {
    int p = pattern.length;

    int[][] rotated = new int[p][p];
    for (int r = 0; r < p; ++r) {
      for (int c = 0; c < p; ++c) {
        rotated[r][c] = pattern[c][p - 1 - r];
      }
    }

    for (int r = 0; r < p; ++r) {
      for (int c = 0; c < p; ++c) {
        pattern[r][c] = rotated[r][c];
      }
    }
  }

  static void mirror(int[][] pattern) {
    int p = pattern.length;

    for (int r = 0; r < p; ++r) {
      for (int c1 = 0, c2 = p - 1; c1 < c2; ++c1, --c2) {
        int temp = pattern[r][c1];
        pattern[r][c1] = pattern[r][c2];
        pattern[r][c2] = temp;
      }
    }
  }

  static int[][] decode(int width, int height, String[] encoded) {
    int[][] result = new int[height][width];
    for (int r = 0; r < height; ++r) {
      String line =
          encoded[r]
              .chars()
              .map(c -> toValue((char) c))
              .mapToObj(
                  value -> String.format("%6s", Integer.toBinaryString(value)).replace(' ', '0'))
              .collect(Collectors.joining());
      for (int c = 0; c < width; ++c) {
        result[r][c] = line.charAt(c) - '0';
      }
    }

    return result;
  }

  static int toValue(char c) {
    if (c >= 'A' && c <= 'Z') {
      return c - 'A';
    }
    if (c >= 'a' && c <= 'z') {
      return c - 'a' + 26;
    }
    if (c >= '0' && c <= '9') {
      return c - '0' + 52;
    }

    return (c == '+') ? 62 : 63;
  }
}