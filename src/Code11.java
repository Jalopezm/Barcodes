//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.*;

public class Code11 {
    public Code11() {
    }

    static String encode(String s) {
        String resultado = "";
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
            resultado = resultado + (String) map.get(c);
        }
        resultado = resultado.substring(0, resultado.length() - 1);
        return resultado;
    }

    static String decode(String s) {
        String bar = "";
        String space = "";
        String resultado = "";
        String sAux = "";

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("00001", "0");
        map.put("10001", "1");
        map.put("01001", "2");
        map.put("11000", "3");
        map.put("00101", "4");
        map.put("10100", "5");
        map.put("01100", "6");
        map.put("00011", "7");
        map.put("10010", "8");
        map.put("10000", "9");
        map.put("00100", "-");
        map.put("00110", "*");

        s = s.trim();
        String resultadoAux = s;


        return calculoResultado(s, resultadoAux, bar, space, sAux, resultado, map);
    }

    public static String decodeImage(String str) {
        String img = str;
        int pos = 0;
        String[] numeros = img.split("\n");
        int anchoNum = 0;
        int altoNum = 0;


        String sAnchoAlto;
        if (!numeros[1].contains("#")) {
            sAnchoAlto = numeros[1];
            pos = 3;
        } else {
            sAnchoAlto = numeros[2];
            pos = 4;
        }

        String anchoAlto[] = sAnchoAlto.split("\s");

        anchoNum = Integer.parseInt(anchoAlto[0])*3;
        altoNum = Integer.parseInt(anchoAlto[1]);

        String codeActual = "";
        String code = "";
        String[] barCode = new String[anchoNum];
        for (int i = pos; i < anchoNum;) {
            for (int j = 0; j < 3; j++) {
                codeActual += numeros[i] + "/";
                i++;
            }
            if (codeActual.length() % 3 == 0) {
                code = codeActual;
                codeActual = "";
                barCode[i-pos] = code;
            }
        }
        return decode(fromNumbertoBarCode(barCode, anchoNum));
    }

    private static String fromNumbertoBarCode(String[] barCode, int anchoNum) {
        int suma = 0;
        int contador = 0;
        String numero = "";
        for (int i = 0; i < anchoNum; i++) {
            if (barCode[i] == null) {int pos = 0;
                continue;
            }
            for (int j = 0; j < anchoNum; j++) {
                if (contador == 3) {
                    contador = 0;
                    break;
                }
                char c = barCode[i].charAt(j);
                if (c != '/') {
                    numero += c;
                } else {
                    int numeroC = Integer.parseInt(numero);
                    suma += numeroC;
                    contador++;
                    numero = "";
                }

            }
            int media = suma / 3;
            suma = 0;
            if (media <= 127.5) {
                barCode[i] = barCode[i].replaceAll("([0-9]+/)+", "█");
            } else {
                barCode[i] = barCode[i].replaceAll("([0-9]+/)+", " ");
            }
        }
        String code = "";
        for (int i = 0; i < barCode.length; i++) {
            String c = barCode[i];
            if (c == null){
                continue;
            }
            code += c;
        }
        return code;

    }

    public static String generateImage(String s) {
        return "";
    }

    private static int maxBarLenght(String s) {
        char c = ' ';
        int contador = 0;
        int mayor = 0;
        for (int i = 0; i < s.length(); ++i) {
            c = s.charAt(i);
            if (c == '█') {
                contador++;
            }
            if (contador > mayor) {
                mayor = contador;
            }
            if (c == ' ') {
                contador = 0;
            }
        }
        return mayor;
    }

    private static int maxSpaceLenght(String s) {
        char c = ' ';
        int contador = 0;
        int mayor = 0;
        for (int i = 0; i < s.length(); ++i) {
            c = s.charAt(i);
            if (c == ' ') {
                contador++;
            }
            if (contador > mayor) {
                mayor = contador;
            }
            if (c == '█') {
                contador = 0;
            }
        }
        return mayor;
    }

    private static String calculoResultado(String s, String resultadoAux, String bar, String space, String sAux, String resultado, HashMap<String, String> map) {
        boolean False = true;
        int contador = 0;

        while (False) {
            for (int i = 0; i < maxBarLenght(s); i++) {
                bar += "█";
            }
            for (int i = 0; i < maxSpaceLenght(s); i++) {
                space += " ";
            }
            if (contador > 0) {
                if (contador >= space.length() - 1) {
                    return null;
                }
                String greySpaces = space.substring(0, space.length() - contador);
                resultadoAux = resultadoAux.replace(greySpaces, "/");

            }

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c != ' ' && c != '█') {
                    return null;
                }
            }

            resultadoAux = resultadoAux + " ";
            resultadoAux = resultadoAux.replace(bar, "8");
            resultadoAux = resultadoAux.replace(space, "/");
            resultadoAux = resultadoAux.replaceAll("█+", "0");
            resultadoAux = resultadoAux.replaceAll("\\s+", "0");
            resultadoAux = resultadoAux.replace("8", "1");
            resultadoAux = resultadoAux.replace("/", "1");

            for (int i = 0; i < resultadoAux.length(); i++) {
                if (sAux.length() == 5) {
                    resultado = resultado + (String) map.get(sAux);
                    sAux = "";
                    continue;
                }
                char c = resultadoAux.charAt(i);
                sAux = sAux + c;
            }
            if (resultado.charAt(0) == '*') {
                False = false;
            } else {
                contador++;
                bar = "";
                space = "";
                resultadoAux = s;
                resultado = "";
            }
        }
        return resultado;
    }

}