import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class AlphaNode extends SemanticNode{
    SemanticNode child;

    /*
    IN: factory: SemanticNodeFactory, this object will be used to create the child node.
        child: Vector<String>, A vector containing all the sub_formula of the alpha node.
     */
    public AlphaNode(SemanticNodeFactory factory, Vector<String> child) throws BadSyntaxFormException{
        this.child = factory.createSemanticNode(child);
    }

    @Override
    public boolean open() {
        return child.open();
    }

    @Override
    public Vector<Proposition> getValidLeaf() {
        return child.getValidLeaf();
    }
}
