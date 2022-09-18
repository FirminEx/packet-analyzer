import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


public class TraceReader {
    private FileReader fichier;
    public BufferedReader br;
    static int count = 1;
    public int number; //identify trace reader number related to count
    public int numberTrace; //identify number of trace in the file

    public TraceReader(String nomFichier) {
        try {
            fichier = new FileReader(nomFichier);
            br = new BufferedReader(this.fichier);
            this.number  = count;
            this.numberTrace = 0;
            count++;
            Offset of;
            while(this.skipToOffset()){ //count number of offset, 1 offset equals to 0 = a new trace
                of = this.readOffset();
                if (of.getDecimalValue() == 0) {
                    this.numberTrace++;
                }
            }
            br.close();
            fichier.close();
            fichier = new FileReader(nomFichier);
            br = new BufferedReader(this.fichier);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public int getNextChar() throws IOException {
        this.br.mark(2);
        int c = br.read();
        this.br.reset();
        return c;
    }

    public char readHalfBytes() throws IOException {
        int c = -1;
        try {
            c = br.read();
            return (char) c;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) c;
    }

    public void skip() throws IOException { //used to ignore space
        this.br.read();
    }

    public boolean isNextByte() throws IOException {  //are next 2 char bytes, place cursor back to when called
        this.br.mark(10);
        char value1 = (char) this.br.read();
        char value2 = (char) this.br.read();
        int value3 =  this.br.read();
        boolean b = Byte.isHexa(value1) && Byte.isHexa(value2) && ((char) value3 == ' ' || value3 == -1 || (char) value3 == '\n');
        this.br.reset();
        return b;
    }


    public boolean skipLastChar() throws IOException { // used to skip the last char of a line, place cursor at start of next line
        int c = this.br.read();
        if(c != '\n') {
            while ((char) c != '\n') {
                c = this.br.read();
                if (c == -1) {
                    System.out.println("End of file");
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    public void close(){
        try {
            this.br.close();
            this.fichier.close();
            System.out.println("TraceReader number " + this.number + " successfully closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Offset readOffset() throws IOException { //once the cursor is on the start of a line use this function
        char c = this.readHalfBytes();
        Offset of = new Offset();
        while(c != ' ') {
            if(Byte.isHexa(c)) {
                of.addHexaValue(c);
            }
            else {
                System.out.println("Error : trying to add non hexa value to offset : " + c);
                return new Offset(); //return an empty offset
            }
            c = this.readHalfBytes();
        }
        return of;
    }

    public boolean isNextOffset() throws IOException {
        this.br.mark(100);
        char c = (char) this.br.read();
        int count = 0;
        if(Byte.isHexa(c)) {
            while(Byte.isHexa(c)) {
                count++;
                c = (char) br.read();
            }
            this.br.reset();
            return c == ' ' && count > 2;//offset encoded on more than 2 hexa
        }
        this.br.reset();
        return false;
    }

    public Byte readByte(int position) throws IOException {
        return new Byte(this.readHalfBytes(), this.readHalfBytes(), position);
    }

    public boolean skipToByte() throws IOException {
        int c;
        while(!this.isNextByte()) {
            c = br.read();
            if(c == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean skipToOffset() throws IOException {
        while(!this.isNextOffset()) {
            if(!this.skipLastChar()) {
                System.out.println("No offset found");
                return false;
            }
        }
        return true;
    }

    public LinkedList<Byte> readByteLine(int pPosition) throws IOException {
        LinkedList<Byte> list = new LinkedList<Byte>();
        int position = pPosition;
        while(this.isNextByte()) {
            Byte b = this.readByte(position);
            list.add(b);
            position++;
            if((char) this.getNextChar() == '\n' ) {
                return list;
            }
            this.skip();
        }
        return list;
    }

    public boolean skipToTrace(int number) throws IOException {//go to trace number, considering the first trace is number 1
        if( number < 1 || number > this.numberTrace){
            System.out.println("There is no trace number " + number);
            return false;
        }
        Offset of;
        int count = 0;
        while(count != number){
            this.skipToOffset();
            this.br.mark(1000000);
            of = this.readOffset();
            //System.out.println(of);
            if(of.getDecimalValue() == 0){
                count++;
            }
        }
        this.br.reset();
        return true;
    }

    public int getNumberTrace(){
        return this.numberTrace;
    }
}



