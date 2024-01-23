import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
  public static void main(String[] args) throws Throwable {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    String[] instructions = new String[n];
    for (int i = 0; i < instructions.length; ++i) {
      instructions[i] = br.readLine();
    }

    System.out.println(solve(instructions));
  }

  static String solve(String[] instructions) {
    List<String> result = new ArrayList<>();
    Set<String> seen = new HashSet<>();
    for (String instruction : instructions) {
      String[] parts = instruction.split(" ");
      if (parts[0].equals("insert")) {
        seen.add(parts[1]);
      } else {
        result.add(seen.contains(parts[1]) ? "yes" : "no");
      }
    }

    return String.join("\n", result);
  }
}