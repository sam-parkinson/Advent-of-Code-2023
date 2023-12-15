import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SpringChecker {
    protected static final int MULT = 5;

    protected class Spring {
        String record;
        String longRecord;
        int[] count;
        int[] longCount;
        int possiblePositions;
        int possibleLongPositions;

        protected Spring(String s) {
            String[] arr = s.split(" ");
            this.record = arr[0];
            findLongRecord();

            String[] intArr = arr[1].split(",");

            this.count = new int[intArr.length];
            for (int i = 0; i < count.length; i++) {
                count[i] = Integer.parseInt(intArr[i]);
            }
            findLongCount();

            this.possiblePositions = findPossiblePositions(this.record, this.count);
            // this.possibleLongPositions = findPossiblePositions(this.longRecord, this.longCount);
        }

        protected void findLongRecord() {
            StringBuilder str = new StringBuilder();

            for (int i = 0; i < MULT; i++) {
                str.append(record);
                if (i < MULT - 1)
                    str.append('?');
            }

            this.longRecord = str.toString();
        }

        protected void findLongCount() {
            this.longCount = new int[count.length * MULT];

            for (int i = 0; i < longCount.length; i++) {
                longCount[i] = count[i % count.length];
            }
        }

        protected int findPossiblePositions(String record, int[] count) {
            int sum = 0;
            int len = record.length();

            int maxTotalPounds = 0;
            int maxConsecPounds = 0;
            for (int i : count) {
                maxTotalPounds += i;
                maxConsecPounds = Math.max(i, maxConsecPounds);
            }

            System.out.println(maxConsecPounds);

            class Recorder {
                private StringBuilder record;
                private int count;

                private Recorder(String str) {
                    this.record = new StringBuilder(str);
                    this.count = 0;

                    for (char c : str.toCharArray()) {
                        if (c == '#')
                            count++;
                    }
                }

                private Recorder(StringBuilder str, int i) {
                    this.record = str;
                    this.count = i;
                }
            }
            
            ArrayDeque<Recorder> queue = new ArrayDeque<Recorder>();
            
            queue.add(new Recorder(record));

            ArrayList<String> garbage = new ArrayList<String>();
            garbage.add("");

            for (int i = 0; i < len; i++) {
                int queueLength = queue.size();

                for (int j = 0; j < queueLength; j++) {
                    Recorder current = queue.remove();
                    boolean tooLong = false;

                    List<String> nonPeriodArr = new ArrayList<String>(
                    Arrays.asList(current.record.toString().split("\\.+")));

                    nonPeriodArr.removeAll(garbage);
                    // System.out.println(nonPeriodArr.get(0));

                    List<String> hashArr = new ArrayList<String>(
                    Arrays.asList(current.record.toString().split("\\.+|\\?+")));

                    hashArr.removeAll(garbage);
                    // System.out.println(hashArr.get(0));

                    for (String str : hashArr) {
                        if (str.length() > maxConsecPounds) 
                            tooLong = true;
                    }

                    if (nonPeriodArr.size() <= count.length + 1 && !tooLong) {
                        // System.out.println(current.record);
                        if (current.record.charAt(i) == '?') {
                            StringBuilder period = new StringBuilder(current.record.toString());
                            StringBuilder hash = new StringBuilder(current.record.toString());

                            period.setCharAt(i, '.');
                            hash.setCharAt(i, '#');

                            queue.add(new Recorder(period, current.count));

                            if (current.count < maxTotalPounds)
                                queue.add(new Recorder(hash, current.count + 1));                        
                        } else {
                            queue.add(current);
                        }
                    } 
                }
            }

            System.out.println("Queue size: " + queue.size());

            for (Recorder r : queue) {
                int countIndex = 0;
                
                List<String> hashArr = new ArrayList<String>(
                    Arrays.asList(r.record.toString().split("\\.+")));

                hashArr.removeAll(garbage);
                
                boolean match = true;
                if (hashArr.size() == count.length) {
                    for (String str : hashArr) {
                        if (str.length() == count[countIndex]) {
                            countIndex++;
                        } else {
                            match = false;
                            break;
                        }
                    }
                    if (match)
                        sum++;
                } 
            }
            queue.clear();
            return sum;
        }

        protected int findPossibleLongPositions() {

            

            return 0;
        }
    }
    
    private Spring[] springs;
    private int totalPossiblePositions;
    private int totalLongPositions;

    public SpringChecker(String address) {
        parseInput(address);
        this.totalPossiblePositions = findTotalPossiblePositions();
        this.totalLongPositions = findTotalPossibleLongPositions();
    }

    public int getTotalPossiblePositions() {
        return this.totalPossiblePositions;
    }

    public int getTotalLongPositions() {
        return this.totalLongPositions;
    }

    private void parseInput(String address) {
        ArrayList<Spring> springArr = new ArrayList<Spring>();

        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                springArr.add(new Spring(stdin.nextLine()));
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.springs = new Spring[springArr.size()];
        springs = springArr.toArray(springs);
    }

    private int findTotalPossiblePositions() {
        int sum = 0;

        for (Spring spring : springs) {
            sum += spring.possiblePositions;
        }

        return sum;
    }

    private int findTotalPossibleLongPositions() {
        int sum = 0;

        for (Spring spring : springs) {
            sum += spring.possibleLongPositions;
        }

        return sum;
    }
}