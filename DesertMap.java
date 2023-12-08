import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DesertMap {
    private char[] instructions;
    private ArrayList<String> startingPoints;
    private HashMap<String, HashMap<Character, String>> map;
    private int moveCount;
    private long trueMoveCount;

    public DesertMap(String address) {
        parseInput(address);
        this.moveCount = findMoveCount("AAA", "ZZZ");
        this.trueMoveCount = findTrueMoveCount();
    }

    public int getMoveCount() {
        return this.moveCount;
    }

    public long getTrueMoveCount() {
        return this.trueMoveCount;
    }

    private void parseInput(String address) {
        this.map = new HashMap<String, HashMap<Character, String>>();
        this.startingPoints = new ArrayList<String>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            this.instructions = stdin.nextLine().toCharArray();
            stdin.nextLine();

            while (stdin.hasNextLine()) {
                String[] str = stdin.nextLine().split(" ");
                
                String key = str[0];

                if (key.charAt(2) == 'A')
                    startingPoints.add(key);

                String left = str[2].substring(1, 4);
                String right = str[3].substring(0, 3);

                HashMap<Character, String> dir = new HashMap<>();
                dir.put('L', left);
                dir.put('R', right);

                map.put(key, dir);
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int findMoveCount(String begin, String end) {
        int count = 0;

        String curr = begin;

        while (!curr.equals(end)) {
            curr = map.get(curr).get(instructions[count % instructions.length]);
            count++;
        }

        return count;
    }

    private long findTrueMoveCount() {
        int[] moves = new int[startingPoints.size()];

        for (int i = 0; i < startingPoints.size(); i++) {
            moves[i] = findSingleTrueMoveCount(startingPoints.get(i));
        }

        long result = moves[0];

        for (int i = 1; i < moves.length; i++) {
            result = lcm(result, moves[i]);
        }

        return result;
    }

    private int findSingleTrueMoveCount(String begin) {
        int count = 0;

        String curr = begin;

        while (curr.charAt(2) != 'Z') {
            curr = map.get(curr).get(instructions[count % instructions.length]);
            count++;
        }

        return count;
    }

    private long lcm (long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        } else {
            long gcd = gcd(a, b);
            return (a * b) / gcd;
        }
    }

    private long gcd (long a, long b) {
        if (a == 0 || b == 0) {
            return a + b;
        } else {
            long absA = Math.abs(a);
            long absB = Math.abs(b);
            long max = Math.max(absA, absB);
            long min = Math.min(absA, absB);
            return gcd(max % min, min);
        }
    }
}
