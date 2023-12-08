import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CamelCards {
    private class PokerHand implements Comparable<PokerHand> {
        protected String handString;
        protected int[] handArr;
        protected int[] countArr;
        protected int value;
        protected int bet;

        protected PokerHand(String str) {
            String[] arr = str.split(" ");
            this.handString = arr[0];
            this.bet = Integer.parseInt(arr[1]);
            makeHandArr();
            makeCountArr();
            findValue();
        }

        protected void makeHandArr() {
            this.handArr = new int[handString.length()];

            for (int i = 0; i < handArr.length; i++) {
                char c = handString.charAt(i);

                switch (c) {
                    case 'A':
                        handArr[i] = 14;
                        break;
                    case 'K':
                        handArr[i] = 13;
                        break;
                    case 'Q':
                        handArr[i] = 12;
                        break;
                    case 'J':
                        handArr[i] = 11;
                        break;
                    case 'T':
                        handArr[i] = 10;
                        break;
                    default:
                        handArr[i] = Character.getNumericValue(c);
                        break;
                }
            }
        }

        protected void makeCountArr() {
            this.countArr = new int[15];

            for (int card : handArr) {
                countArr[card]++;
            }
        }

        protected void findValue() {
            int countTwos = 0;
            int max = 0;

            for (int count : countArr) {
                max = Math.max(max, count);
                if (count == 2)
                    countTwos++;
            }

            switch (max) {
                case 5:
                    this.value = 7;
                    break;
                case 4: 
                    this.value = 6;
                    break;
                case 3:
                    this.value = 4 + countTwos;
                    break;
                case 2:
                    this.value = countTwos + 1;
                    break;
                default:
                    this.value = 1;
                    break;
            }
        }
        
        @Override
        public int compareTo(PokerHand o) {
            if (this.value < o.value) 
                return -1;
            else if (this.value > o.value) 
                return 1;
            
            for (int i = 0; i < this.handArr.length; i++) {
                if (this.handArr[i] < o.handArr[i])
                    return -1;
                else if (this.handArr[i] > o.handArr[i])
                    return 1;
            }

            return 0;
        }

        @Override
        public String toString() {
            return this.handString + " " + this.bet;
        }
    }
    private class JokerHand extends PokerHand {
        private JokerHand(String str) {
            super(str);
            String[] arr = str.split(" ");
            this.handString = arr[0];
            this.bet = Integer.parseInt(arr[1]);
            makeHandArr();
            makeCountArr();
            findValue();
        }

        @Override
        protected void makeHandArr() {
            this.handArr = new int[handString.length()];

            for (int i = 0; i < handArr.length; i++) {
                char c = handString.charAt(i);

                switch (c) {
                    case 'J':
                        handArr[i] = 1;
                        break;
                    case 'A':
                        handArr[i] = 14;
                        break;
                    case 'K':
                        handArr[i] = 13;
                        break;
                    case 'Q':
                        handArr[i] = 12;
                        break;                    
                    case 'T':
                        handArr[i] = 10;
                        break;
                    default:
                        handArr[i] = Character.getNumericValue(c);
                        break;
                }
            }
        }

        @Override
        protected void findValue() {
            int countTwos = 0;
            int countJokers = countArr[1];
            int max = 0;

            for (int count : countArr) {
                max = Math.max(max, count);
                if (count == 2)
                    countTwos++;
            }

            switch (max) {
                case 5:
                    this.value = 7;
                    break;
                case 4: 
                    this.value = countJokers > 0 ? 7 : 6; // 7 if joker, else 6
                    break;
                case 3:
                    if (countJokers == 3)
                        this.value = 6 + countTwos; // either 6 or 7
                    else
                        this.value = countJokers > 0 ? 5 + countJokers : 4 + countTwos; // either 7, 6 or 5
                    break;
                case 2:
                    if (countTwos == 2 && countJokers > 0)
                        this.value = 4 + countJokers;
                    else
                        this.value = countJokers > 0 ? 4 : countTwos + 1 + countJokers;
                    break;
                default:
                    this.value = 1 + countJokers;
                    break;
            }
        }
    }

    private ArrayList<PokerHand> pokerHands;
    private ArrayList<PokerHand> jokerHands;
    private int totalPokerWinnings;
    private int totalJokerWinnings;

    public CamelCards(String address) {
        parseInput(address);
        Collections.sort(pokerHands);
        Collections.sort(jokerHands);
        totalPokerWinnings = findTotalWinnings(this.pokerHands);
        totalJokerWinnings = findTotalWinnings(this.jokerHands);
    }

    public int getTotalPokerWinnings() {
        return this.totalPokerWinnings;
    }

    public int getTotalJokerWinnings() {
        return this.totalJokerWinnings;
    }

    private void parseInput(String address) {
        this.pokerHands = new ArrayList<PokerHand>();
        this.jokerHands = new ArrayList<PokerHand>();
        
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();
                pokerHands.add(new PokerHand(str));
                jokerHands.add(new JokerHand(str));
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private int findTotalWinnings(ArrayList<PokerHand> hands) {
        int winnings = 0;

        for (int i = 0; i < hands.size(); i++) {
            winnings += hands.get(i).bet * (i+1);
        }

        return winnings;
    }
}

/*
 * 7 == 5 of a kind
 * 6 == 4 of a kind
 * 5 == full house
 * 4 == 3 of a kind
 * 3 == two pair
 * 2 == pair
 * 1 == high card
 */
