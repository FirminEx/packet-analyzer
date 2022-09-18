public class Byte {
    private char value1;
    private char value2;
    int position;

    public Byte(char pValue1, char pValue2, int position) {
        if(Byte.isHexa(pValue1) && Byte.isHexa(pValue2)){
            this.value1 = pValue1;
            this.value2 = pValue2;
            this.position = position;
        }else {
            System.out.println("Error : creating an invalid Byte with values " + pValue1 + " " +pValue2);
        }
    }

    public static boolean isHexa(char value) {
        return value >= '0' && value <= '9' || value >= 'a' && value <= 'f' || value >= 'A' && value <= 'F';
    }

    public static int toDecimal(char value){
        if(value >= '0' && value <= '9') {
            return ((int) value) - 48;
        }else if( value >= 'a' && value <= 'f' ) {
            return ((int) value) - 87;
        }else if( value >= 'A' && value <= 'F') {
            return ((int) value) - 55;
        }
        System.out.println("Error can't convert to decimal, this is not a Hexa value");
        return -1;
    }

    public static String toBit(String value) {
        String bitString = value;
        bitString = bitString.replaceAll("0", "0000");
        bitString = bitString.replaceAll("1", "0001");
        bitString = bitString.replaceAll("2", "0010");
        bitString = bitString.replaceAll("3", "0011");
        bitString = bitString.replaceAll("4", "0100");
        bitString = bitString.replaceAll("5", "0101");
        bitString = bitString.replaceAll("6", "0110");
        bitString = bitString.replaceAll("7", "0111");
        bitString = bitString.replaceAll("8", "1000");
        bitString = bitString.replaceAll("9", "1001");
        bitString = bitString.replaceAll("A", "1010");
        bitString = bitString.replaceAll("B", "1011");
        bitString = bitString.replaceAll("C", "1100");
        bitString = bitString.replaceAll("D", "1101");
        bitString = bitString.replaceAll("E", "1110");
        bitString = bitString.replaceAll("F", "1111");
        bitString = bitString.replaceAll("a", "1010");
        bitString = bitString.replaceAll("b", "1011");
        bitString = bitString.replaceAll("c", "1100");
        bitString = bitString.replaceAll("d", "1101");
        bitString = bitString.replaceAll("e", "1110");
        bitString = bitString.replaceAll("f", "1111");
        return bitString;
    }

    public static int toDecimal(String value){
        /*int decimalValue = 0;
        for(int i = 0 ; i < value.length() ; i++) {
            decimalValue += Math.pow(16 , value.length() - 1 - i) * Byte.toDecimal(value.charAt(i));
        }
        return decimalValue;*/
        //System.out.println("VALUE: " + value);
        return Integer.parseInt(value , 16);
    }


    public static int bitToDecimal(String bitString) {
        return Integer.parseInt(bitString , 2);
    }

    public static String toAscii(String value) { //return the string value of value (from hexa)v gyoà0=1
        if( value.length() % 2 != 0) {
            System.out.println("Error value is not an hexa string ");
            return "";
        }
        String ascii = "";
        for(int i = 0 ; i < value.length() ; i+=2) {
            //System.out.println(value.charAt(i) + "" + value.charAt(i+1));
            //System.out.println(Byte.toDecimal(value.charAt(i) + "" + value.charAt(i+1)));
            ascii += (char) (Byte.toDecimal(value.charAt(i) + "" + value.charAt(i+1))); //because a
        }
        return ascii;
        //TODO fix this function
    }

    public String toString(){
        return this.value1 + "" + this.value2 + " " +this.position;
    }

    public char getValue1(){
        return this.value1;
    }

    public char getValue2(){
        return this.value2;
    }

    public String getValues(){
        return  this.value1 +""+ this.value2;
    }

}
