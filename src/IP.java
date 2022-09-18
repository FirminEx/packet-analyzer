import java.util.LinkedList;
import java.util.*;

public class IP {
    private String Version;
    private String IHL;
    private String TOS;
    private String Total_Length;
    private String Identifier;
    private String Flags;
    private String Fragment_offset;
    private String TTL;
    private String Protocol;
    private String ProtocolName;
    private String Checksum;
    private String SourceAddress;
    private String DestinationAddress;
    private static int OptionEx;
    private String Options;
    private String OptionName;
    private int OpLength;
    private LinkedList<Byte> lB;
    private int DNSstart;


    public IP(Trace t){
        this.Version = "";
        this.IHL = "";
        this.TOS = "";
        this.Total_Length = "";
        this.Identifier = "";
        this.Flags = "";
        this.Fragment_offset = "";
        this.TTL = "";
        this.Protocol = "";
        this.ProtocolName = "";
        this.Checksum = "";
        this.SourceAddress = "";
        this.DestinationAddress = "";

        this.OptionEx = 0;
        this.Options = "";
        this.OptionName = "";
        this.OpLength = 0;
        this.lB = t.getListByte();
        this.DNSstart = 0;
    }

    public void readVersion(){
        Byte b = this.lB.get(14);
        this.Version += b.getValue1();
    }

    public void readIHL(){
        Byte b = this.lB.get(14);
        this.IHL += String.valueOf(4*(Byte.toDecimal(b.getValue2())));
        this.IHL += " bytes";
        this.OptionEx = 4*(Byte.toDecimal(b.getValue2()));
        this.IHL += " (" + b.getValue2() + ")";
    }

    public void selectOptions(String op){
        int c = Byte.toDecimal(op);
        switch(c){
            case 0 :
                this.OptionName += " End Of Options List";
                this.OpLength = -1;
                break;
            case 1 :
                this.OptionName += " No Operation";
                this.OpLength = -1;
                break;
            case 7 :
                this.OptionName += " Record Route";
                this.OpLength = -1;
                break;
            case 68 :
                this.OptionName += " Time Stamp";
                this.OpLength = -1;
                break;
            case 131 :
                this.OptionName += " Loose Routing";
                this.OpLength = -1;
                break;
            case 137 :
                this.OptionName += " Strict Routing";
                this.OpLength = -1;
                break;
            default :

        }
    }

    public void ifIHLn20(){
        Byte b = lB.get(14);
        int q = 4*(Byte.toDecimal(b.getValue2()));
        this.DNSstart = q + 22;
        if(q > 20){
            this.OptionEx = 1;
            LinkedList<Byte> lB1 = new LinkedList<>(lB.subList(0 , 33));
            LinkedList<Byte> lB2 = new LinkedList<>(lB.subList((34 + (q - 20)) , lB.size()));
            lB1.addAll(lB2);
            this.lB =lB1;
            LinkedList<Byte> newLB = new LinkedList<>(lB.subList(34, (34 + (q - 20))));
            for(int i = 0 ; i < newLB.size() ; i++) {
                Byte d = newLB.get(i);
                String c = d.getValues();
                int v = Byte.toDecimal(c);
                this.selectOptions(c);
                if(this.OpLength == -1) {// Attribute numeric length to options with n length
                    this.OpLength = Byte.toDecimal((this.lB.get(i + 1)).getValues());
                }
                this.Options += "           OPTION (" + v + ") : " + this.OptionName
                        + "\n           length : " + this.OpLength + "\n";
                i = i + this.OpLength + 1;
            }
        }
    }

    public void readTOS(){
        Byte b = this.lB.get(15);
        this.TOS += b.getValues();
    }

    public void readTotal_Length(){
            Byte b1 = this.lB.get(16);
            int v1 = Character.getNumericValue(b1.getValue1());
            int v2 = Character.getNumericValue(b1.getValue2());
            Byte b2= this.lB.get(17);
            int v3 = Character.getNumericValue(b2.getValue1());
            int v4 = Character.getNumericValue(b2.getValue2());
            this.Total_Length = String.valueOf((int)(v1*(Math.pow(16,3)) + v2*(Math.pow(16,2)) + v3*16 + v4));
    }

