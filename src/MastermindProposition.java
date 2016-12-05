import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
/*
This class represents a master mind proposition.
 */
public class MastermindProposition extends Proposition{
    private int position;
    private int color;

    /*
    IN: position: int, the position of the proposition.
        color: int, the color of the proposition.
        value: boolean, the value of the proposition.
     */
    public MastermindProposition(int position, int color, boolean value){
        super(value);
        this.position = position;
        this.color = color;
    }

    /*
    IN: str: String, a string which represents the proposition.
                    the form of the string should x_y or !x_y, where
                     x is the position and y the color. ! is there
                     when the value of the propostion is false.
     */
    public MastermindProposition(String str){
        super(str);
        str = str.replace("!", "");
        position = Integer.parseInt(str.substring(0, str.indexOf("_")));
        color = Integer.parseInt(str.substring(str.indexOf("_") + 1));
    }

    public String toString(){
        if(getValue())
            return (position + "_" + color);
        return ("!" + position + "_" + color);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MastermindProposition))
            return false;
        return (position == ((MastermindProposition) obj).position && color == ((MastermindProposition) obj).color);
    }

    public int getColor(){
        return color;
    }

    public int getPosition(){
        return position;
    }
}
