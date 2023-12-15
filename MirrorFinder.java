import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class MirrorFinder {
    char[][][] mirrorArr;
    int[][][] mirrorCount;
    int reflectionSummary;

    public MirrorFinder(String address) {
        parseInput(address);
        findMirrorCounts();
        this.reflectionSummary = summarizeReflections();
    }

    public int getReflectionSummary() {
        return this.reflectionSummary;
    }

    private void parseInput(String address) {
        ArrayList<ArrayList<String>> outerArr = new ArrayList<ArrayList<String>>();
        ArrayList<String> innerArr = new ArrayList<String>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();

                if (str.equals("")) {
                    outerArr.add(innerArr);
                    innerArr = new ArrayList<String>();
                } else {
                    innerArr.add(str);
                }
            }
            outerArr.add(innerArr);

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.mirrorArr = new char[outerArr.size()][][];

        for (int i = 0; i < mirrorArr.length; i++) {
            ArrayList<String> strArr = outerArr.get(i);
            mirrorArr[i] = new char[strArr.size()][];

            for (int j = 0; j < mirrorArr[i].length; j++) {
                mirrorArr[i][j] = strArr.get(j).toCharArray();
            }
        }
    }

    private void findMirrorCounts() {
        this.mirrorCount = new int[mirrorArr.length][][];
        for (int i = 0; i < mirrorArr.length; i++) {
            mirrorCount[i] = findMirrorCount(mirrorArr[i]);
        }
    }

    private int[][] findMirrorCount(char[][] grid) {
        int[][] count = new int[2][];

        count[0] = new int[grid.length];
        count[1] = new int[grid[0].length];
 
        for (int i = 0; i < grid.length; i++) {
            int rowValue = 0;
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '#')
                    rowValue += Math.pow(2, j);
            }
            count[0][i] = rowValue;
        }

        for (int i = 0; i < grid[0].length; i++) {
            int columnValue = 0;
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] == '#')
                    columnValue += Math.pow(2, j);
            }
            count[1][i] = columnValue;
        }

        return count;
    }

    private int summarizeReflections() {
        int sum = 0;

        for (int i = 0; i < mirrorCount.length; i++) {
            System.out.println("Testing: " + i);
            sum += findReflection(mirrorCount[i]);
        }

        return sum;
    }

    private int findReflection(int[][] mirror) {
        for (int i = 0; i < mirror.length; i++) {
            boolean midpointFound = false;
            int midpoint = 0;
            int j = 1;
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            stack.push(mirror[i][0]);
            while (!stack.isEmpty() && j < mirror[i].length) {
                if (stack.peek() == mirror[i][j]) {
                    if (!midpointFound) {
                        midpointFound = true;
                        midpoint = j;
                    }
                    stack.pop();
                } else {
                    if (midpointFound) {
                        midpoint = -1;
                        midpointFound = false;
                    }   
                    stack.push(mirror[i][j]);
                }
                j++;
            }

            System.out.println(midpointFound);

            if (midpointFound && midpoint > -1)
                return i == 1 ? midpoint : midpoint * 100;
            
        }

        return 0;
    }
}

// 21275 -- too low
// 27390 -- too low

// missing: 15, 63

// look at: test line 29