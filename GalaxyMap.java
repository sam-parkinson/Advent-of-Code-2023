import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class GalaxyMap {
    private static final int EMPTY_SPACE = 1000000;
    char[][] galaxyMap;
    int[] emptyRows;
    int[] emptyColumns;
    ArrayList<Integer[]> galaxyIndices;
    int minimumDistances;
    long millionMinimumDistances;

    public GalaxyMap(String address) {
        parseInput(address);
        findEmptyRows();
        findEmptyColumns();
        findGalaxyIndices();
        findMinimumDistances();
    }

    public int getMinimumDistances() {
        return this.minimumDistances;
    }

    public long getMillionMinimumDistances() {
        return this.millionMinimumDistances;
    }

    private void parseInput(String address) {
        ArrayList<String> arr = new ArrayList<String>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                arr.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.galaxyMap = new char[arr.size()][];
        for (int i = 0; i < galaxyMap.length; i++) {
            galaxyMap[i] = arr.get(i).toCharArray();
        }
    }

    private void findEmptyRows() {
        this.emptyRows = new int[galaxyMap.length];
        for (int i = 0; i < galaxyMap.length; i++) {
            boolean rowEmpty = true;
            for (int j = 0; j < galaxyMap[i].length; j++) {
                if (galaxyMap[i][j] == '#') {
                    rowEmpty = false;
                    break;
                }
            }
            if (rowEmpty) {
                emptyRows[i]++;
            }
        }
        for (int i = 1; i < emptyRows.length; i++) {
            emptyRows[i] += emptyRows[i - 1];
        }
    }

    private void findEmptyColumns() {
        this.emptyColumns = new int[galaxyMap[0].length];
        for (int i = 0; i < galaxyMap[0].length; i++) {
            boolean columnEmpty = true;
            for (int j = 0; j < galaxyMap.length; j++) {
                if (galaxyMap[j][i] == '#') {
                    columnEmpty = false;
                    break;
                }
            }
            if (columnEmpty) {
                emptyColumns[i]++;
            }
        }
        for (int i = 1; i < emptyColumns.length; i++) {
            emptyColumns[i] += emptyColumns[i - 1];
        }
    }

    private void findGalaxyIndices() {
        this.galaxyIndices = new ArrayList<Integer[]>();

        for (int i = 0; i < galaxyMap.length; i++) {
            for (int j = 0; j < galaxyMap[i].length; j++) {
                if (galaxyMap[i][j] == '#') {
                    galaxyIndices.add(new Integer[] {i, j});
                }
            }
        }
    }
    private void findMinimumDistances() {
        this.minimumDistances = 0;
        this.millionMinimumDistances = 0;

        for (int i = 0; i < galaxyIndices.size() - 1; i++) {
            int y1 = galaxyIndices.get(i)[0];
            int x1 = galaxyIndices.get(i)[1];
            for (int j = i + 1; j < galaxyIndices.size(); j++) {
                
                int y2 = galaxyIndices.get(j)[0];
                int x2 = galaxyIndices.get(j)[1];
                
                int adjustment = Math.abs(emptyRows[y1] - emptyRows[y2]) 
                    + Math.abs(emptyColumns[x1] - emptyColumns[x2]);

                minimumDistances += Math.abs(x1 - x2) + Math.abs(y1 - y2) + adjustment;
                millionMinimumDistances += Math.abs(x1 - x2) + Math.abs(y1 - y2) + (adjustment * EMPTY_SPACE) - adjustment;
            }
        }
    }
}
