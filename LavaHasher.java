import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class LavaHasher {
    protected class Lens {
        String name;
        int focalLength;

        protected Lens(String name, int focalLength) {
            this.name = name;
            this.focalLength = focalLength;
        }
    }
    String[] initialValues;
    int[] hashedValues;
    ArrayList<LinkedList<Lens>> lensBoxes;
    int hashedSum, focalLengthSum;

    public LavaHasher(String address) {
        parseInput(address);
        hashValues();
        boxLenses();
        this.hashedSum = sumHashValues();
        this.focalLengthSum = sumFocalLength();
    }

    public int getHashedSum() {
        return this.hashedSum;
    }

    public int getFocalLengthSum() {
        return this.focalLengthSum;
    }

    private void parseInput(String address) {
        String str = "";
        
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            str = stdin.nextLine();

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        this.initialValues = str.split(",");
    }

    private void hashValues() {
        this.hashedValues = new int[initialValues.length];

        for (int i = 0; i < initialValues.length; i++) {
            hashedValues[i] = hashString(initialValues[i]);
        }
    }

    private int hashString(String str) {
        int result = 0;

        for (int i = 0; i < str.length(); i++) {
            result += (int) str.charAt(i);
            result *= 17;
            result = result % 256;
        }

        return result;
    }

    private void boxLenses() {
        this.lensBoxes = new ArrayList<LinkedList<Lens>>(255);
        for (int i = 0; i <256; i++) {
            lensBoxes.add(new LinkedList<Lens>());
        }

        for (int i = 0; i < initialValues.length; i++) {
            String[] str = initialValues[i].split("-|=");
            int boxIndex = hashString(str[0]);
            LinkedList<Lens> box = lensBoxes.get(boxIndex);

            if (str.length == 1) {
                Iterator<Lens> it = box.iterator();
                while (it.hasNext()) {
                    Lens lens = it.next();
                    if (lens.name.equals(str[0]))
                        it.remove();
                }
                
            } else {
                int focalLength = Integer.parseInt(str[1]);
                boolean found = false;
                for (Lens lens : box) {
                    if (lens.name.equals(str[0])) {
                        found = true;
                        lens.focalLength = focalLength;
                    }
                }
                if (!found) {
                    box.add(new Lens(str[0], focalLength));
                }
            }
        }
    }

    private int sumHashValues() {
        int sum = 0;
        for (int n : hashedValues)
            sum += n;
        return sum;
    }

    private int sumFocalLength() {
        int sum = 0;

        for (int i = 0; i < lensBoxes.size(); i++) {
            LinkedList<Lens> box = lensBoxes.get(i);

            if (box.size() > 0) {
                int boxLength = findFocalLength(box, i + 1);
                sum += boxLength;
            }    
        }

        return sum;
    }

    private int findFocalLength(LinkedList<Lens> box, int index) {
        int productSum = 0;

        int i = 1;

        for (Lens lens : box) {
            productSum += lens.focalLength * i * index;
            i++;
        }
        return productSum;
    }
}