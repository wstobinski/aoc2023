import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Solution {


    public static void main(String[] args) {

        int result = readFile("input.txt");
        System.out.println(result);
    }


    public static Integer readFile(String fileName) {

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;

            int sum = 0;
            int cardNumber = 0;
            ArrayList<ScratchCard> cards = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {

                int score = 0;
                cardNumber++;
                String[] parts = line.split(":\\s{1,2}");
                line = parts[1]; // numbers info
                parts = line.split(" \\| ");
                String winning = parts[0];
                String myNumbers = parts[1];

                String[] winningArray = winning.split("\\s{1,2}");
                removeEmptyStrings(winningArray);
                String[] myArray = myNumbers.split("\\s{1,2}");
                removeEmptyStrings(myArray);
                System.out.println(Arrays.toString(winningArray));
                System.out.println(Arrays.toString(myArray));
                String[] alreadyChecked = new String[myArray.length];
                int currIndex = 0;
                int numberOfWins = 0;
                for (String myNum: myArray) {
                    if (stringArrayContains(myNum, winningArray) && !stringArrayContains(myNum, alreadyChecked)) {
                        alreadyChecked[currIndex++] = myNum;
                        numberOfWins++;
                        if (score == 0) {
                            score = 1;
                        } else {
                            score *= 2;
                        }
                    }
                }
                System.out.println("Score = " + score);
                System.out.println("---");
                sum += score;
                cards.add(new ScratchCard(cardNumber, numberOfWins, false));

            }

            HashMap<Integer, Integer> numberOfCardsMap = new HashMap<>();
            int skippedCounter = 0;
            for (ScratchCard card: cards) {

                populateMap(card, numberOfCardsMap, cards);


            }
            System.out.println(numberOfCardsMap);

            int sum2 = 0;
            for (int val: numberOfCardsMap.values()) {
                sum2 += val;
            }

//        return sum;
        return sum2;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 11787590
    private static void populateMap(ScratchCard card, HashMap<Integer, Integer> map, ArrayList<ScratchCard> cards) {


        if (!map.containsKey(card.gameNumber)) {
            map.put(card.gameNumber, 1);
        } else {
            map.put(card.gameNumber, map.get(card.gameNumber) + 1);
        }

        for (int gm = card.gameNumber + 1; gm <= card.gameNumber + card.numberOfWins; gm++) {
            populateMap(new ScratchCard(gm, cards.get(gm - 1).numberOfWins, false), map, cards);
        }



    }

    private static boolean stringArrayContains(String str, String[] array) {

        for (String string: array) {
            if (str.equals(string)) {

                return true;
            }
        }
        return false;

    }

    private static boolean areUnprocessed(ArrayList<ScratchCard> cards) {

        for (ScratchCard card: cards) {
            if (!card.wasProcessed && card.numberOfWins > 0) {
                return true;
            }
        }
        return false;
    }

    private static String[] removeEmptyStrings(String[] array) {
        ArrayList<String> nonEmptyList = new ArrayList<>();

        // Iterate through the array and add non-empty strings to the list
        for (String str : array) {
            if (Objects.equals(str, "")) {
                continue;
            }
            nonEmptyList.add(str);
        }

        // Convert the ArrayList back to an array
        String[] resultArray = new String[nonEmptyList.size()];
        resultArray = nonEmptyList.toArray(resultArray);

        return resultArray;
    }


}
