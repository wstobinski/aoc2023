import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Solution {
    public static void main(String[] args) {
        int result = readFile("input.txt");
        System.out.println(result);
    }

    public static boolean checkCubeInfo(String[] cubeInfoParts, HashMap<String, Integer> cubeLimitMap, HashMap<String, Integer> resultMap) {
        resultMap.put("red", 0);
        resultMap.put("green", 0);
        resultMap.put("blue", 0);
        boolean isValid = true;
        for (String cubeInfo: cubeInfoParts) {
            String[] cubeNumberColorInfo = cubeInfo.split(" ");
            int numberOfCubes = Integer.parseInt(cubeNumberColorInfo[0]);
            String color = cubeNumberColorInfo[1];
            if (numberOfCubes > resultMap.get(color)) {
                resultMap.put(color, numberOfCubes);
            }
            if (numberOfCubes > cubeLimitMap.get(color)) {
                isValid = false;
            }
        }
        return isValid;
    }

    public static Integer readFile(String fileName) {
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int gameNumber = 1;
            int sum = 0;
            int sumVer2 = 0;
            HashMap<String, Integer> cubeLimitMap = new HashMap<>();
            cubeLimitMap.put("red", 12);
            cubeLimitMap.put("green", 13);
            cubeLimitMap.put("blue", 14);
            while ((line = bufferedReader.readLine()) != null) {

                String[] parts = line.split(": ");
                String interestingPart = parts[1];
                interestingPart = interestingPart.replace(";", ",");
                String[] cubeInfoParts = interestingPart.split(", ");
                HashMap<String, Integer> resultMap = new HashMap<>();
                boolean isGameValid = checkCubeInfo(cubeInfoParts, cubeLimitMap, resultMap);
                int powerToAdd = 1;
                System.out.println(resultMap.values());
                for (int value: resultMap.values()) {
                    powerToAdd *= value;
                }
                sumVer2 += powerToAdd;
                System.out.println("Game nr" + gameNumber + " is " + isGameValid);
                if (isGameValid) {
                    sum += gameNumber;
                }
                gameNumber++;


            }
//            return sum; version 1
            return sumVer2; // version 2

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
