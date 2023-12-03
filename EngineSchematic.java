import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EngineSchematic {
    private char[][] schematic;
    private int partNumberSum;
    private int gearRatio;
    private HashMap<String, ArrayList<Integer>> gearList;
    private String adjacentGear;

    public EngineSchematic(String address) {
        gearList = new HashMap<String, ArrayList<Integer>>();
        parseInput(address);
        partNumberSum = findPartNumberSum();
        gearRatio = findGearRatio();
    }

    public int getPartNumberSum() {
        return this.partNumberSum;
    }

    public int getGearRatio() {
        return this.gearRatio;
    }

    private void parseInput(String address) {
        ArrayList<char[]> inputArr = new ArrayList<char[]>();
        
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                inputArr.add(stdin.nextLine().toCharArray());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.schematic = new char[inputArr.size()][];
        schematic = inputArr.toArray(schematic);
    }

    private int findPartNumberSum() {
        int sum = 0;
        int currentValue = 0;
        boolean isCurrPart = false;
        adjacentGear = "";

        for (int i = 0; i < schematic.length; i++) {
            for (int j = 0; j < schematic[i].length; j++) {
                if (Character.isDigit(schematic[i][j])) {
                    currentValue *= 10;
                    currentValue += Character.getNumericValue(schematic[i][j]);
                    if (!isCurrPart)
                        isCurrPart = isSymbolAdjacent(i, j);
                } else {
                    if (currentValue > 0) {
                        if (isCurrPart) {
                            sum += currentValue;
                            isCurrPart = false;
                        }
                        if (adjacentGear != "") {
                            gearList.putIfAbsent(adjacentGear, new ArrayList<Integer>());
                            gearList.get(adjacentGear).add(currentValue);
                            adjacentGear = "";
                        }
                        currentValue = 0;
                    }
                }
            }
            if (currentValue > 0) {
                if (isCurrPart) {
                    sum += currentValue;
                    isCurrPart = false;
                }
                if (adjacentGear != "") {
                    gearList.putIfAbsent(adjacentGear, new ArrayList<Integer>());
                    gearList.get(adjacentGear).add(currentValue);
                    adjacentGear = "";
                }
                currentValue = 0;
            }
        }

        if (currentValue > 0 && isCurrPart) {
            sum += currentValue;
            if (adjacentGear != "") {
                gearList.putIfAbsent(adjacentGear, new ArrayList<Integer>());
                gearList.get(adjacentGear).add(currentValue);
                adjacentGear = "";
            }
        }  

        return sum;
    }

    private boolean isSymbolAdjacent(int i, int j) {
        if (i > 0 && j > 0 && isCharacterSymbol(schematic[i - 1][j - 1])) {
            if (schematic[i-1][j-1] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = (i - 1) + "," + (j - 1);
            }
            return true;
        }        
        
        if (i > 0 && isCharacterSymbol(schematic[i - 1][j])) {
            if (schematic[i-1][j] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = (i - 1) + "," + j;
            }
            return true;
        }
        
        if (i > 0 && j < schematic[i].length - 1 && isCharacterSymbol(schematic[i - 1][j + 1])) {
            if (schematic[i-1][j+1] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = (i - 1) + "," + (j + 1);
            }
            return true;
        }
        
        if (j < schematic[i].length - 1 && isCharacterSymbol(schematic[i][j + 1])) {
            if (schematic[i][j+1] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = i + "," + (j + 1);
            }
            return true;
        }

        if (i < schematic.length - 1 && j < schematic[i].length - 1 && isCharacterSymbol(schematic[i + 1][j + 1])) {
            if (schematic[i+1][j+1] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = (i + 1) + "," + (j + 1);
            }
            return true;
        }

        if (i < schematic.length - 1 && isCharacterSymbol(schematic[i + 1][j])) {
            if (schematic[i+1][j] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = (i + 1) + "," + j;
            }
            return true;
        }

        if (i < schematic.length - 1 && j > 0 && isCharacterSymbol(schematic[i + 1][j - 1])) {
            if (schematic[i+1][j-1] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = (i + 1) + "," + (j - 1);
            }
            return true;
        }

        if (j > 0 && isCharacterSymbol(schematic[i][j - 1])) {
            if (schematic[i][j-1] == '*' && this.adjacentGear.equals("")) {
                adjacentGear = i + "," + (j - 1);
            }
            return true;
        }
        
        return false;
    }

    private boolean isCharacterSymbol(char c) {
        return !Character.isDigit(c) && c != '.';
    }

    private int findGearRatio() {
        int result = 0;

        Object[] keys = gearList.keySet().toArray();

        for (Object key : keys) {
            ArrayList<Integer> value = gearList.get(key.toString());

            if (value.size() == 2) {
                result += value.get(0) * value.get(1);
            }
        }

        return result;
    }
}