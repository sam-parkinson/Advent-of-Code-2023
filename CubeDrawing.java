import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CubeDrawing {
    private ArrayList<String[]> games;
    private int validIdSum;
    private int minimumPowerSum;
    private HashMap<Character, Integer> validator;
   
    public CubeDrawing(String address) {
        games = new ArrayList<String[]>();
        validator = new HashMap<Character, Integer>();
        this.validator.put('r', 12);
        this.validator.put('g', 13);
        this.validator.put('b', 14);

        parseInput(address);
        validIdSum = findValidIdSum();
        minimumPowerSum = findMinimumPowerSum();
    }

    public int getValidIdSum() {
        return this.validIdSum;
    }

    public int getMinimumPowerSum() {
        return this.minimumPowerSum;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while(stdin.hasNextLine()) {
                String str = stdin.nextLine();
                String[] strArr = str.split(":");

                games.add(strArr[1].substring(1).split(";"));
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int findValidIdSum() {
        int result = 0;

        for (int i = 0; i < games.size(); i++) {
            if (isValidGame(games.get(i))) {
                result += i + 1;
            }
        }

        return result;
    }

    private boolean isValidGame(String[] game) {
        for (String round : game) {
            String[] checker = round.trim().split(",");

            if (!isValidRound(checker))
                return false;
        }
        
        return true;
    }

    private boolean isValidRound(String[] round) {
        for (String draw : round) {
            String[] broken = draw.trim().split(" ");

            if (validator.get(broken[1].trim().charAt(0)) < Integer.parseInt(broken[0]))
                return false;
        }

        return true;
    }

    private int findMinimumPowerSum() {
        int result = 0;

        for (String[] game : games) {
            result += findMinimumPower(game);
        }

        return result;
    }

    private int findMinimumPower(String[] game) {
        HashMap<Character, Integer> minimums = new HashMap<>();
        minimums.put('r', 1);
        minimums.put('b', 1);
        minimums.put('g', 1);

        for (String round : game) {
            String[] trimmedRound = round.trim().split(",");

            for (String draw : trimmedRound) {
                String[] splitDr = draw.trim().split(" ");
                int val = Integer.parseInt(splitDr[0]);
                if (val > minimums.get(splitDr[1].charAt(0)))
                    minimums.put(splitDr[1].charAt(0), val);
            }
        }

        return minimums.get('r') * minimums.get('b') * minimums.get('g');
    }
}