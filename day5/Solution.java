import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Solution {

    public static void main(String[] args) {
        readFile("input.txt");
    }

    // 112165657 too low
    public static void readFile(String fileName) {
        try (FileReader fileReader = new FileReader(fileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            String seedLine;
            line = bufferedReader.readLine();
            seedLine = line;
            bufferedReader.readLine(); // skip second line
            ArrayList<String> remainingLines = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {

                remainingLines.add(line);

            }
            // result for part2
            Long resultV2 = prepareSeedNumbersV2(seedLine, remainingLines, true);
            System.out.println("Found solution for location = " + resultV2);



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

    private static Long prepareSeedNumbersV2(String fileLine, ArrayList<String> remainingLines, boolean reversed) {
        String[] split = fileLine.split(" ");
        String[] numbers = Arrays.copyOfRange(split, 1, split.length);

        Collections.reverse(remainingLines);
        Long startingLocationNumber = Long.valueOf(remainingLines.get(0).split(" ")[0]);
        Long smallestSolution = Long.MAX_VALUE;
//        System.out.println(remainingLines);
        System.out.println("---");
        long biggestNumber = 0L;
        Long[] numbersInLong = new Long[numbers.length];
        for (int i = 0; i < numbers.length; i+=2) {
            numbersInLong[i] = Long.parseLong(numbers[i]);
            numbersInLong[i+1] = Long.parseLong(numbers[i+1]);
            if (numbersInLong[i] < smallestSolution) {
                smallestSolution = numbersInLong[i];
            }
            long currVal = numbersInLong[i];
            if (currVal > biggestNumber) {
                biggestNumber = currVal;
                biggestNumber += currVal + numbersInLong[i+1] - 1L;
            }
        }

        // starting number was assessed by trial and error 126102240 (by manipulating possibleLocation increment rate)
        for (long possibleLocation = 0; possibleLocation < biggestNumber; possibleLocation+=1) {

            long solutionWhichWouldGiveLocation = getLocationNumber(possibleLocation, remainingLines, reversed);
            System.out.println("Currently checking location number = " + possibleLocation);
            if (solutionWhichWouldGiveLocation < 0 && solutionWhichWouldGiveLocation < smallestSolution) {
                continue;
            }
            boolean solutionIsInSeeds = checkPossibleSolution(solutionWhichWouldGiveLocation, numbersInLong);
            System.out.println("----");
            if (solutionIsInSeeds) {
                return possibleLocation;
            }

        }

        return -1L;
    }

    private static boolean checkPossibleSolution(long solutionWhichWouldGiveLocation, Long[] numbers) {


        for (int i = 0; i < numbers.length; i+=2) {
            if (solutionWhichWouldGiveLocation >= numbers[i] && solutionWhichWouldGiveLocation < numbers[i] + numbers[i+1]) {
                return true;
            }
        }
        return false;
    }

    private static Long getLocationNumber(Long seedNumber, ArrayList<String> remainingLines, boolean reversed) {

        Long locationNumber = seedNumber;
        boolean numberChanged = false;
//        System.out.println(remainingLines);
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
            long startingRange;
            long offset;
            if (!reversed) {
                startingRange = Long.parseLong(split[1]);
                offset = Long.parseLong(split[0]);
            } else {
                startingRange = Long.parseLong(split[0]);
                offset = Long.parseLong(split[1]);
            }

            long rangeLength = Long.parseLong(split[2]);
            if ( !reversed && locationNumber >= startingRange && locationNumber < (startingRange + rangeLength)) {
                System.out.println("Condition met: " + locationNumber + " is between " + startingRange + " and " + (startingRange + rangeLength));
                locationNumber = offset + (locationNumber - startingRange);
                numberChanged = true;
                System.out.println("New value = " + locationNumber);
            }
            if (reversed && locationNumber >= startingRange && locationNumber < (startingRange + rangeLength)) {
//                System.out.println("Condition met: " + locationNumber + " is between " + offset + " and " + (offset + rangeLength));
                locationNumber = offset + (locationNumber - startingRange);
                numberChanged = true;
//                System.out.println("New value = " + locationNumber);
            }
        }
        if (!reversed) {
            System.out.println("Location number = " + locationNumber);
        } else {
            System.out.println("Solution number = " + locationNumber);
        }

        return locationNumber;

    }
}


