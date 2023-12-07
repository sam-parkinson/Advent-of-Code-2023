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
            findValue();    // TODO: fix
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
    }

    private ArrayList<PokerHand> pokerHands;
    private ArrayList<JokerHand> jokerHands;
    private int totalPokerWinnings;

    public CamelCards(String address) {
        parseInput(address);
        Collections.sort(pokerHands);
        totalPokerWinnings = findTotalWinnings(this.pokerHands);
    }

    public int getTotalPokerWinnings() {
        return this.totalPokerWinnings;
    }

    private void parseInput(String address) {
        this.pokerHands = new ArrayList<PokerHand>();
        
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
 * Data structure
 * 
 * Array of PokerHands
 * 
 * PokerHands contain a hand string, a hand array, a count array, a value, and a bet
 * Do they need all of this shit?
 * I have no idea but it could be useful in part 2
 * 
 * VALUE enum type?
 * 
 * Sorting algorithm for hands
 * 
 * Go through input string
 * 
 * For each line, create PokerHand
 * 
 * Convert hand string into hand array -- this is puzzle 0
 * 
 *  A  K  Q  J 10 9 8 7 6 5 4 3 2
 * 14 13 12 11 10 9 8 7 6 5 4 3 2
 *  
 * Calculate value! -- this is puzzle 1, hardest
 * 
 * So, we have the hand
 * Do we make a hash map for each hand? Or is there a cheeky way to do this?
 * I think an array of length 15 is better for dealing with this
 *  Yes, do this
 * 
 * This array tells us nicely what the hand value is
 * 
 * -- max value of array == 5 -- 5 of a kind
 * -- max value of array == 4 -- 4 of a kind
 * -- max value of array == 3
 *      -- contains 2 ? full house : 3 of a kind
 * -- max value of array == 2
 *      -- contains other 2 ? two pair : pair
 * default -- high card
 * 
 * 
 * 1 - high card
 * 2 - pair
 * 3 - two pair
 * 4 - three of a kind
 * 5 - full house
 * 6 - four of a kind
 * 7 - five of a kind
 * 
 * Sorting algorithm -- this is puzzle 2
 * 
 * put worst hands at start of array
 * comparator -- first, put lower of two values, next, put lower first card (use array!)
 * 
 * Calculate winnings sum -- this is puzzle 3, easiest
 * for (int i = 0; i < hands.length; i++)
 *    result += hands[i].bet * (i + 1);
 */