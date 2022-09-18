import java.util.LinkedList;
import java.util.*;
import java.lang.Math;

public class Options {
    private String name;
    private String value;
    private int length;
    private String all;
    private String padding;
    private LinkedList<Byte> lB;


    public Options(Trace t){
        this.name = "";
        this.value = "";
        this.length = -1;
        this.all = "";
        this.padding = "Padding : ";
        this.lB = new LinkedList<>(t.getListByte().subList(282, (t.getListByte().size() - 1)));
    }

    public void selectOptions(String op){
        int c = Byte.toDecimal(op);
        switch (c) {
            case 0 :
                this.name = "Pad Option";
                this.length = 1;
                this.value = "00";
                break;
            case 255 :
                this.name = "End Option";
                this.length = 1;
                break;
            case 1 :
                this.name = "Subnet Mask";
                this.length = 4;
                break;
            case 2 :
                this.name = "Time Offset";
                this.length = 4;
                break;
            case 3 :
                this.name = "Router Option";
                this.length = -1;
                break;
            case 4 :
                this.name = "Time Server Option";
                this.length = -1;
                break;
            case 5 :
                this.name = "Name Server Option";
                this.length = -1;
                break;
            case 6 :
                this.name = "Domain Name Server Option";
                this.length = -1;
                break;
            case 7 :
                this.name = "Log Server Option";
                this.length = -1;
                break;
            case 8 :
                this.name = "Log Server Option";
                this.length = -1;
                break;
            case 9 :
                this.name = "LPR Server Option";
                this.length = -1;
                break;
            case 10 :
                this.name = "Impress Server Option";
                this.length = -1;
                break;
            case 11 :
                this.name = "Resource Location Server Option";
                this.length = -1;
                break;
            case 12 :
                this.name = "Host Name Option";
                this.length = -1;
                break;
            case 13 :
                this.name = "Boot File Size Option";
                this.length = 2;
                break;
            case 14 :
                this.name = "Merit Dump File";
                this.length = -1;
                break;
            case 15 :
                this.name = "Domain Name";
                this.length = -1;
                break;
            case 16 :
                this.name = "Swap Server";
                this.length = -1;
                break;
            case 17 :
                this. name = "Root Path";
                this.length = -1;
                break;
            case 18 :
                this.name = "Extensions Path";
                this.length = -1;
                break;
            case 19 :
                this.name = "IP Forwarding Enable/Disable Option";
                this.length = 1;
                break;
            case 20 :
                this.name = "Non-Local Source Routing Enable/Disable Option";
                this.length = 1;
                break;
            case 21 :
                this.name = "Policy Filter Option (Adresse/Mask)";
                this.length = -1;
                break;
            case 22 :
                this.name = "Maximum Datagram reassembly Size";
                this.length = 2;
                break;
            case 23 :
                this.name = "Default IP Time-to-live";
                this.length = 1;
                break;
            case 24 :
                this.name = "Path MTU Aging Timeout Option";
                this.length =4;
                break;
            case 25 :
                this.name = "Path MTU Plateau Table Option";
                this.length = -1;
                break;
            case 26 :
                this.name = "Interface";
                this.length = 2;
                break;
            case 27 :
                this.name = "All subnets are Local Option";
                this.length = 1;
                break;
            case 28 :
                this.name = "Broadcast Address Option";
                this.length = 4;
                break;
            case 29 :
                this.name = "Perform Mask Discovery Option";
                this.length = 1;
                break;
            case 30 :
                this.name = "Mask Supplier Option";
                this.length = 1;
                break;
            case 31 :
                this.name = "Perform Router Discovery Option";
                this.length = 1;
                break;
            case 32 :
                this.name = "Router Solicitation Address Option";
                this.length = 4;
                break;
            case 33 :
                this.name = "Static Route Option";
                this.length = -1;
                break;
            case 34 :
                this.name = "Trailer Encapsulation Option";
                this.length = 1;
                break;
            case 35 :
                this.name = "ARP Cache Timeout Option";
                this.length = 4;
                break;
            case 36 :
                this.name = "Ethernet Encapsulation Option";
                this.length = 1;
                break;
            case 37 :
                this.name = "TCP Default TTL Option";
                this.length = 1;
                break;
            case 38 :
                this.name = "TCP Default TTL Option";
                this.length = 1;
                break;
            case 40 :
                this.name = "Network Information Service Domain Option";
                this.length = -1;
                break;
            case 41 :
                this.name = "Network Information Servers Option";
                this.length = -1;
                break;
            case 42 :
                this.name = "Network Time Protocol Servers Option";
                this.length = -1;
                break;
            case 43 :
                this.name = "Vendor Specific Information";
                this.length = -1;
                break;
            case 48 :
                this.name = "X Window System Font Server Option";
                this.length = -1;
                break;
            case 49 :
                this.name = "Network Information Service+ Domain Option";
                this.length = -1;
                break;
            case 64 :
                this.name = "Network Information Service+ Domain Option";
                this.length = -1;
                break;
            case 65 :
                this.name = "Network Information Service+ Servers Option";
                this.length = -1;
                break;
            case 68 :
                this.name = "Mobile IP Home Agent option";
                this.length = -1;
                break;
            case 69 :
                this.name = "Simple Mail Transport Protocol (SMTP) Server Option";
                this.length = -1;
                break;
            case 70 :
                this.name = "Post Office Protocol (POP3) Server Option";
                this.length = -1;
                break;
            case 71 :
                this.name = "Network News Transport Protocol (NNTP) Server Option";
                this.length = -1;
                break;
            case 72 :
                this.name = "Default World Wide Web (WWW) Server Option";
                this.length = -1;
                break;
            case 73 :
                this.name = "Default Finger Server Option";
                this.length = -1;
                break;
            case 74 :
                this.name = "Default Internet Relay Chat (IRC) Server Option";
                this.length = -1;
                break;
            case 75 :
                this.name = "StreetTalk Server Option";
                this.length = -1;
                break;
            case 76 :
                this.name = "StreetTalk Directory Assistance (STDA) Server Option";
                this.length = -1;
                break;
            case 50 :
                this.name = "Requested IP Address";
                this.length = 4;
                break;
            case 51 :
                this.name = "IP Address Lease Time";
                this.length = 4;
                break;
            case 52 :
                this.name = "Option Overload";
                this.length = 1;
                break;
            case 66 :
                this.name = "TFTP server name";
                this.length = -1;
                break;
            case 67 :
                this.name = "Bootfile name";
                this.length = -1;
                break;
            case 53 :
                this.name = "DHCP Message Type";
                this.length = 1;
                break;
            case 54 :
                this.name = "Server Identifier";
                this.length = 4;
                break;
            case 55 :
                this.name = "Parameter Request List";
                this.length = -1;
                break;
            case 56 :
                this.name = "Message";
                this.length = -1;
                break;
            case 57 :
                this.name = "Maximum DHCP Message Size";
                this.length = 2;
                break;
            case 58 :
                this.name = "Renewal (T1) Time Value";
                this.length= 4;
                break;
            case 59 :
                this.name = "Rebinding (T2) Time Value";
                this.length = 4;
                break;
            case 60 :
                this.name = "Vendor class identifier";
                this.length = -1;
                break;
            case 61 :
                this.name = "Client-Identifier";
                this.length = -1;
                break;
            default :
                this.name = "OPTION not managed by the program\n";
                break;
        }
    }

