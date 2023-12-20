import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PartSorter {
    protected class MachinePart {
        int x, m, a, s;

        protected MachinePart(int x, int m, int a, int s) {
            this.x = x;
            this.m = m;
            this.a = a;
            this.s = s;
        }

        private int getValue(char c) {
            switch (c) {
                case 'x': return this.x;
                case 'm': return this.m;
                case 'a': return this.a;
                default: return this.s;   
            }
        }
    }

    protected class Instruction {
        char condition;
        char operation;
        int value;
        String destination;

        protected Instruction (char condition, char operation, int value, String destination) {
            this.condition = condition;
            this.operation = operation;
            this.value = value;
            this.destination = destination;
        }

        protected Instruction (String destination) {
            this.destination = destination;
            this.value = -1;
        }
    }

    private ArrayList<MachinePart> partArr;
    private HashMap<String, Instruction[]> instructions;
    private int ratingSum;

    public PartSorter(String address) {
        parseInput(address);
        this.ratingSum = sumRatings();
    }

    public int getRatingSum() {
        return this.ratingSum;
    }

    private void parseInput(String address) {
        this.partArr = new ArrayList<MachinePart>();
        this.instructions = new HashMap<String, Instruction[]>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            boolean makingInstructions = true;

            while (stdin.hasNextLine()) {
                String str = stdin.nextLine();
                if (str.equals(""))
                    makingInstructions = false;
                else if (makingInstructions) {
                    addInstruction(str);
                } else {
                    addMachinePart(str.substring(1, str.length() - 1));
                }
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void addInstruction(String str) {
        String[] baseArr = str.split("\\{");

        String key = baseArr[0];
        String[] strArr = baseArr[1].substring(0, baseArr[1].length() - 1).split(",");
        Instruction[] instructionArr = new Instruction[strArr.length];

        for (int i = 0; i < strArr.length; i++) {
            
            if (strArr[i].length() > 1 && (
                strArr[i].charAt(1) == '<' || strArr[i].charAt(1) == '>'
            )) {
                String[] inst = strArr[i].split(":");

                char condition = inst[0].charAt(0);
                char operation = inst[0].charAt(1);
                int value = Integer.parseInt(inst[0].substring(2));
                String destination = inst[1];

                instructionArr[i] = new Instruction(condition, operation, value, destination);
            } else {
                instructionArr[i] = new Instruction(strArr[i]);
            }
        }

        instructions.put(key, instructionArr);
    }

    private void addMachinePart(String str) {
        String[] strArr = str.split(",");

        int x = Integer.parseInt(strArr[0].substring(2));
        int m = Integer.parseInt(strArr[1].substring(2));
        int a = Integer.parseInt(strArr[2].substring(2));
        int s = Integer.parseInt(strArr[3].substring(2));

        partArr.add(new MachinePart(x, m, a, s));
    }

    private int sumRatings() {
        int sum = 0;

        for (MachinePart part : partArr)
            sum += findRating(part);

        return sum;
    }

    private int findRating(MachinePart part) {
        boolean accepted = false;
        boolean rejected = false;
        String current = "in";

        while (accepted == false && rejected == false) {
            current = followDirection(current, part);
            if (current.equals("A"))
                accepted = true;
            else if (current.equals("R"))
                rejected = true;
        }

        if (accepted) {
            return part.x + part.m + part.a + part.s;
        }

        return 0;
    }

    private String followDirection(String str, MachinePart part) {
        Instruction[] instruction = instructions.get(str);
        
        for (Instruction inst : instruction) {
            if (inst.value == -1) {
                return inst.destination;
            }

            int val = part.getValue(inst.condition);
            if (inst.operation == '<') {
                if (val < inst.value)
                    return inst.destination;
            } else {
                if (val > inst.value)
                    return inst.destination;
            }

        }

        return instruction[instruction.length - 1].destination;
    }
}

/*
 * 
 * Thoughts on part 2:
 * 
 * either
 *  range
 * or
 *  work from ends
 * 
 */