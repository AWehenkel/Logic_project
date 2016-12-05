import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class BetaNode extends SemanticNode{
    private SemanticNode left_child;
    private SemanticNode right_child;

    /*
    IN: factory: SemanticNodeFactory, this object will be used to create the children nodes.
        left: Vector<String>, A vector containing all the sub_formula of the left child node.
        right: Vector<String>, A vector containing all the sub_formula of the right child node.
     */
    public BetaNode(SemanticNodeFactory factory, Vector<String>  left, Vector<String>  right) throws BadSyntaxFormException{
        left_child = factory.createSemanticNode(left);
        right_child = factory.createSemanticNode(right);
    }

    @Override
    public boolean open() {
        return (left_child.open() || right_child.open());
    }

    @Override
    public Vector<Proposition> getValidLeaf() {
        if(left_child.open())
            return left_child.getValidLeaf();
        else if(right_child.open())
            return right_child.getValidLeaf();
        return null;
    }
}