    public void TypeMessage(String op){
        int c = Byte.toDecimal(op);
        switch(c){
            case 1 :
                this.name += " (DHCP DISCOVER)";
                this.value = "1";
                break;
            case 2 :
                this.name += " (DHCP OFFER)";
                this.value = "2";
                break;
            case 3 :
                this.name += " (DHCP REQUEST)";
                this.value = "3";
                break;
            case 4 :
                this.name += " (DHCP DECLINE)";
                this.value = "4";
                break;
            case 5 :
                this.name += " (DHCP ACK)";
                this.value = "5";
                break;
            case 6 :
                this.name += " (DHCP NAK)";
                this.value = "6";
                break;
            case 7 :
                this.name += " (DHCP RELEASE)";
                this.value = "7";
                break;
            case 8 :
                this.name += " (DHCP INFORM)";
                this.value = "8";
                break;
            default :

        }
    }

    public void readOptions(){
        int IPO = IP.getOptionEx();
        if(IPO > 20){
            this.lB = new LinkedList<>(lB.subList((262 + IPO) , lB.size()));
        }
        for(int i = 0 ; i < lB.size() ; i++) {
            Byte b = this.lB.get(i);
            String c = b.getValues();
            int v = Byte.toDecimal(c);
            this.value = "";
            this.selectOptions(c);

            if(this.length == -1) {// Attribute numeric length to options with n length
                this.length = Byte.toDecimal((this.lB.get(i + 1)).getValues());
            }

            if (v == 53){
                String y = (this.lB.get(i+2)).getValues();
                this.TypeMessage(y);
                this.all += "    OPTION (" + v + ") : " + this.name
                        + "\n        length : 1 "
                        + "\n        value : " + this.value + "\n";
                i = i + 2;
                continue;
            }

            if(v == 1 || v == 21 || v == 54){
                for(int j = i + 2 ; j < i + 2 +this.length ; j++){
                    int h = 0;
                    h++;
                    Byte t = this.lB.get(j);
                    String c2 = String.valueOf(Byte.toDecimal(b.getValues()));
                    this.value += c2;
                    if(j < i + 2 + this.length - 1){
                        this.value += ".";
                    }
                    if(h % 4 == 0){
                        this.value += "|";
                    }
                }
                this.all += "    OPTION (" + v + ") : " + this.name
                        + "\n        length : " + this.length
                        + "\n        value : " + this.value + "\n";
                i = i + this.length + 1;
                continue;
            }

            if(v == 22){
                Byte bn1 = this.lB.get(i+1);
                String vn1 = bn1.getValues();
                this.value = String.valueOf(Byte.toDecimal(vn1));
                Byte bn2 = this.lB.get(i+2);
                String vn2 = bn2.getValues();
                this.value = String.valueOf(Byte.toDecimal(vn2));
                i = i + 2; //increase I to pass to the next option
                this.all += "    OPTION (" + v + ") : " + this.name
                        + "\n        length : " + this.length
                        + "\n        value : " + this.value + "\n\n";
                i = i + this.length + 1;
                continue;
            }

            if(v == 255){
                this.all += "    OPTION (" + v + ") : " + this.name
                        + "\n";
                continue;
            }

            if(v == 0){
                this.padding += this.value;
                continue;
            }

            for(int j = i + 2 ; j < i + 2 + this.length && j < lB.size() ; j++){
                Byte t = this.lB.get(j);
                String c2 = t.getValues();
                this.value += c2;
            }
            this.all += "    OPTION (" + v + ") : " + this.name
                    + "\n        length : " + this.length
                    + "\n        value : 0x" + this.value + "\n";
            i = i + this.length + 1;
        }
    }

    public String toString(){
        String s = this.all
                + "    " + this.padding + "\n";
        return s;
    }








}

