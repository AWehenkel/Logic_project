/**
 * Created by antoinewehenkel on 4/12/16.
 */
//This class is used to represent any atomic proposition.
public abstract class Proposition {
    private boolean value;

    /*
    IN: value: boolean, the value of the atom.
     */
    public Proposition(boolean value){
        this.value = value;
    }

    /*
    IN: value: String, if the first character is ! the atom is considered has false else it is true.
     */
    public Proposition(String value){
        if(value.charAt(0) == '!')
            this.value = false;
        else
            this.value = true;
    }

    public boolean getValue(){
        return value;
    }

}
