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
        String resultado = "";
        String stringBin = "";
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
            System.out.println(resultado);
        }
        resultado = resultado.substring(0,resultado.length()-1);
        return resultado;
    }

    static String decode(String s) {
        char c = ' ';
        int contador = 0;
        int mayor = 0;
        int menor  = 0;
        for (int i = 0; i < s.length(); ++i) {
            char cAntigua = c;
            c = s.charAt(i);

            while(c == cAntigua){
                contador++;
            }
            mayor = contador;
            if (c!= cAntigua){
                contador  = 0 ;
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
