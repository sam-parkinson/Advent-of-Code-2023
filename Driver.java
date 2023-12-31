public class Driver {
    public static void main(String[] args) {
        Calibrator calibrator = new Calibrator("inputs/day1.txt");
        CubeDrawing cubeDrawing = new CubeDrawing("inputs/day2.txt");
        EngineSchematic engineSchematic = new EngineSchematic("inputs/day3.txt");
        LottoChecker lottoChecker = new LottoChecker("inputs/day4.txt");
        FarmersAlmanac farmersAlmanac = new FarmersAlmanac("inputs/day5.txt");
        BoatRace boatRace = new BoatRace("inputs/day6.txt");
        CamelCards camelCards = new CamelCards("inputs/day7.txt");
        DesertMap desertMap = new DesertMap("inputs/day8.txt");
        OasisInspector oasisInspector = new OasisInspector("inputs/day9.txt");
        PipeMaze pipeMaze = new PipeMaze("inputs/day10.txt");
        GalaxyMap galaxyMap = new GalaxyMap("inputs/day11.txt");
        // SpringChecker springChecker = new SpringChecker("inputs/day12.txt");
        MirrorFinder mirrorFinder = new MirrorFinder("inputs/day13.txt");

        LavaHasher lavaHasher = new LavaHasher("inputs/day15.txt");




        PartSorter partSorter = new PartSorter("inputs/day19.txt");

        System.out.println("Advent of Code 2023");

        System.out.println();
        System.out.println("Problem 1.1: " + calibrator.getCalibrationSum());
        System.out.println("Problem 1.2: " + calibrator.getTrueCalibrationSum());

        System.out.println();
        System.out.println("Problem 2.1: " + cubeDrawing.getValidIdSum());
        System.out.println("Problem 2.2: " + cubeDrawing.getMinimumPowerSum());

        System.out.println();
        System.out.println("Problem 3.1: " + engineSchematic.getPartNumberSum());
        System.out.println("Problem 3.2: " + engineSchematic.getGearRatio());

        System.out.println();
        System.out.println("Problem 4.1: " + lottoChecker.getPointSum());
        System.out.println("Problem 4.2: " + lottoChecker.getTicketCount());

        System.out.println();
        System.out.println("Problem 5.1: " + farmersAlmanac.getMinLocation());
        System.out.println("Problem 5.2: " + farmersAlmanac.getTrueMinLocation());

        System.out.println();
        System.out.println("Problem 6.1: " + boatRace.getPossibleRecords());
        System.out.println("Problem 6.2: " + boatRace.getRealRecord());

        System.out.println();
        System.out.println("Problem 7.1: " + camelCards.getTotalPokerWinnings());
        System.out.println("Problem 7.2: " + camelCards.getTotalJokerWinnings());

        System.out.println();
        System.out.println("Problem 8.1: " + desertMap.getMoveCount());
        System.out.println("Problem 8.2: " + desertMap.getTrueMoveCount());

        System.out.println();
        System.out.println("Problem 9.1: " + oasisInspector.getLastHistories());
        System.out.println("Problem 9.2: " + oasisInspector.getFirstHistories());

        System.out.println();
        System.out.println("Problem 10.1: " + pipeMaze.getMaxSteps());
        System.out.println("Problem 10.2: " + pipeMaze.getInsideCount());

        System.out.println();
        System.out.println("Problem 11.1: " + galaxyMap.getMinimumDistances());
        System.out.println("Problem 11.2: " + galaxyMap.getMillionMinimumDistances());

        // System.out.println();
        // System.out.println("Problem 12.1: " + springChecker.getTotalPossiblePositions());
        // System.out.println("Problem 12.2: " + springChecker.getTotalLongPositions());

        System.out.println();
        System.out.println("Problem 13.1: " + mirrorFinder.getReflectionSummary());

        System.out.println();
        System.out.println("Problem 15.1: " + lavaHasher.getHashedSum());
        System.out.println("Problem 15.2: " + lavaHasher.getFocalLengthSum());

        System.out.println();
        System.out.println("Problem 19.1: " + partSorter.getRatingSum());
    }
}