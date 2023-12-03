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

            String[] numbersAsStrings = new String[5000];
            populateNumbersArray(numbersAsStrings, charArray);
//            int result = ver1(charArray, numbersAsStrings);
//            System.out.println(result);
            HashMap<Integer, Integer> asterixMap = new HashMap<>();
            ver2(charArray, numbersAsStrings, asterixMap);
            int sum2 = 0;
            int len = 0;
            System.out.println(asterixMap);
            for (int value: asterixMap.values()) {

                sum2 += value / 1000;
                len++;
            }
            System.out.println("Problem2 = " + sum2);
            System.out.println(len);



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
    private static void checkIsPart2(int rowNumber, int colNumber, int stringLen, String string, char[][] array, HashMap<Integer, Integer> asterixMap) {

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
                        asterixMap.put(row * array[0].length + col, Integer.parseInt(string));
                        System.out.println("Added " + string + " to * at " + row * array[0].length + col);

                    } else if (asterixMap.get(row * array[0].length + col) < 1000) {
                        System.out.println("Multiplied " + string + " to * at " + row * array[0].length + col);
                        asterixMap.put(row * array[0].length + col, (asterixMap.get(row * array[0].length + col) * Integer.parseInt(string)) * 1000);
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

    private static Integer ver2(char[][] charArray, String[] numbersAsStrings, HashMap<Integer, Integer> asterixMap) {
        int searchIndex = 0;
        int sum = 0;
        for (int row = 0; row < charArray.length; row++) {

            String searchNumber = numbersAsStrings[searchIndex];
            String rowInString = new String(charArray[row]);

            int foundIndex = 0;
            while ((foundIndex = foundIndex + rowInString.substring(foundIndex).indexOf(searchNumber)) != -1) {

            checkIsPart2(row, foundIndex, searchNumber.length(), searchNumber, charArray, asterixMap);
                searchNumber = numbersAsStrings[++searchIndex];
                if (searchNumber == "\n" || searchNumber == null) {
                    searchIndex++;
                    break;
                }


            }


        }
        return sum;
    }


    private static void populateNumbersArray(String[] numbers, char[][] charArray) {
        int numberIndex = 0;
        for (char[] row : charArray) {
            String numberStr = "";
            for (char ch : row) {
                if (ch >= '0' && ch <= '9') {
                    numberStr += String.valueOf(ch);
                }
                if (numberStr.length() > 0 && !(ch >= '0' && ch <= '9')) {
                    numbers[numberIndex++] = numberStr;
                    numberStr = "";
                }
            }
            if (numberStr.length() > 0) {
                numbers[numberIndex++] = numberStr;

            }
            numbers[numberIndex++] = "\n";
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

