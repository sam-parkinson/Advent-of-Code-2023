import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class FarmersAlmanac {
    long[] seeds;
    ArrayList<Long> locations;
    ArrayList<Long> trueLocations;
    long[][][] mappings;
    long minLocation;
    long trueMinLocation;

    public FarmersAlmanac(String address) {
        parseInput(address);
        findLocations();
        findTrueLocations();
        this.minLocation = findMinLocation(locations);
        this.trueMinLocation = findMinLocation(trueLocations);
    }

    public long getMinLocation() {
        return this.minLocation;
    }

    public long getTrueMinLocation() {
        return this.trueMinLocation;
    }

    private void parseInput(String address) {
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            String[] seedStr = stdin.nextLine().split(" ");

            this.seeds = new long[seedStr.length - 1];

            for (int i = 0; i < seeds.length; i++) {
                seeds[i] = Long.parseLong(seedStr[i + 1]);
            }

            stdin.nextLine();
            stdin.nextLine();

            ArrayList<long[][]> mappingsList = new ArrayList<long[][]>();
            ArrayList<long[]> list = new ArrayList<long[]>();

            while (stdin.hasNextLine()) {
                String line = stdin.nextLine();

                if (line.equals("") || !stdin.hasNextLine()) {
                    long[][] mapping = new long[list.size()][];
                    mapping = list.toArray(mapping);
                    mappingsList.add(mapping);
                    list.clear();
                } else if (Character.isDigit(line.charAt(0))) {
                    String[] lineStrArr = line.split(" ");
                    long[] lineIntArr = new long[lineStrArr.length];

                    for (int i = 0; i < lineStrArr.length; i++) {
                        lineIntArr[i] = Long.parseLong(lineStrArr[i]);
                    }

                    list.add(lineIntArr);
                } 
            }

            this.mappings = new long[mappingsList.size()][][];
            mappings = mappingsList.toArray(mappings);

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void findLocations() {
        this.locations = new ArrayList<Long>();

        for (int i = 0; i < seeds.length; i++) {
            long seed = seeds[i];

            locations.add(checkMappings(seed));
        }
    }

    private long checkMappings(long seed) {
        long n = seed;
        for (int i = 0; i < mappings.length; i++) {
            n = checkMapping(n, i);
        }
        return n;
    }

    private long checkMapping(long value, int index) {
        long[][] currentMap = mappings[index];
        
        for (int i = 0; i < currentMap.length; i++) {
            long[] mapLine = currentMap[i];
            if (value >= mapLine[1] && value <= mapLine[1] + mapLine[2]) {
                long diff = mapLine[0] - mapLine[1];
                return value + diff;
            }
        }

        return value;
    }

    private void findTrueLocations() {
        this.trueLocations = new ArrayList<Long>();

        for (int i = 0; i < seeds.length; i += 2) {
            long start = seeds[i];
            long range = seeds[i + 1];
            checkTrueMappings(start, range, 0);
        }
    }

    private void checkTrueMappings(long s, long r, int index) {
        long start = s;
        long range = r;
        long[][] map = mappings[index];
        long end;
        
        for (int i = 0; i < map.length; i++) {

            end = start + range;
            long[] mapLine = map[i];
            long mapLineEnd = mapLine[1] + mapLine[2];
            long diff = mapLine[0] - mapLine[1];

            if (s >= mapLine[1] && end <= mapLineEnd) {
                start += diff;
                if (index == mappings.length - 1) {
                    trueLocations.add(start);
                } else {
                    checkTrueMappings(start, range, index + 1);
                }
                break;
            } else if (s >= mapLine[1] && s < mapLineEnd) {
                long newStart = start + diff;
                long newRange = mapLineEnd - start;

                if (index == mappings.length - 1) {
                    trueLocations.add(newStart);
                } else {
                    checkTrueMappings(newStart, newRange, index + 1);
                }
                start = mapLineEnd;
                range = end - start;
            } else if (end > mapLine[1] && end <= mapLineEnd) {
                long newStart = mapLine[1] + diff;
                long newRange = end - mapLine[1];

                if (index == mappings.length - 1) {
                    //System.out.println(newStart);
                    //System.out.println(diff);
                    //System.out.println(start);
                    // trueLocations.add(Math.max(start, newStart)); // only my case works
                    trueLocations.add(newStart); // my case and David's case work
                    // trueLocations.add(start); // the provided test case and David's case work
                    // trueLocations.add(Math.min(start, newStart)); // the provided test case and David's case work
                } else {
                    checkTrueMappings(newStart, newRange, index + 1);
                }
                end = mapLine[1];
                range = end - start;
            } else if (s < mapLine[1] && end > mapLineEnd) {
                long newStart = mapLine[1] + diff;
                long newRange = mapLineEnd - mapLine[1];

                if (index == mappings.length - 1) {
                    trueLocations.add(newStart);
                } else {
                    checkTrueMappings(s, mapLine[1] - s, index);
                    checkTrueMappings(mapLineEnd, end - mapLineEnd + 1, index);
                    checkTrueMappings(newStart, newRange, index + 1);
                }
                break;
            } else if (i == map.length - 1) {
                if (index == mappings.length - 1) {
                    trueLocations.add(start);
                } else {
                    checkTrueMappings(start, range, index + 1);
                }
            }
        }
    }

    private long findMinLocation(ArrayList<Long> arr) {
        long min = arr.get(0);

        for (int i = 1; i < arr.size(); i++) {
            min = Math.min(min, arr.get(i));
        }

        return min;
    }
}
