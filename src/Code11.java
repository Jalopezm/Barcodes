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


        return calculoResultado(s, resultadoAux, sAux, resultado, map);
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

        anchoNum = Integer.parseInt(anchoAlto[0]);
        altoNum = Integer.parseInt(anchoAlto[1]);

        String codeActual = "";
        String code = "";
        String[][] barcode3 = new String[anchoNum * altoNum][3];
        for (int i = 0; i < anchoNum * altoNum; i++) {
            for (int j = 0; j < 3; j++) {
                barcode3[i][j] = numeros[pos];
                pos++;
            }
        }
        String[][] barcode = new String[altoNum][anchoNum];

        int saltoFila = 0;
        resultado = decode(fromNumbertoBarCode(barcode, anchoNum, altoNum, barcode3, saltoFila));
        int contador = 0;
        while (resultado == null) {
            saltoFila += 30;
            String barcodeString = fromNumbertoBarCode(barcode, anchoNum, altoNum, barcode3, saltoFila);
            resultado = decode(barcodeString);
            if (resultado == null) {
                StringBuilder barcodeReverse = new StringBuilder(barcodeString);
                resultado = decode(String.valueOf(barcodeReverse.reverse()));
            }
            System.out.println("Contador: "+contador);
            contador+=30;
            if (contador >= anchoNum){
                break;
            }
        }
        saltoFila = 0;
        while (resultado == null) {
            saltoFila += 30;
            if (resultado == null) {
                String verticalBarcodeString = verticalReading(altoNum, anchoNum, barcode3, barcode, saltoFila);
                resultado = decode(String.valueOf(verticalBarcodeString));
            }

            if (resultado == null) {
                String verticalBarcodeString = verticalReading(altoNum, anchoNum, barcode3, barcode, saltoFila);
                StringBuilder verticalReverse = new StringBuilder(verticalBarcodeString);
                resultado = decode(String.valueOf(verticalReverse.reverse()));
            }
        }

        saltoFila = 30;


        return resultado;
    }

    private static String verticalReading(int altoNum, int anchoNum, String[][] barCode3, String[][] barCode, int saltoFila) {
        String code = "";
        imageToBarCode(altoNum, anchoNum, barCode3, barCode);
//        for (int i = 0; i < anchoNum; i++) {
//            for (int j = 0; j < altoNum; j++) {
//                System.out.print(barCode[j][i]);
//            }
//            System.out.println();
//        }
        for (int k = saltoFila; k < anchoNum; k++) {
            for (int l = 0; l < altoNum; l++) {
                String c = barCode[l][k];
                if (c == null) {
                    continue;
                }
                code += c;
            }
            break;
        }
        return code;
    }

    private static String fromNumbertoBarCode(String[][] barCode, int anchoNum, int altoNum, String[][] barCode3, int saltoFila) {
        imageToBarCode(altoNum, anchoNum, barCode3, barCode);
        String code = "";
        for (int k = saltoFila; k < altoNum; k++) {
            for (int l = 0; l < anchoNum; l++) {
                String c = barCode[k][l];
                if (c == null) {
                    continue;
                }
                code += c;
            }
            break;
        }
        System.out.println(code);

        return code;

    }

    private static String[][] imageToBarCode(int altoNum, int anchoNum, String[][] barCode3, String[][] barCode) {
        int[][] barCodeInt = new int[altoNum][anchoNum];
        int index = 0;
        for (int i = 0; i < altoNum; i++) {
            for (int j = 0; j < anchoNum; j++) {
                int r = Integer.parseInt(barCode3[index][0]);
                int g = Integer.parseInt(barCode3[index][1]);
                int b = Integer.parseInt(barCode3[index][2]);
                int RGB = (r + g + b) / 3;
                barCodeInt[i][j] = RGB;
                index++;

                int numero = barCodeInt[i][j];

                if (numero >= 100) {
                    barCode[i][j] = " ";
                } else {
                    barCode[i][j] = "█";
                }
            }
        }
        return barCode;
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

    private static String calculoResultado(String s, String resultadoAux, String sAux, String resultado, HashMap<String, String> map) {
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
        int valorMinimo = minValue(lista);
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

            if (resultado.length() > 0) {
                if ((resultado.charAt(0) == '*' && resultado.charAt(resultado.length() - 1) == '*' && !resultado.contains("null"))) {
                    False = false;
                } else {
                    System.out.println(resultado);
                    System.out.println("resultado-aux.lenght " + resultadoAux.length());
                    resultado = "";
                    valorMaximo--;
                }
            }
        }
        return resultado;
    }

    private static int minValue(List arryValores) {
        int cActual = 0;
        int c = 0;
        for (int i = 0; i < arryValores.toArray().length; i++) {
            c = (Integer) arryValores.get(i);
            if (c < cActual) {
                cActual = c;
            }
        }
        System.out.println("cActual " + cActual);
        return cActual;
    }

}