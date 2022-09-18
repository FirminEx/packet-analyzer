import java.util.LinkedList;

public class Name {
    private LinkedList<Label> labelList;
    private boolean isComplete;

    public Name() {
        this.labelList = new LinkedList<>();
        this.isComplete = false;
    }

    public void addLabel(Label label) {//add label by copy
        this.labelList.add(label);
        if(label.getValue().equals("00")) {
            this.isComplete = true;
        }
        if(label.isPointer()) {
            this.isComplete = true;
        }
    }

    public void addFrom(Name pName , int position) {
        this.labelList.addAll(pName.getFrom(position));
    }

    public LinkedList<Label> getFrom(int from) {
        return new LinkedList<Label>(this.labelList.subList(from , this.labelList.size()));
    }



    public boolean hasLabel(int position) {
        for(int i = 0; i < this.labelList.size() ; i++) {
            if(this.labelList.get(i).getPosition() == position){
                return true;
            }
        }
        return false;
    }

    public int getLabel(int position) {
        for(int i = 0 ; i < this.labelList.size() ; i++) {
            if(this.labelList.get(i).getPosition() == position){
                return i;
            }
        }
        return -1;
    }


    public String getValuesFrom(int from) {
        String values = "";
        for(int i = from ; i < this.labelList.size() ; i++) {
            values += this.labelList.get(i).getCleanLabel();
        }
        return values;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    /*public String toString(int from) {
        String cleanName = "";
        for(int i = from; i < this.labelList.size() ; i++) {
            cleanName += this.labelList.get(i).getCleanLabel();
            if(i != this.labelList.size() - 2 && !(this.labelList.get(i).getValue().equals("00"))){
                cleanName += ".";
            }
        }
        return cleanName;
    }*/

    public String toString(int position) {
        String cleanName = "";
        for(int i = position ; i < this.labelList.size() ; i++){
             if(this.labelList.get(i).isPointer()) {
                 cleanName += this.labelList.get(i).getCleanLabel();
             }
             else if(this.labelList.get(i).getValue().equals("00") || ( (i == this.labelList.size() - 2) && (this.labelList.get(i + 1).getValue().equals("00")) )) {
                 cleanName += this.labelList.get(i).getCleanLabel();
             }
             else {
                 cleanName += this.labelList.get(i).getCleanLabel() + ".";
             }
        }
        return cleanName;
    }

    public String toString() {
        return this.toString(0);
    }

}
