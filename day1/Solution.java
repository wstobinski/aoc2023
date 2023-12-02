import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Solution {

    public static void main(String[] args) {
       Integer sol = readPuzzleFile("input.txt");
       System.out.println(sol);
    }

    public static char getNumberFromLine(String line, boolean reversed) {

        if (reversed) {
            line = new StringBuilder(line).reverse().toString();
        }
        for (char c: line.toCharArray()) {
            if (c > '0' && c <= '9') {
                return c;
            }
        }


        return 0;
    }

    public static char getNumberFromLineVer2(String line, HashMap<String, Character> numberMap, boolean reversed) {

        String current = "";
        if (reversed) {
            line = new StringBuilder(line).reverse().toString();
        }

        for (char c: line.toCharArray()) {
            if (c > '0' && c <= '9') {
                return c;
            }
            current = current.concat(String.valueOf(c));

            for (String key: numberMap.keySet()) {
                if (current.contains(key) || (reversed && new StringBuilder(current).reverse().toString().contains(key))) {
                    return numberMap.get(key);
                }
            }


        }
        return 0;
    }

    public static Integer readPuzzleFile(String fileName) {
        try(FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int sum = 0;
            HashMap<String, Character> numberMap = new HashMap<>();
            numberMap.put("one", '1');
            numberMap.put("two", '2');
            numberMap.put("three", '3');
            numberMap.put("four", '4');
            numberMap.put("five", '5');
            numberMap.put("six", '6');
            numberMap.put("seven", '7');
            numberMap.put("eight", '8');
            numberMap.put("nine", '9');
            while ((line = bufferedReader.readLine()) != null) {
                char number1 = getNumberFromLineVer2(line, numberMap, false);
                char number2 = getNumberFromLineVer2(line, numberMap, true);

                if (number1 != 0 && number2 != 0) {
                    sum += Integer.parseInt("" + number1 + number2);
                } else if (number1 != 0) {
                    sum += Integer.parseInt("" + number1);
                } else if (number2 != 0) {
                    sum += Integer.parseInt("" + number2);
                }


            }
            return sum;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
