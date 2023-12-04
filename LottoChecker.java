import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LottoChecker {
    private ArrayList<int[][]> ticketArray;
    private int pointSum;
    private int ticketCount;

    public LottoChecker(String address) {
        parseInput(address);
        this.pointSum = findPointSum();
        this.ticketCount = findTicketCount();
    }

    public int getPointSum() {
        return this.pointSum;
    }

    public int getTicketCount() {
        return this.ticketCount;
    }

    private void parseInput(String address) {
        this.ticketArray = new ArrayList<int[][]>();
        
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();
                ticketArray.add(makeLottoTicket(str));
            }
            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int[][] makeLottoTicket(String str) {
        String[] arr = str.split(":");
        String[] strArr = arr[1].split("\\|");

        int[][] ticket = new int[3][];

        for (int i = 0; i < ticket.length - 1; i++) {
            String[] strNumbers = strArr[i].trim().split("\\s+");
            int[] numbers = new int[strNumbers.length];

            for (int j = 0; j < strNumbers.length; j++) {
                numbers[j] = Integer.parseInt(strNumbers[j].trim());
            }
            ticket[i] = numbers;
        }

        Arrays.sort(ticket[0]);
        ticket[2] = new int[] { 1 };

        return ticket;
    }

    private int findPointSum() {
        int result = 0;

        for (int i = 0; i < ticketArray.size(); i++) {
            result += findCardValue(ticketArray.get(i), i);
        }

        return result;
    }

    private int findCardValue(int[][] ticket, int index) {
        int value = 0;
        int count = 0;

        for (int i = 0; i < ticket[1].length; i++) {
            int num = ticket[1][i];
            if (Arrays.binarySearch(ticket[0], num) >= 0) {
                value = value == 0 ? 1 : value * 2;
                count++;
            }
        }

        for (int i = 0; i < count; i++) {           
            ticketArray.get(index+i+1)[2][0] += ticket[2][0];
        }

        return value;
    }

    private int findTicketCount() {
        int totalCount = 0;

        for (int[][] ticket : ticketArray) {
            totalCount += ticket[2][0];
        }

        return totalCount;
    }

}