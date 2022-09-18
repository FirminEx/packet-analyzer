import java.util.LinkedList;

public class Label {
    private int size;
    private String value;//all hexa char in the label, no space
    private boolean pointer;
    private int position;//position of first byte not representing a size
    private LinkedList<Name> nameList;

    public Label(int pPosition ,  LinkedList<Name> pNameList) {
        this.value = "";
        this.size = 0;
        this.position = pPosition;
        this.nameList = pNameList;
    }

    public Label(Label clone) {
        this.size = clone.size;
        this.value = clone.value;
        this.pointer = clone.pointer;
        this.position = clone.position;
        this.nameList = clone.nameList;
    }

    public void addByte(String byteValue) {
        this.value += byteValue;
        if(this.value.length() == 2) {
            String bitName = Byte.toBit(this.value.charAt(0) + "" + this.value.charAt(1));
            if(bitName.charAt(0) == '1' && bitName.charAt(1) == '1') {//pointer
                this.pointer = true;
                this.size = 2;
            }
            else {
                this.size = Byte.toDecimal(this.value);
            }
        } if(this.value.length() == 4 && this.pointer) {
            this.initPointer();
        }
    }

    public Name getNamePointed(int pointed) {
        if(!this.pointer) {
            System.out.println("Error this is not a pointer");
            return null;
        }
        for(int i = 0 ; i < this.nameList.size() ; i++){
            if(this.nameList.get(i).hasLabel(pointed)){
                return this.nameList.get(i);
            }
        }
        System.out.println("Pointed location not found pointer pos:" + this.position + "  pointer value: " + this.value + "looking for: " + pointed);
        System.out.println("name list " + this.nameList.get(0).hasLabel(12));
        return null;
    }

    public String getCleanLabel() {
        if(this.isPointer()) {
            return this.value;
        }
        if(this.size == 0){
            return "";
        }
        else{
            return Byte.toAscii(this.getValue().substring(2));//minus the length
        }
    }

    public void initPointer() {
        if(this.pointer) {
            String bitName = Byte.toBit(this.value.charAt(0) + "" + this.value.charAt(1) + this.value.charAt(2) + this.value.charAt(3));
            int pointed = Integer.parseInt(bitName.substring(2) , 2);
            //System.out.println("sending:" + Integer.parseInt(bitName.substring(2) , 2));
            this.value = this.getNamePointed(pointed).toString(this.getNamePointed(pointed).getLabel(pointed));
            return;
        }
        System.out.println("This is not a pointer");
    }

    public int getPosition() {
        return this.position;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isPointer() {
        return this.pointer;
    }

    public int getSize() {
        return this.size;
    }

    public String toString() {
        return "Label starting at: " + this.position + " is a pointer: " + this.pointer
               + " has length: " + this.size + " has value: " + this.value;
    }

}
