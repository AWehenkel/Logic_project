/**
 * Created by antoinewehenkel on 4/12/16.
 */
/*
This class represents a master mind proposition.
 */
public class MastermindProposition extends Proposition{
    private int position;
    private int color;

    public MastermindProposition(int position, int color, boolean value){
        super(value);
        this.position = position;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MastermindProposition))
            return false;
        return (position == ((MastermindProposition) obj).position && color == ((MastermindProposition) obj).color);
    }

}
