//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.HashMap;

public class Code11 {
    public Code11() {
    }

    static String encode(String s) {
        String bar = "█";
        String space = " ";
        String resultado = "█ ██  █ ";
        String stringBin = "";
        HashMap<Character, String> map = new HashMap();
        map.put('0', "000010");
        map.put('1', "100010");
        map.put('2', "010010");
        map.put('3', "110000");
        map.put('4', "001010");
        map.put('5', "101000");
        map.put('6', "011000");
        map.put('7', "000110");
        map.put('8', "100100");
        map.put('9', "100000");
        map.put('-', "001000");
        map.put('*', "00110");

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '*'){
                continue;
            }
            stringBin = stringBin + (String) map.get(c);
        }

        for (int i = 0; i < stringBin.length(); ++i) {
            if (i % 2 == 1) {
                if (stringBin.charAt(i) == '0') {
                    resultado = resultado + space;
                } else {
                    resultado = resultado + space + space;
                }
            } else if (stringBin.charAt(i) == '0') {
                resultado = resultado + bar;
            } else {
                resultado = resultado + bar + bar;
            }

        }
        resultado = resultado + "█ ██  █";
        return resultado;
    }

    static String decode(String s) {
        for (int i = 0; i < s.length(); ++i) {
            if (i % 2 == 0) {
                if (s.charAt(i) == 0) {
                }
            } else if (s.charAt(i) == 0) {
            }
        }

        return "";
    }

    public static String decodeImage(String str) {
        return "";
    }

    public static String generateImage(String s) {
        return "";
    }
}
