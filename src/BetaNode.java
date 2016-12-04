import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class BetaNode extends SemanticNode{
    private SemanticNode left_child;
    private SemanticNode right_child;

    public BetaNode(SemanticNodeFactory factory, String left, String right){
        left_child = factory.createSemanticNode(left);
        right_child = factory.createSemanticNode(right);
    }

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public String getValidProposition() {
        return null;
    }
}
