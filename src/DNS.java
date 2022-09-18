import java.util.ArrayList;
import java.util.LinkedList;

public class DNS {
    private String id;
    private String flags;
    private String numberQuestions;
    private String numberAnswers;
    private String numberAuthority;
    private String numberAdditional;
    private String queryReply;
    private LinkedList<Byte> byteList;
    private String[] flagsValues; //flags value ordered by appearance
    private int bytePosition; //byte cursor (applied to byteList)
    private LinkedList<String> queriesList;
    private ArrayList<String> answersList;
    private ArrayList<String> authorityList;
    private ArrayList<String> additionalList;
    private LinkedList<Name> nameList;
    private static String[] SOA = {"Serial Number: " , "Refresh Interval: " , "Retry Interval: " , "Expire Limit: " , "Minimum TTL: "};


    public DNS(Trace  t , int start) {
        this.id = "";        this.flags = "";
        this.numberQuestions = "";
        this.numberAnswers = "";
        this.numberAuthority = "";
        this.numberAdditional = "";
        this.queryReply = "";
        this.byteList = new LinkedList<>(t.getListByte().subList(start , (t.getListByte().size())));
        this.flagsValues = new String[8];
        this.bytePosition = 0;
        this.nameList = new LinkedList<>();
    }

    private void readIndex() {
        this.id = this.byteList.get(0).getValue1()
                + "" + this.byteList.get(0).getValue2()
                + "" + this.byteList.get(1).getValue1()
                + "" + this.byteList.get(1).getValue2();
    }

    private void readFlags() {
        this.flags = this.byteList.get(2).getValue1()
                + "" + this.byteList.get(2).getValue2()
                + "" + this.byteList.get(3).getValue1()
                + "" + this.byteList.get(3).getValue2();
    }

    private void readNumberQuestions() {
        this.numberQuestions = this.byteList.get(4).getValue1()
                + "" + this.byteList.get(4).getValue2()
                + "" + this.byteList.get(5).getValue1()
                + "" + this.byteList.get(5).getValue2();
    }

    private void readNumberAnswers() {
        this.numberAnswers = this.byteList.get(6).getValue1()
                + "" + this.byteList.get(6).getValue2()
                + "" + this.byteList.get(7).getValue1()
                + "" + this.byteList.get(7).getValue2();
    }

    private void readNumberAuthority() {
        this.numberAuthority = this.byteList.get(8).getValue1()
                + "" + this.byteList.get(8).getValue2()
                + "" + this.byteList.get(9).getValue1()
                + "" + this.byteList.get(9).getValue2();
    }

    private void readNumberAdditional() {
        this.numberAdditional = this.byteList.get(10).getValue1()
                + "" + this.byteList.get(10).getValue2()
                + "" + this.byteList.get(11).getValue1()
                + "" + this.byteList.get(11).getValue2();
    }

    private void readHeader() {
        this.readIndex();
        this.readFlags();
        this.readNumberAdditional();
        this.readNumberAnswers();
        this.readNumberQuestions();
        this.readNumberAuthority();
        this.getFlagsValue();
        this.bytePosition += 12;
    }

    public void readDns(){
        //System.out.println("Reading headers");
        this.readHeader();
        //System.out.println("Reading queries");
        this.queriesList = this.getQueries();
        //System.out.println("Reading answers");
        this.answersList = this.getRR(Byte.toDecimal(this.numberAnswers));
        //System.out.println("Reading authority");
        this.authorityList = this.getRR(Byte.toDecimal(this.numberAuthority));
        //System.out.println("Reading additional");
        this.additionalList = this.getRR(Byte.toDecimal(this.numberAdditional));
    }

