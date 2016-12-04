/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class MastermindProposition {
    private int position;
    private int color;
    private boolean value;


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof MastermindProposition))
            return false;
        return (position == ((MastermindProposition) obj).position && color == ((MastermindProposition) obj).color);
    }

    public boolean getValue(){
        return value;
    }
}
