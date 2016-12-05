import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
/*
This class represents any node in a semantic tableaux.
 */
public abstract class SemanticNode {
    /*
    ROLE: The function returns true if the node has an open branches, false otherwise.
     */
    public abstract boolean open();

    /*
    ROLE: It returns the vector containing the atoms presents in a valid leaf.
     */
    public abstract Vector<Proposition> getValidLeaf();
}
