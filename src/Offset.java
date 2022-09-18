import java.util.*;

public class Offset {
    private ArrayList<Character> hexaValue;//list of char of the offset, from most significant bit to least
    public Offset() {
        this.hexaValue = new ArrayList<Character>();
    }

    public void addHexaValue(char value) {
        if(Byte.isHexa(value)) {
            this.hexaValue.add(value);
        } else {
            System.out.println("Error : adding a non hexa value to Offset : " + value);
        }
    }

    public long getDecimalValue(){
        return Byte.toDecimal(this.toString());
    }

    public String toString(){
        String s = "";
        for(int i = 0 ; i < hexaValue.size() ; i++ ){
            s += this.hexaValue.get(i);
        }
        return s;
    }
}
