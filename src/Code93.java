// https://en.wikipedia.org/wiki/Code_93

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Code93 {

    // Codifica emprant Code93
    static String encode(String str) {
        String result = "";

        //Mapa para Codificar
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
        map.put("¥", "███ ██ █ ");//(%)
        map.put("¶", "███ █ ██ ");//(/)
        map.put("ß", "█  ██  █ ");//(+)

        //Mapa de Valores
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
        mapValues.put("¥", 44);//(%)
        mapValues.put("¶", 45);//(/)
        mapValues.put("ß", 46);//(+)

        str = encodeReplace(str, map);

        //Añadimos los caracteres de control de
        //principio y final
        str = "∇" + str + "∇";

        String auxiliarResult = str;

        //k sera nuestra variable para saber en que vuelta estamos
        //ya que  necesitaremos hacer 2 vueltas.
        //1a limite 20 para los caracteres de control
        //2a limite 15 para los caracteres de control
        for (int k = 0; k < 2; k++) {

            int caracter = 0;
            int caracterValue = 0;
            int counter = 0;

            //bucle inverso de atras hacia delante
            for (int i = auxiliarResult.length() - 1; i < auxiliarResult.length(); i--) {
                if (i == 0) {
                    break;
                }
                String c = String.valueOf(auxiliarResult.charAt(i));
                System.out.println(str);

                //Saltamos el caracter especial de principio y final del String
                if (c.equals("∇")) {
                    continue;
                }
                counter++;
                //Si el contador llega a 21
                //lo cambiamos a 1 ya que el limite de la primera vuelta es 20
                if (k == 0 && counter == 21) {
                    counter = 1;
                }
                //Si el contador llega a 16
                //lo cambiamos a 1 ya que el limite de la segunda vuelta es 16
                if (k == 1 && counter == 16) {
                    counter = 1;
                }

                //Multiplicamos el contador por el valor del mapa de c
                caracterValue = counter * mapValues.get(c);

                //y sumamos el anterior mas el valor calculado
                caracter = caracter + caracterValue;
            }
            //Nos quedamos con el resto de la division
            caracter = caracter % 47;

            //Recorremos el mapa de valores proporcionando un valor para recibir la key
            for (Map.Entry<String, Integer> entry : mapValues.entrySet()) {
                if (entry.getValue() == caracter) {
                    //Añadimos el caracter encontrado al String
                    if (auxiliarResult.charAt(auxiliarResult.length() - 1) == '∇') {
                        auxiliarResult = auxiliarResult.substring(0, auxiliarResult.length() - 1);
                    }
                    auxiliarResult += entry.getKey();
                    auxiliarResult += "∇";
                    break;
                }
            }
            System.out.println(auxiliarResult);
        }
        //Codificamos el Nuevo string a codigo de barras
        for (int i = 0; i < auxiliarResult.length(); ++i) {
            String c = String.valueOf(auxiliarResult.charAt(i));
            if (i == 0) {
                map.put("∇", "█ █ ████ ");//Apertura
            } else {
                map.put("∇", "█ █ ████ █");//Cierre
            }
            result += map.get(c);
        }
        return result;
    }

    private static String encodeReplace(String str, HashMap<String, String> map) {
        String strReplaced = "";
        String charReplaced = "";
        char c = ' ';

        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (map.containsKey(String.valueOf(c))){
                strReplaced+=c;
                continue;
            }
            if (!map.containsKey(String.valueOf(c))) {
               if (c == ',') {
                    charReplaced = "¶L";
                } else if (c == '*') {
                    charReplaced = "¶J";
                } else {
                    charReplaced = "ß" + String.valueOf(c).toUpperCase(Locale.ROOT);
                }
            }
            strReplaced += charReplaced;
        }
        return strReplaced;
    }


    // Decodifica emprant Code93
    static String decode(String str) {

//        for (int i = 0; i < auxiliarResult.length(); ++i) {
//            String c = String.valueOf(auxiliarResult.charAt(i));
//            if (i == 0) {
//                map.put("█ █ ████ ", "∇");//Apertura
//            } else {
//                map.put("█ █ ████ █", "∇");//Cierre
//            }
//            result += map.get(c);
//        }
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
