import java.util.LinkedList;
import java.util.*;
import java.lang.Math;

public class UDP {
    private String SourcePort;
    private String DestinationPort;
    private String Length;
    private int LengthValue;
    private String Checksum;
    private LinkedList<Byte> lB;

    public UDP(Trace t){
        SourcePort = "";
        DestinationPort = "";
        Length = "";
        LengthValue = 0;
        Checksum = "";
        lB = t.getListByte();
    }

    public void readSourcePort(){
        Byte b1 = this.lB.get(34);
        int v1 = Character.getNumericValue(b1.getValue1());
        int v2 = Character.getNumericValue(b1.getValue2());
        Byte b2= this.lB.get(35);
        int v3 = Character.getNumericValue(b2.getValue1());
        int v4 = Character.getNumericValue(b2.getValue2());
        this.SourcePort = String.valueOf((int)(v1*(Math.pow(16,3)) + v2*(Math.pow(16,2)) + v3*16 + v4));
    }

    public void readDestinationPort(){
        Byte b1 = this.lB.get(36);
        int v1 = Character.getNumericValue(b1.getValue1());
        int v2 = Character.getNumericValue(b1.getValue2());
        Byte b2= this.lB.get(37);
        int v3 = Character.getNumericValue(b2.getValue1());
        int v4 = Character.getNumericValue(b2.getValue2());
        this.DestinationPort = String.valueOf((int)(v1*(Math.pow(16,3)) + v2*(Math.pow(16,2)) + v3*16 + v4));
    }

    public void readLength(){
        for(int i = 38 ; i < 40 ; i++) {
            Byte b = this.lB.get(i);
            this.Length += b.getValues();
            if(i == 38){
                int v1 = Character.getNumericValue(b.getValue1());//conversion of String to int
                int v2 = Character.getNumericValue(b.getValue2());//conversion of String to int
                this.LengthValue +=  v1*(Math.pow(16,3)) + v2*(Math.pow(16,2));// add to length the decimal value of 36th byte
            }
            if(i == 39){
                int v1 = Character.getNumericValue(b.getValue1());
                int v2 = Character.getNumericValue(b.getValue2());
                this.LengthValue +=  v1*16+v2; // add to length the decimal value of 37th byte
            }
        }
    }

    public void readChecksum(){
        for(int i = 40 ; i < 42 ; i++) {
            Byte b = this.lB.get(i);
            this.Checksum += b.getValues();
        }
    }

    public void readUDP(){
        int IPO = IP.getOptionEx();
        if(IPO > 20){
            LinkedList<Byte> lB1 = new LinkedList<>(lB.subList(0 , 13));
            LinkedList<Byte> lB2 = new LinkedList<>(lB.subList((14 + IPO) , lB.size()));
            lB1.addAll(lB2);
            this.lB = lB1;
        }
        readSourcePort();
        readDestinationPort();
        readLength();
        readChecksum();
    }

    public String getSourcePort(){
        return SourcePort;
    }

    public String getDestinationPort(){
        return DestinationPort;
    }

    public String toString(){
        String s = "\nUser Datagram Protocol, Src Port : " + this.SourcePort + ", Dst Port : " + this.DestinationPort + "\n";
        s += "    Source Port : " + this.SourcePort
                + "\n    Destination Port : " + this.DestinationPort
                + "\n    Length: 0x" + this.Length + " (" + this.LengthValue + " Bytes)"
                + "\n    Checksum : Ox" + this.Checksum;
        return s;
    }
}


