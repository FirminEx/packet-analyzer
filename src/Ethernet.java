import java.io.IOException;
import java.util.LinkedList;
import java.util.*;

public class Ethernet {
    private String sourceAddress;
    private String destinationAddress;
    private String Typevalue;
    private String Typename;
    private final LinkedList<Byte> lB;

    public Ethernet(Trace t){
        this.sourceAddress = "";
        this.destinationAddress = "";
        this.Typevalue = "";
        this.Typename = "";
        this.lB = t.getListByte();
    }

    public void readSourceAddress(){
        for(int i = 0 ; i < 6 ; i++) {
            Byte b = this.lB.get(i);
            this.sourceAddress += b.getValues();
            if(i < 5){
                this.sourceAddress += ":";
            }
        }
        if(this.sourceAddress.equals("ff:ff:ff:ff:ff:ff") || this.sourceAddress.equals("FF:FF:FF:FF:FF:FF")){
            this.sourceAddress += " (Broadcast)";
        }
    }

    public void readDestinationAddress(){
        for(int i = 6 ; i < 12 ; i++){
            Byte b = this.lB.get(i);
            this.destinationAddress += b.getValues();
            if(i < 11){
                this.destinationAddress += ":";
            }
        }
        if(this.destinationAddress.equals("ff:ff:ff:ff:ff:ff") || this.destinationAddress.equals("FF:FF:FF:FF:FF:FF")){
            this.destinationAddress += " (Broadcast)";
        }
    }

    public void readType(){
        for(int i = 12 ; i < 14 ; i ++){
            Byte b = this.lB.get(i);
            this.Typevalue += b.getValues();
        }
    }

    public void selectType(){ // Attribute type's name according to its value
        if(this.Typevalue.equals("6000") || this.Typevalue.equals("6009")){
            this.Typename = "DEC";
            return;
        }
        if(this.Typevalue.equals("0600")){
            this.Typename = "XNS";
            return;
        }
        if(this.Typevalue.equals("0800")){
            this.Typename = "IPv4";
            return;
        }
        if(this.Typevalue.equals("0805")){
            this.Typename = "X.25 level 3";
            return;
        }
        if(this.Typevalue.equals("0806")){
            this.Typename = "ARP";
            return;
        }
        if(this.Typevalue.equals("8019")){
            this.Typename = "Domain";
            return;
        }
        if(this.Typevalue.equals("8035")){
            this.Typename = "XNS";
            return;
        }
        if(this.Typevalue.equals("809B") ||this.Typevalue.equals("809b")){
            this.Typename = "AppleTalk";
            return;
        }
        if(this.Typevalue.equals("8100")){
            this.Typename = "802.1Q";
            return;
        }
        if(this.Typevalue.equals("86DD") || this.Typevalue.equals("86dd") || this.Typevalue.equals("86Dd") || this.Typevalue.equals("86dD")){
            this.Typename = "IPv6";
            return;
        }
        else{
            this.Typename = "Type not available";
            return;
        }
    }

    public void readEthernet(){//
        this.readSourceAddress();
        this.readType();
        this.readDestinationAddress();
        this.selectType();
    }

    public String getTypevalue(){
        return this.Typevalue;
    }

    public String toString(){
        String s = "Ethernet II \n";
        s += "    Destination : "
                + "(" + this.sourceAddress + ")" + "\n    Source Address : "
                + "(" + this.destinationAddress + ")"
                + "\n    " + this.Typename + " (0x"
                + this.Typevalue + ")";
        return s;
    }
}