    public void readIdentifier(){
        for(int i = 18 ; i < 20 ; i++) {
            Byte b = this.lB.get(i);
            this.Identifier += b.getValues();
        }
    }

    public void readFlags( ){
        Byte b = this.lB.get(20);
        this.Flags += b.getValue1();//read the 4 first bits of the 20th byte
    }

    public void readFragmentOffset(){
        Byte b1 = this.lB.get(20);
        int v1 = Character.getNumericValue(b1.getValue2());
        int res = (int)(v1*(Math.pow(16,2)));
        Byte b2 = this.lB.get(21);
        int v2 = Character.getNumericValue(b2.getValue1());
        int v3 = Character.getNumericValue(b2.getValue2());
        res = res + v2*16 + v3;
        this.Fragment_offset = String.valueOf(res);
    }

    public void readTTL(){
        Byte b = this.lB.get(22);
        int v1 = Character.getNumericValue(b.getValue1());
        int v2 = Character.getNumericValue(b.getValue2());
        int res = v1*16 + v2;
        this.TTL += String.valueOf(res);
    }

    public void readProtocol(){
        Byte b = this.lB.get(23);
        int v1 = Character.getNumericValue(b.getValue1());
        int v2 = Character.getNumericValue(b.getValue2());
        int res = v1*16 + v2;
        this.Protocol = String.valueOf(res);
    }

    public void selectProtocol(){//Select protocol according to its value
        this.ProtocolName = "Protocol not managed by the program";
        if(this.Protocol.equals("01")){
            this.ProtocolName = "ICMP";
        }
        if(this.Protocol.equals("02")){
            this.ProtocolName = "IGMP";
        }
        if(this.Protocol.equals("06")){
            this.ProtocolName = "TCP";
        }
        if(this.Protocol.equals("17")){
            this.ProtocolName = "UDP";
        }
    }

    public void readChecksum(){
        for(int i = 24 ; i < 26 ; i++) {
            Byte b = lB.get(i);
            this.Checksum += b.getValues();
        }
    }


    public void readSourceAddress(){
        for(int i = 26 ; i < 30 ; i++) {
            Byte b = this.lB.get(i);
            //conversion of address to decimal
            int v1 = Character.getNumericValue(b.getValue1());//char-> int
            int v2 = Character.getNumericValue(b.getValue2());//char-> int
            this.SourceAddress +=  String.valueOf(v1*16+v2);
            if(i<29){
                this.SourceAddress +=".";
            }
        }
    }

    public void readDestinationAddress(){
        for(int i = 30 ; i < 34 ; i++) {
            Byte b = this.lB.get(i);
            //conversion of address to decimal
            int v1 = Character.getNumericValue(b.getValue1());//char-> int
            int v2 = Character.getNumericValue(b.getValue2());//char-> int
            this.DestinationAddress += String.valueOf(v1*16+v2);
            if(i < 33){
                this.DestinationAddress +=".";
            }
        }
    }

    public void readIP(){
        readVersion();
        readIHL();
        ifIHLn20();
        readTOS();
        readTotal_Length();
        readIdentifier();
        readFlags();
        readFragmentOffset();
        readTTL();
        readProtocol();
        selectProtocol();
        readChecksum();
        readSourceAddress();
        readDestinationAddress();
    }

    public int getDNSstart(){
        return this.DNSstart;
    }

    public String getProtocolName(){
        return this.ProtocolName;
    }

    public static int getOptionEx(){
        return OptionEx;
    }

    public static void setOptionEx(int v){
        OptionEx = v;
    }

    public String toString(){
        String s = "\nInternet Protocol\n";
        s += "    Version : " + this.Version
                + "\n    IP Header Length : " + this.IHL
                + "\n    Tos : 0x" + this.TOS
                + "\n    Total Length : "+ this.Total_Length
                + "\n    Identifier : 0x" + this.Identifier
                + "\n    Flags : 0x" + this.Flags
                + "\n    Fragment offset : " + this.Fragment_offset
                + "\n    Time to Live : " + this.TTL
                + "\n    Protocol : " + this.ProtocolName + " (" + this.Protocol + ")"
                + "\n    Checksum : Ox" + this.Checksum
                + "\n    Source Address : " + this.SourceAddress
                + "\n    Destination Address : " + this.DestinationAddress;
        return s;
    }
}

