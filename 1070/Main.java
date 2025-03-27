// https://www.hankcs.com/program/algorithm/aoj-1070-fimo-sequence.html

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (true) {
      int q = sc.nextInt();
      if (q == 0) {
        break;
      }

      sc.nextLine();
      String[] queries = new String[q];
      for (int i = 0; i < queries.length; ++i) {
        queries[i] = sc.nextLine();
      }

      System.out.println(solve(queries));
    }

    sc.close();
  }

  static String solve(String[] queries) {
    List<Integer> result = new ArrayList<>();
    FimoSequence fimoSequence = new FimoSequence();
    for (String query : queries) {
      String[] fields = query.split(" ");
      int operation = Integer.parseInt(fields[0]);
      if (operation == 0) {
        int value = Integer.parseInt(fields[1]);

        fimoSequence.add(value);
      } else if (operation == 1) {
        result.add(fimoSequence.remove());
      } else if (operation == 2) {
        result.add(fimoSequence.getLeftMin(1));
      } else if (operation == 3) {
        result.add(fimoSequence.getRightMin(1));
      } else if (operation == 4) {
        int i = Integer.parseInt(fields[1]);

        result.add(fimoSequence.getLeftMin(i));
      } else if (operation == 5) {
        int i = Integer.parseInt(fields[1]);

        result.add(fimoSequence.getRightMin(i));
      } else if (operation == 6) {
        result.add(fimoSequence.getLeftMax(1));
      } else if (operation == 7) {
        result.add(fimoSequence.getRightMax(1));
      } else if (operation == 8) {
        int i = Integer.parseInt(fields[1]);

        result.add(fimoSequence.getLeftMax(i));
      } else {
        int i = Integer.parseInt(fields[1]);

        result.add(fimoSequence.getRightMax(i));
      }
    }

    return String.format(
        "%s\nend", result.stream().map(String::valueOf).collect(Collectors.joining("\n")));
  }
}

class FimoSequence {
  List<Integer> leftValues = new ArrayList<>();
  List<Integer> leftMins = new ArrayList<>();
  List<Integer> leftMaxs = new ArrayList<>();
  List<Integer> rightValues = new ArrayList<>();
  int rightValueBeginIndex = 0;
  List<Integer> rightMins = new ArrayList<>();
  int rightMinBeginIndex = 0;
  List<Integer> rightMaxs = new ArrayList<>();
  int rightMaxBeginIndex = 0;

  void add(int value) {
    rightAdd(value);
    balance();
  }

  int remove() {
    int result = leftRemove();
    balance();

    return result;
  }

  int getLeftMin(int i) {
    return leftMins.get(leftMins.size() - i);
  }

  int getRightMin(int i) {
    return rightMins.get(rightMinBeginIndex + i - 1);
  }

  int getLeftMax(int i) {
    return leftMaxs.get(leftMaxs.size() - i);
  }

  int getRightMax(int i) {
    return rightMaxs.get(rightMaxBeginIndex + i - 1);
  }

  void leftAdd(int value) {
    leftValues.add(value);

    if (leftMins.isEmpty() || value < leftMins.get(leftMins.size() - 1)) {
      leftMins.add(value);
    }

    if (leftMaxs.isEmpty() || value > leftMaxs.get(leftMaxs.size() - 1)) {
      leftMaxs.add(value);
    }
  }

  int leftRemove() {
    int result = leftValues.remove(leftValues.size() - 1);

    if (leftMins.get(leftMins.size() - 1) == result) {
      leftMins.remove(leftMins.size() - 1);
    }

    if (leftMaxs.get(leftMaxs.size() - 1) == result) {
      leftMaxs.remove(leftMaxs.size() - 1);
    }

    return result;
  }

  void rightAdd(int value) {
    rightValues.add(value);

    while (rightMins.size() != rightMinBeginIndex && rightMins.get(rightMins.size() - 1) > value) {
      rightMins.remove(rightMins.size() - 1);
    }
    rightMins.add(value);

    while (rightMaxs.size() != rightMaxBeginIndex && rightMaxs.get(rightMaxs.size() - 1) < value) {
      rightMaxs.remove(rightMaxs.size() - 1);
    }
    rightMaxs.add(value);
  }

  int rightRemove() {
    int result = rightValues.get(rightValueBeginIndex);
    ++rightValueBeginIndex;

    if (rightMins.get(rightMinBeginIndex) == result) {
      ++rightMinBeginIndex;
    }

    if (rightMaxs.get(rightMaxBeginIndex) == result) {
      ++rightMaxBeginIndex;
    }

    return result;
  }

  void balance() {
    if (rightValues.size() - rightValueBeginIndex == leftValues.size() + 1) {
      leftAdd(rightRemove());
    }
  }
}
