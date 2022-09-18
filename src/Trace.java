import java.io.IOException;
import java.util.LinkedList;

public class Trace {//class quon va utiliser pour stocker les infos décodées
    private LinkedList<Byte> listByte;
    private LinkedList<Offset> listOffset;
    private String trace;

    public Trace() {
        this.listByte = new LinkedList<Byte>();
        this.listOffset = new LinkedList<Offset>();
        this.trace = "\n";
    }

    public boolean initTrace(TraceReader tR) throws IOException {
        if(tR.number == 0){
            System.out.println("TraceReader not initialized, initTrace cancelled");
            return false;
        }
        Offset offset;
        Byte b;
        int bytePosition = 0;
        LinkedList<Byte> temp;
        if(!tR.skipToOffset()){
            return false;
        }
        offset = tR.readOffset();
        if(offset.getDecimalValue() != 0) {
            System.out.println("First offset = " + offset.getDecimalValue() + " != 0");
            return false;
        }
        this.listOffset.add(offset);
        tR.skipToByte();
        temp = tR.readByteLine(bytePosition);
        this.listByte.addAll(temp);
        bytePosition += temp.size();
        while(tR.skipLastChar()) {
            if(tR.isNextOffset()) {
                tR.br.mark(1000);
                offset = tR.readOffset();
                //System.out.println("offsetinit: "+ offset);
                if(offset.getDecimalValue() != bytePosition) {
                    if(offset.getDecimalValue() == 0){
                        System.out.println("End of trace");
                        //System.out.println("DERNIERE LIGNE    " + tR.br.readLine());
                        tR.br.reset();
                        return true;
                    }
                    System.out.println("Error in offset value " + offset.getDecimalValue() +" != " + (bytePosition));
                    return false;
                }
                this.listOffset.add(offset);
                tR.skipToByte();
                temp = tR.readByteLine(bytePosition);
                this.listByte.addAll(temp);
                bytePosition += temp.size();
            }
        }
        if(this.listByte.size() < 14) {
            System.out.println("Error : not enough bytes in the trace");
            return false;
        }
        return true;
    }

    public void FormTrace(){
        int k = 0;
        for (int i = 0 ; i < this.listOffset.size() ; i++){
            this.trace += "\n";
            this.trace += this.listOffset.get(i);
            this.trace += " ";
            for(int j = 0 ; j < 16 && k < this.listByte.size() ; j++){
                if(j == 7){
                    this.trace += " ";
                }
                Byte b = this.listByte.get(k);
                String v = b.getValues();
                this.trace += v;
                this.trace += " ";
                k++;
            }
        }
        this.trace += "\n";
    }

    public void setTrace(String s){
        this.trace = s;
    }
    public LinkedList<Byte> getListByte() {
        return this.listByte;
    }
    public LinkedList<Offset> getListOffset() {
        return this.listOffset;
    }

    public String getTrace(){
        return this.trace;
    }

    public String toString() {
        return "trace:      "
                + this.listByte + "\n"
                + this.listOffset + "\n";
    }
}