public class Driver {
    public static void main(String[] args) {
        Calibrator calibrator = new Calibrator("inputs/day1.txt");

        System.out.println("Advent of Code 2023");

        System.out.println();
        System.out.println("Problem 1.1: " + calibrator.getCalibrationSum());
        System.out.println("Problem 1.2: " + calibrator.getTrueCalibrationSum());
    }
}