import java.util.*;

public class Code11 {

    static String encode(String s) {
        String result = "";

        HashMap<Character, String> map = new HashMap();
        map.put('0', "█ █ ██ ");
        map.put('1', "██ █ ██ ");
        map.put('2', "█  █ ██ ");
        map.put('3', "██  █ █ ");
        map.put('4', "█ ██ ██ ");
        map.put('5', "██ ██ █ ");
        map.put('6', "█  ██ █ ");
        map.put('7', "█ █  ██ ");
        map.put('8', "██ █  █ ");
        map.put('9', "██ █ █ ");
        map.put('-', "█ ██ █ ");
        map.put('*', "█ ██  █ ");

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            result = result + (String) map.get(c);
        }

        result = result.substring(0, result.length() - 1);

        return result;
    }

    static String decode(String s) {
        String result = "";

        HashMap<String, String> decodingMap = new HashMap<String, String>();
        decodingMap.put("00001", "0");
        decodingMap.put("10001", "1");
        decodingMap.put("01001", "2");
        decodingMap.put("11000", "3");
        decodingMap.put("00101", "4");
        decodingMap.put("10100", "5");
        decodingMap.put("01100", "6");
        decodingMap.put("00011", "7");
        decodingMap.put("10010", "8");
        decodingMap.put("10000", "9");
        decodingMap.put("00100", "-");
        decodingMap.put("00110", "*");

        s = s.trim();

        return calculateResult(s, result, decodingMap);
    }

    public static String decodeImage(String str) {
        String img = str;
        String result = "";
        int position = 0;
        int numericWidth = 0;
        int numericHeight = 0;

        img = img.replace("\r", "");

        String[] stringImage = img.split("\n");

        String widthHeight;
        if (!stringImage[1].contains("#")) {
            widthHeight = stringImage[1];
            position = 3;
        } else {
            widthHeight = stringImage[2];
            position = 4;
        }

        String widthHeightArray[] = widthHeight.split("\s");

        numericWidth = Integer.parseInt(widthHeightArray[0]);
        numericHeight = Integer.parseInt(widthHeightArray[1]);

        String[][] threePackBarcode = new String[numericWidth * numericHeight][3];

        for (int i = 0; i < numericWidth * numericHeight; i++) {
            for (int j = 0; j < 3; j++) {
                threePackBarcode[i][j] = stringImage[position];
                position++;
            }
        }

        String[][] doubleArrayBarcode = new String[numericHeight][numericWidth];

        int rowJump = 0;

        result = decode(fromNumbertoBarCode(doubleArrayBarcode, numericWidth, numericHeight, threePackBarcode, rowJump));

        int counter = 0;

        result = horizontalTest(result, numericWidth, numericHeight, threePackBarcode, doubleArrayBarcode, rowJump, counter);

        rowJump = 0;

        result = verticalTest(result, numericWidth, numericHeight, threePackBarcode, doubleArrayBarcode, rowJump);

        return result;
    }

    private static String verticalTest(String result, int numericWidth, int numericHeight, String[][] threePackBarcode, String[][] doubleArrayBarcode, int rowJump) {
        while (result == null) {
            rowJump += 30;
            if (result == null) {
                String verticalBarcodeString = verticalReading(numericHeight, numericWidth, threePackBarcode, doubleArrayBarcode, rowJump);
                result = decode(String.valueOf(verticalBarcodeString));
            }

            if (result == null) {
                String verticalBarcodeString = verticalReading(numericHeight, numericWidth, threePackBarcode, doubleArrayBarcode, rowJump);
                StringBuilder verticalReverse = new StringBuilder(verticalBarcodeString);
                result = decode(String.valueOf(verticalReverse.reverse()));
            }
        }
        return result;
    }

    private static String horizontalTest(String result, int numericWidth, int numericHeight, String[][] threePackBarcode, String[][] doubleArrayBarcode, int rowJump, int counter) {
        while (result == null) {
            rowJump += 30;
            String barcodeString = fromNumbertoBarCode(doubleArrayBarcode, numericWidth, numericHeight, threePackBarcode, rowJump);
            result = decode(barcodeString);
            if (result == null) {
                StringBuilder barcodeReverse = new StringBuilder(barcodeString);
                result = decode(String.valueOf(barcodeReverse.reverse()));
            }
            counter += 30;
            if (counter >= numericWidth) {
                break;
            }
        }
        return result;
    }

    private static String verticalReading(int numericHeight, int numericWidth, String[][] threePackBarcode, String[][] doubleArrayBarcode, int rowJump) {

        String code = "";
        imageToBarCode(numericHeight, numericWidth, threePackBarcode, doubleArrayBarcode);

        for (int k = rowJump; k < numericWidth; k++) {
            for (int l = 0; l < numericHeight; l++) {
                String c = doubleArrayBarcode[l][k];
                if (c == null) {
                    continue;
                }
                code += c;
            }
            break;
        }

        return code;
    }

    private static String fromNumbertoBarCode(String[][] doubleArrayBarcode, int numericWidth, int numericHeight, String[][] threePackBarcode, int rowJump) {

        imageToBarCode(numericHeight, numericWidth, threePackBarcode, doubleArrayBarcode);
        String code = "";

        for (int k = rowJump; k < numericHeight; k++) {
            for (int l = 0; l < numericWidth; l++) {
                String c = doubleArrayBarcode[k][l];
                if (c == null) {
                    continue;
                }
                code += c;
            }
            break;
        }
        return code;
    }

    private static String[][] imageToBarCode(int numericHeight, int numericWidth, String[][] threePackBarcode, String[][] doubleArrayBarcode) {
        int[][] numericBarcode = new int[numericHeight][numericWidth];
        int index = 0;

        for (int i = 0; i < numericHeight; i++) {
            for (int j = 0; j < numericWidth; j++) {
                int r = Integer.parseInt(threePackBarcode[index][0]);
                int g = Integer.parseInt(threePackBarcode[index][1]);
                int b = Integer.parseInt(threePackBarcode[index][2]);
                int RGB = (r + g + b) / 3;
                numericBarcode[i][j] = RGB;
                index++;

                int number = numericBarcode[i][j];

                if (number >= 100) {
                    doubleArrayBarcode[i][j] = " ";
                } else {
                    doubleArrayBarcode[i][j] = "█";
                }
            }
        }
        return doubleArrayBarcode;
    }

    public static String generateImage(String s) {

        String stringEncoded = Code11.encode(s);
        int imageHeight = 108;
        String result = "";
        String auxiliarResult = "";
        int imageWidth = 0;
        int barCounter = 0;
        int spaceCounter = 0;
        char c = ' ';
        String topBottomMargin = "255\n255\n255\n";
        String lateralMargin = "255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n";

        if (stringEncoded == null) {
            return null;
        }

        for (int j = 0; j <= stringEncoded.length(); j++) {
            if (j < stringEncoded.length()) {
                c = stringEncoded.charAt(j);
            }
            if (c == ' ') {
                spaceCounter++;
                if (barCounter > 0) {
                    if (barCounter == 1) {
                        auxiliarResult += "0\n0\n0\n0\n0\n0\n0\n0\n0\n";
                        imageWidth += 3;
                    } else {
                        auxiliarResult += "0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n0\n";
                        imageWidth += 10;
                    }
                    barCounter = 0;
                }
            } else {
                barCounter++;
                if (spaceCounter > 0) {
                    if (spaceCounter == 1) {
                        auxiliarResult += "255\n255\n255\n255\n255\n255\n255\n255\n255\n";
                        imageWidth += 3;
                    } else {
                        auxiliarResult += "255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n";
                        imageWidth += 10;
                    }
                    spaceCounter = 0;
                }
            }
        }
        auxiliarResult += "0\n0\n0\n0\n0\n0\n0\n0\n0\n";
        imageWidth += 19;
        auxiliarResult = lateralMargin + auxiliarResult + lateralMargin;

        for (int i = 0; i < 100; i++) {
            result += auxiliarResult;
        }
        String topMargin = "";
        for (int i = 0; i < imageWidth * 4; i++) {
            topMargin += topBottomMargin;
        }
        String bottomMargin = topMargin.substring(0, topMargin.length() - 1);

        return "P3\n" + imageWidth + "\s" + imageHeight + "\n255\n" + topMargin + result + bottomMargin;
    }

    private static List valueListGenerator(String s) {
        List<Integer> list = new ArrayList();

        char c = ' ';
        int barCounter = 0;
        int spaceCounter = 0;

        s = s + " ";

        for (int i = 0; i <= s.length(); i++) {
            if (i < s.length()) {
                c = s.charAt(i);
            } else {
                return list;
            }
            if (c == ' ') {
                spaceCounter++;
                if (barCounter > 0) {
                    list.add(barCounter);
                    barCounter = 0;
                }
            } else {
                barCounter++;
                if (spaceCounter > 0) {
                    list.add(spaceCounter);
                    spaceCounter = 0;
                }
            }
        }
        return list;
    }

    private static String calculateResult(String s, String result, HashMap<String, String> decodingMap) {
        boolean False = true;
        String auxiliarString = "";

        if (invalidCodes(s)) return null;

        List valueList = valueListGenerator(s);

        int maxValue = maxValue(valueList);

        while (False) {
            String auxiliarResult = "";

            if (maxValue <= 1) {
                return null;
            }

            for (int i = 0; i < valueList.toArray().length; i++) {
                Integer c = (Integer) valueList.get(i);
                if (c >= maxValue) {
                    auxiliarResult += 1;
                } else {
                    auxiliarResult += 0;
                }
            }

            for (int i = 0; i <= auxiliarResult.length(); i++) {
                if (auxiliarString.length() == 5) {
                    result = result + (String) decodingMap.get(auxiliarString);
                    auxiliarString = "";
                    continue;
                }
                if (i < auxiliarResult.length()) {
                    char c = auxiliarResult.charAt(i);
                    auxiliarString = auxiliarString + c;
                }
            }

            if (result.length() > 0) {
                if ((result.charAt(0) == '*' && result.charAt(result.length() - 1) == '*' && !result.contains("null"))) {
                    False = false;
                } else {
                    result = "";
                    maxValue--;
                }
            }
        }
        return result;
    }

    private static boolean invalidCodes(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ' && c != '█') {
                return true;
            }
        }
        return false;
    }

    private static int maxValue(List valueList) {
        int cActual = 0;
        int c = 0;

        for (int i = 0; i < valueList.toArray().length; i++) {
            c = (Integer) valueList.get(i);
            if (c > cActual) {
                cActual = c;
            }
        }
        return cActual;
    }
}