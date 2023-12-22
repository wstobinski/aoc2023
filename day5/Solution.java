import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

public class Solution {

    public static void main(String[] args) {
        readFile("input.txt");
    }

    // 112165657 too low
    public static void readFile(String fileName) {
        try (FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            line = bufferedReader.readLine();
            Long[] seedNumbers = prepareSeedNumbers(line);
            bufferedReader.readLine(); // skip second line
            ArrayList<String> remainingLines = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {

                remainingLines.add(line);

            }
            System.out.println(remainingLines);
            ArrayList<Long> locationNumbers = new ArrayList<>();

            for (Long seedNumber: seedNumbers) {
                Long locationNumber = seedNumber;
                boolean numberChanged = false;
                for(String remainingLine: remainingLines) {
                    String[] split = remainingLine.split(" ");
                    if (split.length != 3) {
                        numberChanged = false;
                        continue;
                    }
                    if (numberChanged) {
                        continue;
                    }
                    numberChanged = false;
                    Long startingRange = Long.parseLong(split[1]);
                    Long rangeLength = Long.parseLong(split[2]);
                    if (locationNumber >= startingRange && locationNumber < (startingRange + rangeLength)) {
                        System.out.println("Condition met: " + locationNumber + " is between " + startingRange + " and " + (startingRange + rangeLength));
                        locationNumber = Long.parseLong(split[0]) + (locationNumber - startingRange);
                        numberChanged = true;
                        System.out.println("New value = " + locationNumber);
                    }
                }
                System.out.println("Location number = " + locationNumber);
                locationNumbers.add(locationNumber);
            }
            Collections.sort(locationNumbers);
            Long bestResult = locationNumbers.get(0);
            System.out.println(bestResult);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Long[] prepareSeedNumbers(String fileLine) {
        String[] split = fileLine.split(" ");
        String[] numbers = Arrays.copyOfRange(split, 1, split.length);
        Long[] seedNumbers = new Long[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            seedNumbers[i] = Long.parseLong(numbers[i]);
        }
        return seedNumbers;
    }
}


