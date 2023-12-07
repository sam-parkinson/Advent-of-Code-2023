public class Driver {
    public static void main(String[] args) {
        Calibrator calibrator = new Calibrator("inputs/day1.txt");
        CubeDrawing cubeDrawing = new CubeDrawing("inputs/day2.txt");
        EngineSchematic engineSchematic = new EngineSchematic("inputs/day3.txt");
        LottoChecker lottoChecker = new LottoChecker("inputs/day4.txt");
        FarmersAlmanac farmersAlmanac = new FarmersAlmanac("inputs/day5.txt");
        BoatRace boatRace = new BoatRace("inputs/day6.txt");
        CamelCards camelCards = new CamelCards("inputs/day7.txt");

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
        System.out.println("Problem 7.2: " );
    }
}