/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class MastermindSemanticNodeFactory extends SemanticNodeFactory{
    @Override
    public SemanticNode createSemanticNode(String formula) {
        return null;
        /*
        Donc en gros ici on regarde quelle formule on a et selon le cas on retourne un LeafNode, un BetaNode ou un AlphaNode.
        Il faudra juste se mettre d'accord sur comment on représente en string la formule :), le plus facile me semble de faire
        avec les notations informatiques(!, &, |)
         */
    }
}
