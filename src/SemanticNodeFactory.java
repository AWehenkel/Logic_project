import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
/*
This class is responsible to create the appropriate node from a string formula.
 */
public abstract class SemanticNodeFactory {
    /*
    IN: formula: Vector<String>, a vector containing the string representation of the components of the SemanticNode to create.
    ROLE: To create the appropriate SemanticNode.
     */
    public abstract SemanticNode createSemanticNode(Vector<String> formula) throws BadSyntaxFormException;

    /*
    IN: formula: String, a string containing the representation of the SemanticNode to create(a root node).
    ROLE: To create the appropriate SemanticTableaux.
     */
    public abstract SemanticNode createSemanticNode(String formula) throws BadSyntaxFormException;
}
