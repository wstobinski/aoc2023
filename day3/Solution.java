import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;


// 532331
public class Solution {
    public static void main(String[] args) {
        try {

            String fileName = "input.txt";
            char[][] charArray = readFileTo2DCharArray(fileName);

            // Display the contents of the 2D char array
            for (char[] row : charArray) {
                for (char ch : row) {
                    System.out.print(ch + " ");
                }
                System.out.println();
            }

            NumberObject[] numbersAsStrings = new NumberObject[2000];
            populateNumbersArray(numbersAsStrings, charArray);
            System.out.println(numbersAsStrings.length);
//            int result = ver1(charArray, numbersAsStrings);
//            System.out.println(result);
            HashMap<Integer, AsterixObject> asterixMap = new HashMap<>();
            ver2(charArray, numbersAsStrings, asterixMap);
            int sum2 = 0;

            System.out.println(asterixMap);
            for (AsterixObject object: asterixMap.values()) {

                if (object.numberOfNeighbours == 2) {
                    sum2 += object.value;
                }

            }
            System.out.println("Problem2 = " + sum2);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean checkIsPart(int rowNumber, int colNumber, int stringLen, char[][] array) {

        int statingRowNumber = rowNumber > 0 ? rowNumber - 1 : rowNumber;
        int startingColNumber = colNumber > 0 ? colNumber - 1: colNumber;
        int finishRowNumber = rowNumber < array.length - 1 ? rowNumber + 1 : rowNumber;
        int finishColNumber = colNumber + stringLen < array[0].length - 1 ? colNumber + stringLen : colNumber + stringLen - 1;

        for (int row = statingRowNumber; row <= finishRowNumber; row++) {

            for (int col = startingColNumber; col <= finishColNumber; col++) {

                if (array[row][col] != '.' && !(row == rowNumber && (col >= colNumber && col < colNumber + stringLen))) {
                    return true;
                }
            }

        }
        return false;


    }

    // 80918939 vs 82301120
    private static void checkIsPart2(int rowNumber, int colNumber, int stringLen, String string, char[][] array, HashMap<Integer, AsterixObject> asterixMap) {

        int statingRowNumber = rowNumber > 0 ? rowNumber - 1 : rowNumber;
        int startingColNumber = colNumber > 0 ? colNumber - 1 : colNumber;
        int finishRowNumber = rowNumber < array.length - 1 ? rowNumber + 1 : rowNumber;
        int finishColNumber = colNumber + stringLen < array[0].length - 1 ? colNumber + stringLen : colNumber + stringLen - 1;
//        System.out.println(string);
        for (int row = statingRowNumber; row <= finishRowNumber; row++) {

            for (int col = startingColNumber; col <= finishColNumber; col++) {
                System.out.print(array[row][col]);
                if (array[row][col] == '*' && !(row == rowNumber && (col >= colNumber && col < colNumber + stringLen))) {
                    if (asterixMap.get(row * array[0].length + col) == null) {
                        asterixMap.put(row * array[0].length + col, new AsterixObject(row * array[0].length + col,
                                1, Integer.parseInt(string)));
                        System.out.println("Added " + string + " to * at " + row * array[0].length + col);

                    } else  {
                        AsterixObject current = asterixMap.get(row * array[0].length + col);
                        System.out.println("Multiplied " + string + " to * at " + row * array[0].length + col);
                        asterixMap.put(row * array[0].length + col, new AsterixObject(current.position,
                                current.getNumberOfNeighbours() + 1, current.getValue() * Integer.parseInt(string)));
                    }
                }

            }
            System.out.println();

        }

    }

    private static Integer ver1(char[][] charArray, String[] numbersAsStrings) {
        int searchIndex = 0;
        int sum = 0;
        for (int row = 0; row < charArray.length; row++) {

            String searchNumber = numbersAsStrings[searchIndex];
            String rowInString = new String(charArray[row]);

            int foundIndex = 0;
            while ((foundIndex = rowInString.substring(foundIndex).indexOf(searchNumber)) != -1) {

                boolean isPart = checkIsPart(row, foundIndex, searchNumber.length(), charArray);
                System.out.println("Number " + searchNumber + " = " + isPart);
                if (isPart) {
                    sum += Integer.parseInt(searchNumber);
                }

                searchNumber = numbersAsStrings[++searchIndex];
                if (searchNumber == "\n" || searchNumber == null) {
                    searchIndex++;
                    break;
                }


            }


        }
        return sum;
    }

    private static void ver2(char[][] charArray, NumberObject[] numbers, HashMap<Integer, AsterixObject> asterixMap) {

        for (NumberObject object: numbers) {
            if (object == null) {
                break;
            }
            String numberInString = String.valueOf(object.number);
            checkIsPart2(object.rowPos, object.colPos, numberInString.length(), numberInString, charArray, asterixMap);
        }
    }


    private static void populateNumbersArray(NumberObject[] numbers, char[][] charArray) {
        int numberIndex = 0;
        for (int row = 0; row < charArray.length; row++) {
            String numberStr = "";
            for (int col = 0; col < charArray[row].length; col++) {
                if (charArray[row][col] >= '0' && charArray[row][col] <= '9') {
                    numberStr += String.valueOf(charArray[row][col]);
                }
                if (numberStr.length() > 0 && !(charArray[row][col] >= '0' && charArray[row][col] <= '9')) {
                    numbers[numberIndex++] = new NumberObject(Integer.parseInt(numberStr), row, col - numberStr.length());
                    numberStr = "";
                }
            }
            if (numberStr.length() > 0) {
                numbers[numberIndex++] = new NumberObject(Integer.parseInt(numberStr), row, charArray[row].length - numberStr.length());

            }
        }
    }

    private static char[][] readFileTo2DCharArray(String fileName) throws IOException {
        // Open the file for reading
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        // Read the lines from the file
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append(System.lineSeparator());
        }

        // Close the file
        bufferedReader.close();

        // Convert the content to a 2D char array
        String content = stringBuilder.toString();
        String[] lines = content.split(System.lineSeparator());
        char[][] charArray = new char[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            charArray[i] = lines[i].toCharArray();
        }

        return charArray;
    }
}