    private String getFlagsValue() {
        String bitFlags = Byte.toBit(this.flags);
        if(bitFlags.charAt(0) == 1){
            this.flagsValues[0] = "Message is a response (1)";//QR
            this.queryReply = "reply";
            if(bitFlags.charAt(5) == 1){this.flagsValues[2] = "Authoritative";}//AA
            else{this.flagsValues[2] = "Not an authority for the domain";}//AA
            if(bitFlags.charAt(8) == 1){this.flagsValues[5] = "Recursion Available";}//RA
            else{this.flagsValues[5] = "Recursion Not Available";}//RA
            this.flagsValues[4] = "No value, message is a reply (0)";//RD
        }
        else{
            this.flagsValues[0] = "Message is a query (0)";//QR
            this.queryReply = "query";
            this.flagsValues[2] = "No value, message is a query (0)";//AA
            this.flagsValues[5] = "No value, message is a query (0)";//RA
            if(bitFlags.charAt(7) == 1){this.flagsValues[4] = "Recursion Desired";}//RD
            else{this.flagsValues[4] = "Recursion Not Desired";}//RD
        }
        switch (Byte.bitToDecimal(bitFlags.substring(1, 5))) {
            case 0 :
                this.flagsValues[1] = "Query (0)";
                break;
            case 1 :
                this.flagsValues[1] = "IQuery (1)";
                break;
            case 2 :
                this.flagsValues[1] = "Status (2)";
                break;
            case 3 :
                this.flagsValues[1] = "Unassigned (3)";
                break;
            case 4 :
                this.flagsValues[1] = "Notify (4)";
                break;
            case 5 :
                this.flagsValues[1] = "Update (5)";
                break;
            case 6 :
                this.flagsValues[1] = "DSO (6)";
                break;
            default :
                this.flagsValues[1] = "Unassigned (7-15)";
                break;

        }

        if(bitFlags.charAt(6) == 1){this.flagsValues[3] = "Truncated";}
        else{this.flagsValues[3] = "Not Truncated";}


        this.flagsValues[6]  = "Reserved (000)";

        switch (Byte.bitToDecimal(bitFlags.substring(12 , 15))) {
            case 0 :
                this.flagsValues[7] =  "No Error (0)";
                break;
            case 1 :
                this.flagsValues[7] =  "Format Error (1)";
                break;
            case 2 :
                this.flagsValues[7] =  "Server Failure (2)";
                break;
            case 3 :
                this.flagsValues[7] =  "Non-Existent Domain (3)";
                break;
            case 4 :
                this.flagsValues[7] =  "Not Implemented (4)";
                break;
            case 5 :
                this.flagsValues[7] =  "Query Refused (5)";
                break;
            case 6 :
                this.flagsValues[7] =  "Name Exists when it should not (6)";
                break;
            case 7 :
                this.flagsValues[7] =  "RR set Exists when it should not (7)";
                break;
            case 8 :
                this.flagsValues[7] =  "RR Set that should exist does not (8)";
                break;
            case 9 :
                this.flagsValues[7] =  "Not Authorized (9)";
                break;
            case 10 :
                this.flagsValues[7] = "Name not contained in zone (10)";
                break;
            case 11 :
                this.flagsValues[7] = "DSO-TYPE Not Implemented (11)";
                break;
            default :
                this.flagsValues[7] = "Unassigned (12-15)";
                break;
        }
        return  "               " + this.flagsValues[0]
                + "\n               " + this.flagsValues[1]
                + "\n               " + this.flagsValues[2]
                + "\n               " + this.flagsValues[3]
                + "\n               " + this.flagsValues[4]
                + "\n               " + this.flagsValues[5]
                + "\n               " + this.flagsValues[6]
                + "\n               " + this.flagsValues[7];
    }

    public LinkedList<String> getQueries() {
        LinkedList<String> queries = new LinkedList<String>();
        String temp = "";
        int count = 0;
        if(this.bytePosition != 12){ //start of first name
            System.out.println("Error in cursor position before reading the queries");
            return queries;
        }
        Byte b;
        while(count < Byte.toDecimal(this.numberQuestions)){
            temp = this.getName();
            temp += this.byteList.get(bytePosition).getValues();//Type1
            this.bytePosition++;
            temp += this.byteList.get(bytePosition).getValues();//Type2
            this.bytePosition++;
            temp += this.byteList.get(bytePosition).getValues();//Class1
            this.bytePosition++;
            temp += this.byteList.get(bytePosition).getValues();//Class2
            this.bytePosition ++;
            queries.add(temp.substring(0 , temp.length() - 8)
                    + "\n                 RRType: " + this.getRRType(temp.substring(temp.length() - 8 , temp.length() - 4))
                    + "\n                 Class: " + this.getClassCode(temp.substring(temp.length() - 4)));
            count++;
        }
        return queries;
    }

