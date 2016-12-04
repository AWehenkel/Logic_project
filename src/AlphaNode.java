/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class AlphaNode extends SemanticNode{
    SemanticNode child;

    @Override
    public boolean open() {
        return false;
    }

    @Override
    public String getValidProposition() {
        return null;
    }
}
