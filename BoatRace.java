import java.io.File;
import java.util.Scanner;

public class BoatRace {
    int[] times;
    int[] distances;
    int[] possiblePresses;
    int possibleRecords;
    long realTime;
    long realDistance;
    long realRecord;

    public BoatRace(String address) {
        parseInput(address);
        makePossiblePresses();
        this.possibleRecords = findPossibleRecords();
        this.realRecord = checkRealRace();
    }

    public int getPossibleRecords() {
        return this.possibleRecords;
    }

    public long getRealRecord() {
        return this.realRecord;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);
            
            String[] timeStr = stdin.nextLine().split("\\s+");
            this.times = new int[timeStr.length - 1];

            for (int i = 0; i < times.length; i++) {
                times[i] = Integer.parseInt(timeStr[i + 1]);
            }

            String realStr = "";
            for (int i = 0; i < times.length; i++) {
                realStr += times[i];
            }

            this.realTime = Long.parseLong(realStr);

            String[] distStr = stdin.nextLine().split("\\s+");
            this.distances = new int[distStr.length - 1];

            for (int i = 0; i < times.length; i++) {
                distances[i] = Integer.parseInt(distStr[i + 1]);
            }

            realStr = "";
            for (int i = 0; i < distances.length; i++) {
                realStr += distances[i];
            }

            this.realDistance = Long.parseLong(realStr);

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void makePossiblePresses() {
        this.possiblePresses = new int[times.length];
        for (int i = 0; i < possiblePresses.length; i++) {
            possiblePresses[i] = checkRace(i);
        }
    }

    private int checkRace(int i) {
        int time = times[i];
        int distance = distances[i];

        int j = 0;
        while((time - j) * j <= distance) {
            j++;
        }    

        return (time - (j * 2) + 1);
    }

    private long checkRealRace() {
        long j = 0;
        while ((realTime - j) * j < realDistance) {
            j++;
        }

        return (realTime - (j * 2) + 1);
    }

    private int findPossibleRecords() {
        int result = 1;
        for (int presses : possiblePresses) {
            result *= presses;
        }
        return result;
    }
}