// https://en.wikipedia.org/wiki/Code_93

import java.util.HashMap;
import java.util.Map;

public class Code93 {

    // Codifica emprant Code93
    static String encode(String str) {
        String resultado = "";
        str = "*" + str + "*";
        HashMap<String, String> map = new HashMap();
        map.put("0", "█   █ █  ");
        map.put("1", "█ █  █   ");
        map.put("2", "█ █   █  ");
        map.put("3", "█ █    █ ");
        map.put("4", "█  █ █   ");
        map.put("5", "█  █  █  ");
        map.put("6", "█  █   █ ");
        map.put("7", "█ █ █    ");
        map.put("8", "█   █  █ ");
        map.put("9", "█    █ █ ");
        map.put("A", "██ █ █   ");
        map.put("B", "██ █  █  ");
        map.put("C", "██ █   █ ");
        map.put("D", "██  █ █  ");
        map.put("E", "██  █  █ ");
        map.put("F", "██   █ █ ");
        map.put("G", "█ ██ █   ");
        map.put("H", "█ ██  █  ");
        map.put("I", "█ ██   █ ");
        map.put("J", "█  ██ █  ");
        map.put("K", "█   ██ █ ");
        map.put("L", "█ █ ██   ");
        map.put("M", "█ █  ██  ");
        map.put("N", "█ █   ██ ");
        map.put("O", "█  █ ██  ");
        map.put("P", "█   █ ██ ");
        map.put("Q", "██ ██ █  ");
        map.put("R", "██ ██  █ ");
        map.put("S", "██ █ ██  ");
        map.put("T", "██ █  ██ ");
        map.put("U", "██  █ ██ ");
        map.put("V", "██  ██ █ ");
        map.put("W", "█ ██ ██  ");
        map.put("X", "█ ██  ██ ");
        map.put("Y", "█  ██ ██ ");
        map.put("Z", "█  ███ █ ");
        map.put("-", "█  █ ███ ");
        map.put(".", "███ █ █  ");
        map.put(" ", "███ █  █ ");
        map.put("$", "███  █ █ ");
        map.put("%", "██ █ ███ ");
        map.put("/", "█ ██ ███ ");
        map.put("+", "█ ███ ██ ");
        map.put("@", "█  █  ██ ");//($)
        map.put("¥", "███ ██ █ ");//(/)
        map.put("¶", "███ █ ██ ");//(+)
        map.put("ß", "█  ██  █ ");//(%)

        HashMap<String, Integer> mapValues = new HashMap();
        mapValues.put("0", 0);
        mapValues.put("1", 1);
        mapValues.put("2", 2);
        mapValues.put("3", 3);
        mapValues.put("4", 4);
        mapValues.put("5", 5);
        mapValues.put("6", 6);
        mapValues.put("7", 7);
        mapValues.put("8", 8);
        mapValues.put("9", 9);
        mapValues.put("A", 10);
        mapValues.put("B", 11);
        mapValues.put("C", 12);
        mapValues.put("D", 13);
        mapValues.put("E", 14);
        mapValues.put("F", 15);
        mapValues.put("G", 16);
        mapValues.put("H", 17);
        mapValues.put("I", 18);
        mapValues.put("J", 19);
        mapValues.put("K", 20);
        mapValues.put("L", 21);
        mapValues.put("M", 22);
        mapValues.put("N", 23);
        mapValues.put("O", 24);
        mapValues.put("P", 25);
        mapValues.put("Q", 26);
        mapValues.put("R", 27);
        mapValues.put("S", 28);
        mapValues.put("T", 29);
        mapValues.put("U", 30);
        mapValues.put("V", 31);
        mapValues.put("W", 32);
        mapValues.put("X", 33);
        mapValues.put("Y", 34);
        mapValues.put("Z", 35);
        mapValues.put("-", 36);
        mapValues.put(".", 37);
        mapValues.put(" ", 38);
        mapValues.put("$", 39);
        mapValues.put("/", 40);
        mapValues.put("+", 41);
        mapValues.put("%", 42);
        mapValues.put("@", 43);//($)
        mapValues.put("¥", 44);//(/)
        mapValues.put("¶", 45);//(+)
        mapValues.put("ß", 46);//(%)

        int newCaracter = 0;
        String resultadoAux = str;
        int oldCaracter = 0;
        int caracter = 0;
        int contador = 0;

        for (int k = 0; k < 2; k++) {
            caracter = 0;
            oldCaracter = 0;
            newCaracter = 0;
            contador = 0;
            for (int i = resultadoAux.length() - 1; i < resultadoAux.length(); i--) {
                if (i == 0) {
                    break;
                }
                String c = String.valueOf(resultadoAux.charAt(i));
                if (c.equals("*")) {
                    continue;
                }
                contador++;
                if (k == 0 && contador == 21) {
                    contador = 1;
                }
                if (k == 1 && contador == 16) {
                    contador = 1;
                }
                newCaracter = contador * mapValues.get(c);
                oldCaracter = newCaracter;
                caracter = caracter + oldCaracter;
            }
            caracter = caracter % 47;

            for (Map.Entry<String, Integer> entry : mapValues.entrySet()) {
                if (entry.getValue() == caracter) {
                    if (resultadoAux.charAt(resultadoAux.length() - 1) == '*') {
                        resultadoAux = resultadoAux.substring(0, resultadoAux.length() - 1);
                    }
                    resultadoAux += entry.getKey();
                    resultadoAux += "*";
                    break;
                }
            }
        }

        for (int i = 0; i < resultadoAux.length(); ++i) {
            String c = String.valueOf(resultadoAux.charAt(i));
            if (i == 0) {
                map.put("*", "█ █ ████ ");//Apertura
            } else {
                map.put("*", "█ █ ████ █");//Cierre
            }
            System.out.println(c + map.get(c));
            resultado += map.get(c);
        }
        return resultado;
    }

    // Decodifica emprant Code93
    static String decode(String str) {
        return "";
    }

    // Decodifica una imatge. La imatge ha d""estar en format "ppm"
    public static String decodeImage(String str) {
        return "";
    }

    // Genera imatge a partir de barcode code93
    // Unitat barra mínima: 3 pixels
    // Alçada: 100px
    // Marges: vertical: 5px, horizontal: 15px
    public static String generateImage(String s) {
        return "";
    }
}
