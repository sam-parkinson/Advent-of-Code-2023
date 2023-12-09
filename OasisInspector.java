import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class OasisInspector {
    ArrayList<ArrayList<Integer>> oases;
    int lastHistories;
    int firstHistories;

    public OasisInspector(String address) {
        parseInput(address);
        findNextHistories();
        this.lastHistories = findLastHistories();
        this.firstHistories = findFirstHistories();
    }

    public int getLastHistories() {
        return this.lastHistories;
    }

    public int getFirstHistories() {
        return this.firstHistories;
    }

    private void parseInput(String address) {
        oases = new ArrayList<ArrayList<Integer>>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                String[] str = stdin.nextLine().split(" ");
                ArrayList<Integer> intArr = new ArrayList<Integer>();

                intArr.add(0);

                for (int i = 0; i < str.length; i++) {
                    intArr.add(Integer.parseInt(str[i]));
                }
                oases.add(intArr);
            }            

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void findNextHistories() {
        for (ArrayList<Integer> oasis : oases) {
            findNextHistory(oasis);
        }
    }

    private void findNextHistory(ArrayList<Integer> oasis) {
        ArrayList<ArrayList<Integer>> history = new ArrayList<ArrayList<Integer>>();

        history.add(oasis);

        boolean allZeroes = false;
        int i = 0;

        while (!allZeroes) {
            allZeroes = true;
            ArrayList<Integer> nextLine = new ArrayList<Integer>();
            nextLine.add(0);
            for (int j = 1; j < history.get(i).size() - 1; j++) {
                int next = history.get(i).get(j + 1) - history.get(i).get(j);
                nextLine.add(next);
                if (next != 0) {
                    allZeroes = false;
                }
            }
            history.add(nextLine);

            i++;    
        }

        int last = 0;
        int first = 0;
        for (i = history.size() - 1; i >= 0; i--) {
            int index = history.get(i).size() - 1;
            last += history.get(i).get(index);
            first = history.get(i).get(1) - first;
            history.get(i).add(last);
            history.get(i).set(0, first);
        }
    }

    private int findLastHistories() {
        int result = 0;

        for (ArrayList<Integer> oasis : oases) {
            result += oasis.get(oasis.size() - 1);
        }

        return result;
    }

    private int findFirstHistories() {
        int result = 0;

        for (ArrayList<Integer> oasis : oases) {
            result += oasis.get(0);
        }

        return result;
    }
}