import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class SemanticLeaf extends SemanticNode{
    private Vector<Proposition> atoms;

    public SemanticLeaf(Vector<Proposition> atoms){
        this.atoms = atoms;
        this.atoms = new Vector<>();
        int j;
        for(int i = 0; i < atoms.size(); i++) {
            for (j = 0; j < this.atoms.size(); j++)
                if (atoms.get(i).toString().equals(this.atoms.get(j).toString()))
                    break;

            if(j == this.atoms.size())
                this.atoms.add(atoms.get(i));
        }
    }

    @Override
    public boolean open() {
        for(int i = 0; i < atoms.size(); i++)
            for(int j = i + 1; j < atoms.size(); j++)
                if(atoms.get(i).equals(atoms.get(j)) && atoms.get(i).getValue() != atoms.get(j).getValue())
                    return false;

        return true;
    }

    @Override
    public Vector<Proposition> getValidLeaf() {
        if(!open())
            return null;
        return this.atoms;
    }
}