    private String getRRType(String value) {
        String RRType = "";
        switch(Byte.toDecimal(value)) {
            case 1 :
                RRType = "A";
                break;
            case 28 :
                RRType = "AAAA";
                break;
            case 5 :
                RRType = "CNAME";
                break;
            case 13 :
                RRType = "HINFO";
                break;
            case 15 :
                RRType = "MX";
                break;
            case 2 :
                RRType = "NS";
                break;
            case 12 :
                RRType = "PTR";
                break;
            case 6 :
                RRType = "SOA";
                break;
            case 33 :
                RRType = "SRV";
                break;
            case 16 :
                RRType = "TXT";
                break;
            default :
                RRType = "Not supported";
                break;
        }
            return RRType;

    }

    private String getClassCode(String value) {
        String classCode = "";
        switch(Byte.toDecimal(value)){
            case 1 :
                classCode = "INTERNET";
                break;
            case 2 :
                classCode = "CSNET";
                break;
            case 3 :
                classCode = "CHAOS";
                break;
            case 4 :
                classCode = "HESIOD";
                break;
            case 254 :
                classCode = "NONE";
                break;
            case 255 :
                classCode = "ALL/AYN";
                break;
            default :
                classCode = "Not supported";
                break;
        }
        return classCode;
    }

    private String getName(){
        Name name = new Name();
        Label temp;//retrieves data
        int current;
        while(!name.isComplete()) {
            temp = new Label(this.bytePosition , this.nameList);
            temp.addByte(this.byteList.get(this.bytePosition).getValues());
            this.bytePosition++;
            if(temp.isPointer()) {
                temp.addByte(this.byteList.get(this.bytePosition).getValues());
                this.bytePosition++;
            }
            else{
                current = this.bytePosition;
                while(this.bytePosition < current + temp.getSize()) {
                    temp.addByte(this.byteList.get(this.bytePosition).getValues());
                    this.bytePosition++;
                }
            }
            name.addLabel(new Label(temp));
        }
        this.nameList.add(name);
        return name.toString();
    }


    private ArrayList<String> getRR(int iteration) { //bytePosition first byte of answers
        ArrayList<String> RRList = new ArrayList<String>();
        String name = "";
        String type = "";
        String classType = "";
        String TTL = "";
        String RDLength = "";
        String temp;
        String data = "";
        for( int i = 0 ; i < iteration ; i++){
            name = this.getName();
            type = this.getRRType(this.byteList.get(this.bytePosition).getValues() + "" + this.byteList.get(this.bytePosition + 1).getValues());
            this.bytePosition += 2;
            classType = this.getClassCode(this.byteList.get(bytePosition).getValues() + "" + this.byteList.get(bytePosition + 1).getValues());
            this.bytePosition += 2;
            TTL = Byte.toDecimal(this.byteList.get(bytePosition).getValues() + this.byteList.get(bytePosition + 1).getValues() + this.byteList.get(bytePosition + 2).getValues() + this.byteList.get(bytePosition + 3).getValues()) + "";
            this.bytePosition += 4;
            RDLength = this.byteList.get(bytePosition).getValues()+ this.byteList.get(bytePosition + 1).getValues();
            this.bytePosition += 2;
            data = this.getData(type , Byte.toDecimal(RDLength));
            temp = "Name: " + name
                    + "\n                Type: " + type
                    + "\n                Class: " + classType
                    + "\n                Time to live: " + TTL + " (s)"
                    + "\n                Data length: " + Byte.toDecimal(RDLength) + " bytes"
                    + "\n                Data: " + data + "\n";
            RRList.add(temp);
        }
        return RRList;
    }

    private String getAnswersString() {
        String answers = "\n                " ;
        for(int i = 0 ; i < this.answersList.size() ; i++) {
            answers += this.answersList.get(i) + "\n                " ;
        }
        return answers;
    }

    private String getQueriesString() {
        String queries = "\n                 " ;
        for(int i = 0 ; i < this.queriesList.size() ; i++) {
            queries += this.queriesList.get(i) + "\n                ";
        }
        return queries;
    }

