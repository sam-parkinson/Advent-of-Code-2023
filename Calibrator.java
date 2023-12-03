import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Calibrator {
    private ArrayList<String> calibrations;
    private int calibrationSum;
    private int trueCalibrationSum;

    public Calibrator(String address) {
        calibrations = new ArrayList<String>();
        parseInput(address);
        calibrationSum = findCalibrationSum();
        trueCalibrationSum = findTrueCalibrationSum();
    }

    public int getCalibrationSum() {
        return this.calibrationSum;
    }

    public int getTrueCalibrationSum() {
        return this.trueCalibrationSum;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while(stdin.hasNextLine()) {
                calibrations.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int findCalibrationSum() {
        int result = 0;
        for (int i = 0; i < calibrations.size(); i++) {
            result += findCalibrationValue(calibrations.get(i));
        }
        return result;
    }

    private int findCalibrationValue(String str) {
        int firstDigit;
        int lastDigit;

        int i = 0;
        while (!Character.isDigit(str.charAt(i))) {
            i++;
        }

        firstDigit = Character.getNumericValue(str.charAt(i));

        i = str.length() - 1;
        while (!Character.isDigit(str.charAt(i))) {
            i--;
        }

        lastDigit = Character.getNumericValue(str.charAt(i));

        return (firstDigit * 10) + lastDigit;
    }

    private int findTrueCalibrationSum() {
        int result = 0;
        for (int i = 0; i < calibrations.size(); i++) {
            result += findTrueCalibrationValue(calibrations.get(i));
        }
        return result;
    }

    private int findTrueCalibrationValue(String str) {
        int firstDigit = -1;
        int lastDigit = -1;

        int i = 0;
        while (firstDigit == -1) {
            if (Character.isDigit(str.charAt(i))) {
                firstDigit = Character.getNumericValue(str.charAt(i));
            } else {
                firstDigit = findStringValue(str.substring(i));
            }
            i++;
        }

        i = str.length() - 1;
        while (lastDigit == -1) {
            if (Character.isDigit(str.charAt(i))) {
                lastDigit = Character.getNumericValue(str.charAt(i));
            } else {
                lastDigit = findStringValue(str.substring(i));
            }
            i--;
        }

        return (firstDigit * 10) + lastDigit;
    }

    private int findStringValue(String str) {
        int result = -1;

        if (str.length() < 3) {
            return result;
        }

        switch (str.charAt(0)) {
            case 'o': 
                if (str.substring(0, 3).equals("one"))
                    result = 1;
                break;
            case 't':
                if (str.substring(0, 3).equals("two"))
                    result = 2;
                else if (str.length() > 4 && str.substring(0, 5).equals("three"))
                    result = 3;
                break;
            case 'f':
                if (str.length() > 3 && str.substring(0, 4).equals("four"))
                    result = 4;
                else if (str.length() > 3 && str.substring(0, 4).equals("five"))
                    result = 5;
                break;
            case 's':
                if (str.substring(0, 3).equals("six"))
                    result = 6;
                else if (str.length() > 4 && str.substring(0, 5).equals("seven"))
                    result = 7;
                break;
            case 'e':
                if (str.length() > 4 && str.substring(0, 5).equals("eight"))
                    result = 8;
                break;
            case 'n':
                if (str.length() > 3 && str.substring(0, 4).equals("nine"))
                    result = 9;
                break;
            default:
                break;    
        }

        return result;
    }
}