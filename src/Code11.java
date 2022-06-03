import java.util.*;

public class Code11 {

    static String encode(String s) {
        String result = "";

        //Generamos un Mapa que contendra como claves los caracteres a codificar y
        //su valor sera el codigo de barras perteneciente.
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

        //Vamos cogiendo caracter a caracter su valor en el mapa
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            result = result + map.get(c);
        }

        //Quitamos el ultimo espacio
        result = result.substring(0, result.length() - 1);

        return result;
    }

    static String decode(String s) {
        String result = "";

        //A la hora de decodificar utilizaremos el valor estandarizado
        HashMap<String, String> decodingMap = new HashMap();
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

        //Eliminamos los espacios del principio y final
        s = s.trim();

        //Vamos a reotornar el string proporcionado por el metodo "calculateResult"
        return calculateResult(s, result, decodingMap);
    }

    public static String decodeImage(String str) {
        String img = str;
        String result = "";
        int position = 0;
        int numericWidth = 0;
        int numericHeight = 0;

        //Si estamos en Windows aparece el caracter \r que sirve para volver al inico de linea
        //despues de un intro como no nos interesa lo eliminamos del String
        img = img.replace("\r", "");

        //Introducimos el un Array de String unidimensional
        //los String generados por el split de la imagen
        String[] stringImage = img.split("\n");

        String widthHeight;

        //Dentro del Array stringImage buscaremos el caracter # para comprobar si
        //este contiene un comentario y saber asi en que posicion esta el alto y ancho de la imagen
        if (!stringImage[1].contains("#")) {
            widthHeight = stringImage[1];
            position = 3;
        } else {
            widthHeight = stringImage[2];
            position = 4;
        }

        //Separaremsos la posicion con un split por espacios
        String widthHeightArray[] = widthHeight.split("\s");

        //Adjudicamos el valor del ancho y alto a una variable
        numericWidth = Integer.parseInt(widthHeightArray[0]);
        numericHeight = Integer.parseInt(widthHeightArray[1]);

        //A continuacion creamos un array que contendra paquetes de tres para saber cada pixel --> [255,255,255]
        String[][] threePackBarcode = new String[numericWidth * numericHeight][3];

        //Cogemos los valores del array de la imagen de 3 en 3 y lo metemos en el array bidimensional threePackBarcode
        for (int i = 0; i < numericWidth * numericHeight; i++) {
            for (int j = 0; j < 3; j++) {
                threePackBarcode[i][j] = stringImage[position];
                position++;
            }
        }

        //Creamos un segundo array bidimensional, este sera el que guarde la consecucion de caracteres
        //barra y espacios
        String[][] doubleArrayBarcode = new String[numericHeight][numericWidth];

        int rowJump = 0;

        //Vamos a probar la decodificacion de la imagen de 3 maneras diferentes:
        //1. Leemos la primera linea y la decodificamos con el metodo decode
        result = decode(horizontalReading(doubleArrayBarcode, numericWidth, numericHeight, threePackBarcode, rowJump));

        int counter = 0;

        //2. Si leida la primera linea el resultado continua siendo null
        //Leeremos linea a linea hasta el final para encontrar un string valido
        result = horizontalTest(result, numericWidth, numericHeight, threePackBarcode, doubleArrayBarcode, rowJump, counter);

        rowJump = 0;

        //3. Si leyendo en horizontal resultado sigue siendo null probaremos de forma vertical
        result = verticalTest(result, numericWidth, numericHeight, threePackBarcode, doubleArrayBarcode, rowJump);

        return result;
    }

    private static String verticalTest(String result, int numericWidth, int numericHeight, String[][] threePackBarcode, String[][] doubleArrayBarcode, int rowJump) {
        //Mientras resultado sea null
        while (result == null) {

            //haremos saltos de 30 para leer la imagen mas rapido
            rowJump += 30;

            //haremos primero una comprobacion vertical del string normal
            if (result == null) {
                String verticalBarcodeString = verticalReading(numericHeight, numericWidth, threePackBarcode, doubleArrayBarcode, rowJump);
                result = decode(String.valueOf(verticalBarcodeString));
            }
            //Si falla haremos la comprobacion al la inversa
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
            //haremos saltos de lienea para avanzar mas rapido
            rowJump += 30;

            //Primero decodificaremos el string de frente
            String barcodeString = horizontalReading(doubleArrayBarcode, numericWidth, numericHeight, threePackBarcode, rowJump);
            result = decode(barcodeString);

            //Si el resultado es null probaremos a leerlo a la inversa
            if (result == null) {
                StringBuilder barcodeReverse = new StringBuilder(barcodeString);
                result = decode(String.valueOf(barcodeReverse.reverse()));
            }

            counter += 30;
            //Si el contador llega al alto total de la imgen implica que hemos llegado al final y debe dejar de leer
            if (counter >= numericHeight) {
                break;
            }
        }
        return result;
    }

    private static String verticalReading(int numericHeight, int numericWidth, String[][] threePackBarcode, String[][] doubleArrayBarcode, int rowJump) {

        String code = "";

        //Genera un Array bidimensional
        imageToBarCode(numericHeight, numericWidth, threePackBarcode, doubleArrayBarcode);

        //Leemos el array devuelto por el metodo imageToBarCode de forma verical y retornamos el String generado
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

    private static String horizontalReading(String[][] doubleArrayBarcode, int numericWidth, int numericHeight, String[][] threePackBarcode, int rowJump) {

        imageToBarCode(numericHeight, numericWidth, threePackBarcode, doubleArrayBarcode);
        String code = "";

        //Leemos el Array generado de forma horizontal y retornamos el String Generado
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

        //Generamos un array bidimensional numerico que contendra los valores de la media de RGB
        int[][] numericBarcode = new int[numericHeight][numericWidth];
        int index = 0;


        for (int i = 0; i < numericHeight; i++) {
            for (int j = 0; j < numericWidth; j++) {
                //Cogemos los valores de RGB del array threePackBarcode generado
                //sumamos los valores y dividimos entre 3 para sacar la media
                int r = Integer.parseInt(threePackBarcode[index][0]);
                int g = Integer.parseInt(threePackBarcode[index][1]);
                int b = Integer.parseInt(threePackBarcode[index][2]);
                int RGB = (r + g + b) / 3;
                numericBarcode[i][j] = RGB;
                index++;

                int number = numericBarcode[i][j];

                //Si la media es mayor o igual a 100 pondremos un espacio sino una barra
                //en el array bidimensional que retornaremos
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
        //Codificamos el String de la imagen
        String stringEncoded = Code11.encode(s);

        //La imagen siempre mide 108px de alto
        int imageHeight = 108;
        String barCode = "";
        String auxiliarResult = "";
        int imageWidth = 0;
        int barCounter = 0;
        int spaceCounter = 0;
        char c = ' ';

        //Cada pixel en el margen superior o inferior son rgb --> 255 pq son blancos
        String topBottomMargin = "255\n255\n255\n";

        //8px de Margen lateral 
        String lateralMargin = "255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n255\n";

        if (stringEncoded == null) {
            return null;
        }

        for (int j = 0; j <= stringEncoded.length(); j++) {

            if (j < stringEncoded.length()) {
                c = stringEncoded.charAt(j);
            }
            
            //En funcion del Caracter obtenido del String optaremos por una accion o otra
            //Si el caracter es una barra incrementaremos el contador, hasta que se encuentre un espacio
            //en cuyo caso incrementaremos el contador de espacoos y se reseteara a 0 el de barras,
            // En funcion del tamaño del contador introduciremos en el String la consecucion de caracteres que pertoque.
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
        //Añadimos la ultima barra
        auxiliarResult += "0\n0\n0\n0\n0\n0\n0\n0\n0\n";

        //16px de margenes laterales mas 3px de la ultima barra
        imageWidth += 19;

        //Añadimos los margenes laterales al String
        auxiliarResult = lateralMargin + auxiliarResult + lateralMargin;

        for (int i = 0; i < 100; i++) {
            //Generamos el String que contriene el Codigo de Barras
            barCode += auxiliarResult;
        }
        
        String topMargin = "";

        for (int i = 0; i < imageWidth * 4; i++) {
            //Generamos el margen superior
            topMargin += topBottomMargin;
        }

        //Eliminamos el ultimo salto de linea para el margen inferior
        String bottomMargin = topMargin.substring(0, topMargin.length() - 1);

        return "P3\n" + imageWidth + "\s" + imageHeight + "\n255\n" + topMargin + barCode + bottomMargin;
    }

    private static List valueListGenerator(String s) {

        //Creamos una lista
        List<Integer> list = new ArrayList();

        char c = ' ';
        int barCounter = 0;
        int spaceCounter = 0;

        s = s + " ";

        //Leemos todo el String caracter ha caracter
        for (int i = 0; i <= s.length(); i++) {
            if (i < s.length()) {
                c = s.charAt(i);
            } else {
                return list;
            }
            //Vamos incrementando un contador que guardara el numero de
            //veces segidas que encontramos un caracter
            //cuando se encuentre el caracter contrario guardara ese valor y reseteara el contador a 0
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

        //Si el metodo invalidCodes retorna true
        //implica que el String contiene un caracter no valido
        // por tanto retornamos true
        if (invalidCodes(s)) return null;

        //Generamos una lista con los valores numericos de
        //la cantidad de barras y espacios que hay --> [3,4,6,8,9]
        List valueList = valueListGenerator(s);

        //Obtenemos el valor maximo de la lista
        int maxValue = maxValue(valueList);

        //Mientras que el String no sea el esperado o no haya mas posibilidades
        while (False) {
            String auxiliarResult = "";

            if (maxValue <= 1) {
                return null;
            }

            //Vamos cogiendo los numeros de la lista y en funcion del
            //numero mas grande y los mayores a este añadimos un 1 o un 0 al resultadoAuxiliar
            for (int i = 0; i < valueList.toArray().length; i++) {
                Integer c = (Integer) valueList.get(i);
                if (c >= maxValue) {
                    auxiliarResult += 1;
                } else {
                    auxiliarResult += 0;
                }
            }

            //Cada 5 caracteres nos metemos en el Mapa para comprobar si la combinacion
            //de 1 y 0 es valida o no
            for (int i = 0; i <= auxiliarResult.length(); i++) {
                if (auxiliarString.length() == 5) {
                    result = result + decodingMap.get(auxiliarString);
                    auxiliarString = "";
                    continue;
                }
                if (i < auxiliarResult.length()) {
                    char c = auxiliarResult.charAt(i);
                    auxiliarString = auxiliarString + c;
                }
            }

            if (result.length() > 0) {
                //Si hay un * al principi y al final y no es null
                if ((result.charAt(0) == '*' && result.charAt(result.length() - 1) == '*' && !result.contains("null"))) {
                    //Acabamos el bucle
                    False = false;
                } else {
                    //Sino reseteamos y restamos 1 al valor maximo
                    //De esta manera la siguiente vuelta los 1 seran el valor mas grande y el valor mas grande - 1
                    result = "";
                    maxValue--;
                }
            }
        }
        return result;
    }

    private static boolean invalidCodes(String s) {
        //Recoremos todo el String  para comprobar si hay caracteres no validos
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

        //Recorremos toda la lista en busca de numero mas grande
        for (int i = 0; i < valueList.toArray().length; i++) {
            c = (Integer) valueList.get(i);
            if (c > cActual) {
                cActual = c;
            }
        }
        return cActual;
    }
}