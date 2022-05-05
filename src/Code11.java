
// Consultar taula https://en.wikipedia.org/wiki/Barcode#Linear_barcodes
// Code11: https://en.wikipedia.org/wiki/Code_11

// Generadors de codis:
//     https://barcode.tec-it.com/en/Code11
//     https://www.free-barcode-generator.net/code-11/
//     https://products.aspose.app/barcode/generate

import java.util.HashMap;

public class Code11 {

    // Codifica un String amb Code11
    static String encode(String s) {
        String bar = "█";
        String space = " ";
        String resultado = "";
        String stringBin = "";
        HashMap<Character, String> map = new HashMap<>();

        //Mapa con las posibles convinaciones
        map.put('0', "00001");
        map.put('1', "10001");
        map.put('2', "01001");
        map.put('3', "11000");
        map.put('4', "00101");
        map.put('5', "10100");
        map.put('6', "01100");
        map.put('7', "00011");
        map.put('8', "10010");
        map.put('9', "10000");
        map.put('-', "00100");
        map.put('*', "00110");
        //Encode
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            stringBin += map.get(c);
        }
        for (int i = 0; i < stringBin.length(); i++) {
            if (i < stringBin.length() - 1) {
                if (i % 2 != 0) {
                    if (stringBin.charAt(i) == '0') {
                        resultado += space;
                    } else {
                        resultado += space + space;
                    }
                } else {
                    if (stringBin.charAt(i) == '0') {
                        resultado += bar;
                    } else {
                        resultado += bar + bar;
                    }
                }
            }
        }

        return resultado;
    }

    // Decodifica amb Code11
    static String decode(String s) {
        //Decode
        for (int i = 0; i < s.length(); i++) {
            if (i % 2 == 0) {
                if (s.charAt(i) == 0) {

                } else {

                }
            } else {
                if (s.charAt(i) == 0) {

                } else {

                }
            }
        }
        return "";
    }

    // Decodifica una imatge. La imatge ha d'estar en format "ppm"
    public static String decodeImage(String str) {
        return "";
    }

    // Genera imatge a partir de codi de barres
    // Alçada: 100px
    // Marges: vertical 4px, horizontal 8px
    public static String generateImage(String s) {
        return "";
    }
}
