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
                // no third condition, we just skip text
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

    private void findTrueLocationsOld() {
        this.trueLocations = new ArrayList<Long>();

        for (int i = 0; i < seeds.length - 1; i += 2) {
            long start = seeds[i];
            long range = seeds[i + 1];

            for (int j = 0; j < range; j++) {
                trueLocations.add(checkMappings(start + j));
            }
        }
    }

    private void findTrueLocations() {
        this.trueLocations = new ArrayList<Long>();

        for (int i = 0; i < seeds.length - 1; i += 2) {
            long start = seeds[i];
            long range = seeds[i + 1];
            checkTrueMappings(start, range, 0);
        }
    }

    private void checkTrueMappings(long s, long r, int index) {
        System.out.println(index);
        long start = s;
        long range = r;
        long[][] map = mappings[index];
        long end;
        
        for (int i = 0; i < map.length; i++) {

            end = start + range;
            long[] mapLine = map[i];
            long mapLineEnd = mapLine[1] + mapLine[2];
            long diff = mapLine[0] - mapLine[1];

            if (start >= mapLine[1] && end <= mapLineEnd) {
                start += diff;
                if (index == mappings.length - 1) {
                    trueLocations.add(start);
                } else {
                    checkTrueMappings(start, range, index + 1);
                }
            } else if (start >= mapLine[1] && start <= mapLineEnd) {
                long newStart = start + diff;
                long newRange = mapLineEnd - newStart + diff;
                
                if (index == mappings.length - 1) {
                    trueLocations.add(newStart);
                } else {
                    checkTrueMappings(newStart, newRange, index + 1);
                }
                start = mapLineEnd + 1;
                range = end - start;
            } else if (end >= mapLine[1] && end <= mapLineEnd) {
                long newStart = mapLine[1] + diff;
                long newRange = end - mapLine[1];

                if (index == mappings.length - 1) {
                    trueLocations.add(newStart);
                } else {
                    checkTrueMappings(newStart, newRange, index + 1);
                }
                end = mapLine[1] - 1;
                range = end - start;
            } else if (start <= mapLine[1] && end >= mapLineEnd) {
                long newStart = mapLine[1] + diff;
                long newRange = mapLineEnd - mapLine[1];

                if (index == mappings.length - 1) {
                    trueLocations.add(newStart);
                } else {
                    checkTrueMappings(newStart, newRange, index + 1);
                }
            } else {
                if (i == map.length - 1) {
                    if (index == mappings.length - 1) {
                        trueLocations.add(start);
                    } else {
                        checkTrueMappings(start, range, index + 1);
                    }
                }
            }

            /* if (start >= mapLine[1] && start <= mapLineEnd) {
                long newStart = start + diff;
                if (index == mappings.length - 1) {
                    trueLocations.add(newStart);
                } else {
                    checkTrueMappings(newStart, end - newStart, index + 1);        
                }
                start = newStart;
                range = end - start;

            } else if (end <= mapLineEnd) {
                long newEnd = mapLine[1] + diff;
                if (index == mappings.length - 1) {
                    trueLocations.add(newEnd);
                } else {
                    checkTrueMappings(start, newEnd - start, index + 1);
                }
                range = newEnd - start;
                
            } else {
                if (index == mappings.length - 1) {
                    trueLocations.add(start);
                } else {
                    checkTrueMappings(start, range, index + 1);
                }
            } */
            /*
             * There are five conditions to test for
             * 
             * 1. The entire starting range is in the map range
             *  -- move the entire starting range to the next map range, adjusted by difference
             * 2. The start, but not the end, is in the map range
             *  -- slice the range into two parts
             *  -- the part of the starting range in the map range is moved to the next range, adjusted by difference
             *  -- the remaining range is continued in the current range, with the start adjusted accordingly
             * 3. The end, but not the start, is in the map range
             *  -- slice the range into two parts
             *  -- the part of the starting range in the map range is moved to the next range, adjusted by difference
             *  -- the remaining range is continued in the current range, with the end/range adjusted accordingly
             * 4. The entire map range is in the starting range
             *  -- why.
             *  -- I hate that this is a possibility.
             *  -- This requires the following:
             *  -- put entire map range in next range, adjusted by difference
             *  -- you know what fuck it just toss the entire starting range to the next thing like it's case 5. who cares. this won't cause any problems.
             * 5. There is no overlap between the starting and map ranges
             *  -- continue in the current range
             *  -- if this is at the end, go next
             */
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