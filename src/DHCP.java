import java.util.LinkedList;
import java.util.*;
import java.lang.Math;

public class DHCP {
    private String op;//Message Type
    private String hType;//
    private String hTypename;//
    private String hlen;//
    private String hops;//
    private String xid;//
    private String secs;
    private String flags;
    private String ciaddr;
    private String yiaddr;
    private String siaddr;
    private String giaddr;
    private String cmaddr;
    private String chaddr;
    private String sname;
    private String file;
    private String MK;
    private LinkedList<Byte> lB;

    public DHCP(Trace t){
        this.op = "";
        this.hType = "";
        this.hTypename = "unknown Type";
        this.hlen = "" ;
        this.hops = "" ;
        this.xid = "" ;
        this.secs = "" ;
        this.flags = "" ;
        this.ciaddr = "" ;
        this.yiaddr = "" ;
        this.siaddr = "" ;
        this.giaddr = "" ;
        this.cmaddr = "" ;
        this.chaddr = "" ;
        this.sname = "Server hs name not given" ;
        this.file = "Boot file name not given" ;
        this.MK = "";
        this.lB = new LinkedList<>(t.getListByte().subList(42, (t.getListByte().size() - 1)));
    }

    public void readOp(){
        Byte b = this.lB.get(0);
        String t = b.getValues();
        this.op = t;
        if(t.equals("01")){
            this.op += " (Request)";
        }
        if(t.equals("02")){
            this.op += " (Reply)";
        }
    }

    public void readHtype(){
        Byte b = this.lB.get(1);
        this.hType += b.getValues();
    }

    public void selectHtypename(){
            if(this.hType.equals("01")){
                this.hTypename = "Ethernet";
            }
    }

    public void readHlen(){
        Byte b = this.lB.get(2);
        int v1 = Character.getNumericValue(b.getValue1());//char-> int
        int v2 = Character.getNumericValue(b.getValue2());//char-> int
        this.hlen += String.valueOf(v1*16+v2);
    }

    public void readHops(){
        Byte b = this.lB.get(3);
        int v1 = Character.getNumericValue(b.getValue1());//char-> int
        int v2 = Character.getNumericValue(b.getValue2());//char-> int
        this.hops += String.valueOf(v1*16+v2);
    }

    public void readXid(){
        for(int i = 4 ; i < 8 ; i++) {
            Byte b = this.lB.get(i);
            this.xid += b.getValues();
        }
    }

    public void readSecs(){
        for(int i = 8 ; i < 10 ; i++) {
            Byte b = this.lB.get(i);
            if(i == 8){
                int v1 = Character.getNumericValue(b.getValue1());//conversion of String to int
                int v2 = Character.getNumericValue(b.getValue2());//conversion of String to int
                this.secs +=  (int)(v1*(Math.pow(16,3)) + v2*(Math.pow(16,2)));// add to length the decimal value of 36th byte
            }
            if(i == 9){
                int v1 = Character.getNumericValue(b.getValue1());
                int v2 = Character.getNumericValue(b.getValue2());
                this.secs +=  (int)(v1*16+v2); // add to length the decimal value of 37th byte
            }
        }
    }

    public void readFlags(){
        for(int i = 10 ; i < 12 ; i++) {
            Byte b = this.lB.get(i);
            this.flags += b.getValues();
        }
    }

    public void readCiaddr(){
        for(int i = 12 ; i < 16 ; i++) {
            Byte b = this.lB.get(i);
            int v1 = Character.getNumericValue(b.getValue1());//char-> int
            int v2 = Character.getNumericValue(b.getValue2());//char-> int
            this.ciaddr += String.valueOf(v1*16+v2);
            if(i < 15){
                this.ciaddr +=".";
            }
        }
    }

    public void readYiaddr(){
        for(int i = 16 ; i < 20 ; i++) {
            Byte b = this.lB.get(i);
            int v1 = Character.getNumericValue(b.getValue1());//char-> int
            int v2 = Character.getNumericValue(b.getValue2());//char-> int
            this.yiaddr += String.valueOf(v1*16+v2);
            if(i < 19){
                this.yiaddr +=".";
            }
        }
    }

    public void readSiaddr(){
        for(int i = 20 ; i < 24 ; i++) {
            Byte b = this.lB.get(i);
            int v1 = Character.getNumericValue(b.getValue1());//char-> int
            int v2 = Character.getNumericValue(b.getValue2());//char-> int
            this.siaddr += String.valueOf(v1*16+v2);
            if(i < 23){
                this.siaddr +=".";
            }
        }
    }

    public void readGiaddr(){
        for(int i = 24 ; i < 28 ; i++) {
            Byte b = this.lB.get(i);
            int v1 = Character.getNumericValue(b.getValue1());//char-> int
            int v2 = Character.getNumericValue(b.getValue2());//char-> int
            this.giaddr += String.valueOf(v1*16+v2);
            if(i < 27){
                this.giaddr +=".";
            }
        }
    }

    public void readCmaddr(){
        for(int i = 28 ; i < 34 ; i++) {
            Byte b = this.lB.get(i);
            this.cmaddr += b.getValues();
            if(i<33){
                this.cmaddr +=":";
            }
        }
    }

    public void readChaddr(){
        for(int i = 34 ; i < 44 ; i++) {
            Byte b = this.lB.get(i);
            this.chaddr += b.getValues();
        }
    }

    public void readMK(){
        for(int i = 236 ; i < 240 ; i++){
            Byte b = this.lB.get(i);
            int v1 = Character.getNumericValue(b.getValue1());//char-> int
            int v2 = Character.getNumericValue(b.getValue2());//char-> int
            this.MK += String.valueOf(v1*16+v2);
            if(i < 281){
                this.MK += ".";
            }
        }
        this.MK += " DHCP";
    }

    public void readDHCP(){
        int IPO = IP.getOptionEx();
        if(IPO > 20){
            this.lB = new LinkedList<>(lB.subList((22 + IPO) , lB.size()));
        }
        readOp();
        readHtype();
        selectHtypename();
        readHlen();
        readHops();
        readXid();
        readSecs();
        readFlags();
        readCiaddr();
        readYiaddr();
        readSiaddr();
        readGiaddr();
        readCmaddr();
        readChaddr();
        readMK();
    }

    public String toString(){
        String s = "\nBootstrap Protocol \n";
        s += "    Message Type : " + this.op
                + "\n    Hardware type : " + this.hTypename + " (0x" + this.hType + ")"
                + "\n    Hardware address length: " + this.hlen
                + "\n    Hops : " + this.hops
                + "\n    Transaction ID : 0x" + this.xid
                + "\n    seconds elapsed : " + this.secs
                + "\n    Bootp flags : 0x" + this.flags
                + "\n    Client IP address : "+ this.ciaddr
                + "\n    Your (client) IP address : " + this.yiaddr
                + "\n    Next server IP address : " + this.siaddr
                + "\n    Relay agent IP address : " + this.giaddr
                + "\n    Client MAC address : " + this.cmaddr + " (" + this.cmaddr + ")"
                + "\n    Client hardware address padding : " + this.chaddr
                + "\n    "+ this.sname
                + "\n    "+ this.file
                + "\n    Magic Cookie : " + this.MK;
        return s;
    }


}
