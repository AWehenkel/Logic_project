import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class SemanticLeaf extends SemanticNode{
    private Vector<MastermindProposition> atoms;


    @Override
    public boolean open() {
        return false;
    }

    @Override
    public String getValidProposition() {
        return null;
    }
}
