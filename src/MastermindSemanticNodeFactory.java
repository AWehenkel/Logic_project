import javafx.util.Pair;
import java.util.Vector;

/**
 * Created by antoinewehenkel on 4/12/16.
 */
public class MastermindSemanticNodeFactory extends SemanticNodeFactory{
    @Override
    public SemanticNode createSemanticNode(Vector<String> formula) throws BadSyntaxFormException{
        Pair<String, String> sub_formula;

        //Search for an alpha formula:
        for(int i = 0; i < formula.size(); i++){
            formula.set(i, removeUselessParentheses(formula.get(i)));
            sub_formula = separateFormula(0, '&', formula.get(i));
            if(sub_formula == null)
                continue;
            Vector<String> child = new Vector<>(formula);
            child.remove(i);
            child.add(sub_formula.getKey());
            child.add(sub_formula.getValue());
            return new AlphaNode(this, child);
        }

        //Search for a beta formula
        for(int i = 0; i < formula.size(); i++){
            formula.set(i, removeUselessParentheses(formula.get(i)));
            sub_formula = separateFormula(0, '|', formula.get(i));
            if(sub_formula == null)
                continue;
            Vector<String> left = new Vector<>(formula);
            Vector<String> right = new Vector<>(formula);
            left.remove(i);
            right.remove(i);
            right.add(sub_formula.getKey());
            left.add(sub_formula.getValue());
            return new BetaNode(this, left, right);
        }
        //All the propositions are atomic.
        Vector<Proposition> atoms = new Vector<>();
        for(int i = 0; i < formula.size(); i++)
            atoms.add(new MastermindProposition(formula.get(i)));
        return new SemanticLeaf(atoms);
    }

    @Override
    public SemanticNode createSemanticNode(String formula) throws BadSyntaxFormException{
        Vector<String> vec_formula = new Vector<>();
        vec_formula.add(formula);
        return createSemanticNode(vec_formula);
    }

    /*
    IN: level: int, the level where we want to find a formula.
        separator: char, a char which can be interpreted as a binary operator(beta or alpha rule)
        formula: String, the formula in which sear for sub formula.
    OUT: Pair<String, String>: it contains the two subformula found, if no subformula found return null.
    ROLE: To search after sub-formula in a formula in a precise level(the level is the number of parenthesis around the
    separator).
     */
    private Pair<String, String> separateFormula(int level, char separator, String formula) throws BadSyntaxFormException{
        int curr_level = 0;
        char curr_char;
        for(int i = 0; i < formula.length(); i++){
            curr_char = formula.charAt(i);
            if(curr_char == '(')
                curr_level++;
            else if(curr_char == ')')
                curr_level--;
            else if(curr_char == separator)
                if(curr_level == level){
                    String left = formula.substring(0, i);
                    left = removeUselessParentheses(left);
                    String right = formula.substring(i + 1);
                    right = removeUselessParentheses(right);
                    return new Pair<>(left, right);
                }
        }
        if(curr_level != 0)
            throw new BadSyntaxFormException(formula);
        return null;
    }

    /*
    IN: formula: String, a text.
    OUT: The formula string without any external parentheses if they are useless.
    Throw: BadSyntaxFormException
     */
    private String removeUselessParentheses(String formula) throws BadSyntaxFormException{
        if(formula.charAt(0) == '(') {
            int curr_level = 1;
            for (int i = 1; i < formula.length(); i++){
                if(formula.charAt(i) == '(')
                    curr_level++;
                else if(formula.charAt(i) == ')') {
                    if (curr_level == 1) {
                        if (i == (formula.length() - 1))
                            return formula.substring(1, formula.length() - 1);
                        return formula;
                    }
                    curr_level--;
                }
            }
            throw new BadSyntaxFormException(formula);
        }
        return formula;
    }
}
