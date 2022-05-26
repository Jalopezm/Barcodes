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
        String resultado = "";
        img = img.replace("\r", "");
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

        anchoNum = Integer.parseInt(anchoAlto[0]) * 3;
        anchoNum += 3;
        altoNum = Integer.parseInt(anchoAlto[1]);

        String codeActual = "";
        String code = "";
        String[] barCode = new String[anchoNum];
        for (int i = pos; i < anchoNum; ) {
            for (int j = 0; j < 3; j++) {
                codeActual += numeros[i] + "/";
                i++;
            }
            code = codeActual;
            codeActual = "";
            barCode[i] = code;

        }
        System.out.println("String" + Arrays.toString(barCode));
        resultado = decode(fromNumbertoBarCode(barCode, anchoNum));
        if (resultado == null) {
            codeActual = "";
            code = "";
            barCode = new String[anchoNum];

            int anchoNumNuevo = anchoNum + anchoNum;
            for (int i = anchoNum; i < anchoNumNuevo; ) {
                for (int j = 0; j < 3; j++) {
                    codeActual += numeros[i] + "/";
                    i++;
                }
                code = codeActual;
                codeActual = "";
                barCode[i - 2] = code;
            }
            resultado = decode(fromNumbertoBarCode(barCode, anchoNum));
        }
        return resultado;
    }

    private static String fromNumbertoBarCode(String[] barCode, int anchoNum) {
        int suma = 0;
        int contador = 0;
        String numero = "";
        for (int i = 0; i < anchoNum; i++) {
            if (barCode[i] == null) {
                int pos = 0;
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
            if (media >= 100) {
                barCode[i] = barCode[i].replaceAll("([0-9]+/)+", " ");
            } else {
                barCode[i] = barCode[i].replaceAll("([0-9]+/)+", "█");
            }
        }
        String code = "";
        for (int i = 0; i < barCode.length; i++) {
            String c = barCode[i];
            if (c == null) {
                continue;
            }
            code += c;
        }

        System.out.println(code);
        return code;

    }

    public static String generateImage(String s) {
        return "";
    }

    private static int maxValue(List arryValores) {
        int cActual = 0;
        int c = 0;
        for (int i = 0; i < arryValores.toArray().length; i++) {
            c = (Integer) arryValores.get(i);
            if (c > cActual) {
                cActual = c;
            }
        }
        System.out.println("cActual " + cActual);
        return cActual;
    }

    private static List arrayValores(String s) {
        List<Integer> list = new ArrayList();
        char c = ' ';
        int contadorBarras = 0;
        int contadorSpacios = 0;
        int mayor = 0;
        s = s + " ";
        for (int i = 0; i <= s.length(); i++) {
            if (i < s.length()) {
                c = s.charAt(i);
            } else {
                return list;
            }
            if (c == ' ') {
                contadorSpacios++;
                if (contadorBarras > 0) {
                    list.add(contadorBarras);
                    contadorBarras = 0;
                }
            } else {
                contadorBarras++;
                if (contadorSpacios > 0) {
                    list.add(contadorSpacios);
                    contadorSpacios = 0;
                }
            }
        }
        return list;
    }

    private static String calculoResultado(String s, String resultadoAux, String bar, String space, String sAux, String resultado, HashMap<String, String> map) {
        boolean False = true;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ' && c != '█') {
                return null;
            }
        }
        List lista = arrayValores(s);
        System.out.println(s);
        int valorMaximo = maxValue(lista);

        while (False) {
            resultadoAux = "";
            System.out.println("Lista: " + arrayValores(s));

            for (int i = 0; i < lista.toArray().length; i++) {
                Integer c = (Integer) lista.get(i);
                if (c >= valorMaximo) {
                    resultadoAux += 1;
                } else {
                    resultadoAux += 0;
                }
            }
            if (valorMaximo <= 1) {
                return null;
            }


            System.out.println(resultadoAux);
            for (int i = 0; i <= resultadoAux.length(); i++) {
                if (sAux.length() == 5) {
                    System.out.println("saux " + sAux);
                    resultado = resultado + (String) map.get(sAux);
                    System.out.println(resultado);
                    sAux = "";
                    continue;
                }
                if (i < resultadoAux.length()) {
                    char c = resultadoAux.charAt(i);
                    sAux = sAux + c;
                }
            }
            if (resultado.charAt(0) == '*') {
                False = false;
            } else {
                System.out.println(resultado);
                System.out.println("resultado-aux.lenght " + resultadoAux.length());
                resultado = "";
                valorMaximo--;
            }
        }
        return resultado;
    }

}