    private String getAuthorityString() {
        String authority = "\n                 " ;
        for(int i = 0 ; i < this.authorityList.size() ; i++) {
            authority += this.authorityList.get(i) + "\n                ";
        }
        return authority;
    }

    private String getAdditionalString() {
        String additional = "";
        for(int i = 0 ; i < this.additionalList.size() ; i++){
            additional += this.additionalList.get(i) + "\n                ";
        }
        return additional;
    }

    private String getData(String RRType , int RDLength) {
        String data = "";
        switch(RRType) {
            case "A" :
                data = "IPv4 Address: " + this.getDataA();
                break;
            case "AAAA" :
                data = "IPv6 Address: " + this.getDataAAAA();
                break;
            case "CNAME" :
                data = "CNAME: " + this.getDataCNAME(RDLength);
                break;
            case "HINFO" :
                data = "System info: ";
                break;
            case "MX" :
                data = "Priority, Server Name: " + this.getDataMX();
                break;
            case "NS" :
                data = "Server Name: " + this.getDataNS();
                break;
            case "PTR" :
                data = "Name";
                break;
            case "SOA" :
                data = this.getDataSOA();
                break;
            case "SRV" :
                data = "3 Integer + Name: ";
                break;
            case "TXT" :
                data = "String: ";
                break;
            default :
                data = "Not supported";
                break;
        }
        return data;
    }

    private String getDataA(){
        String address = "";
        address += Byte.toDecimal(this.byteList.get(this.bytePosition).getValues()) + ".";
        this.bytePosition++;
        address += Byte.toDecimal(this.byteList.get(this.bytePosition).getValues()) + ".";
        this.bytePosition++;
        address += Byte.toDecimal(this.byteList.get(this.bytePosition).getValues()) + ".";
        this.bytePosition++;
        address += Byte.toDecimal(this.byteList.get(this.bytePosition).getValues()) + "";
        this.bytePosition++;
        return address;
    }


    private String getDataAAAA() {//16 BYES DANS UEN ADRESEUUU
        String address = "";
        for(int i =0 ; i < 7 ; i++) {
            address += this.byteList.get(this.bytePosition).getValues() + this.byteList.get(this.bytePosition + 1).getValues() + ":";
            this.bytePosition += 2;
        }
        address += this.byteList.get(this.bytePosition).getValues() + this.byteList.get(this.bytePosition + 1).getValues();
        this.bytePosition += 2;
        return address;
    }

    private String getDataCNAME(int RDLength) {
        String cname = this.getName();
        return cname;
    }

    private String getDataMX() {
        String data = Byte.toDecimal(this.byteList.get(this.bytePosition).getValues()) + "";
        this.bytePosition++;
        data += this.getName();
        return data;
    }

    private String getDataSOA() {
        String data = "\n                      Primary Name Server: ";
        data += this.getName();
        data += "\n                     Responsable's e-mail: ";
        data += this.getName();
        String value = "";
        for(int i = 0 ; i < 5 ; i++) {
            value = "";
            for(int j = 0 ; j < 4 ; j++){
                value += this.byteList.get(this.bytePosition).getValues();
                this.bytePosition++;
            }
                data += "\n                     " + DNS.SOA[i] + Byte.toDecimal(value);
        }




        return data;
    }

    private String getDataNS() {
        String data = "";
        data += this.getName();
        return data;
    }


        public String toString() {
        return "\n    Domain Name System"
                + "\n        Transaction ID: 0x" + this.id
                + "\n        Flags : 0x" + this.flags
                + "\n" + this.getFlagsValue()
                + "\n        Questions: " + Byte.toDecimal(this.numberQuestions)
                + "\n        Answer RRs: " + Byte.toDecimal(this.numberAnswers)
                + "\n        Authority RRs: " + Byte.toDecimal(this.numberAuthority)
                + "\n        Additional RRS: " + Byte.toDecimal(this.numberAdditional)
                + "\n        Queries: " + this.getQueriesString()
                + "\n        Answers: " + this.getAnswersString()
                + "\n        Authority:" + this.getAuthorityString()
                + "\n        Additional Records: " + this.getAdditionalString();


        }
}

