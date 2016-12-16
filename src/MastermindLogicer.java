import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Created by pierre on 12/5/16.
 */

/*
* This class is responsible for creating a formula out of the input stream and proposing
* a new valid code for the Mastermind
*/

public class MastermindLogicer{


    private SemanticNode s_tableau;
    private BufferedReader br;

    //Vector containing pairs where key is the position and value the color at this position
    private Vector<Pair<Integer,Integer>> red;
    private Vector<Pair<Integer,Integer>> white;
    private Vector<Pair<Integer,Integer>> star;

    private MastermindSemanticNodeFactory factory;
    private int pegs;
    private int color;
    private int played;

    /*
    * In : int C : the number of color
    *      int P : the number of pegs
    *      int R : the number of guess
    */
    public MastermindLogicer(int C, int P, int R){
        pegs = P;
        color = C;
        played = R;
        red = new Vector<>();
        white = new Vector<>();
        star = new Vector<>();
        factory = new MastermindSemanticNodeFactory();
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /*
    * Create a part of the formula from a peg where the feedback is R
    * In : Pair<Integer,Integer> myPair a pair indicating the current peg to treat.
    */
    private String redPeg(Pair<Integer,Integer> myPair){
        String red_sub_formula = new String();
        red_sub_formula += myPair.getKey().toString() + "_" + myPair.getValue().toString();
        //exclude all color different from the one at this position
        for(int i = 0 ; i < color ; i++){
            if(i+1 != myPair.getValue()){
                red_sub_formula += "&!" + myPair.getKey().toString() + "_" + Integer.toString(i+1);
            }
        }
        return red_sub_formula;
    }

    /*
    * Create a part of the formula from a peg where the feedback is B
    * In : Pair<Integer,Integer> myPair a pair indicating the current peg to treat.
    */
    private String whitePeg(Pair<Integer,Integer> myPair){
        String white_sub_formula = new String();
        Vector<Integer> forbiddenPlace = new Vector<>();
        forbiddenPlace.addElement(myPair.getKey());
        //particular case where Only one B and the rest of R

        if(white.size() == 1 && red.size() == pegs-1){
            white_sub_formula += "!" + myPair.getKey().toString() + "_" + myPair.getValue().toString() + "&" + myPair.getKey().toString() + "_" + myPair.getValue().toString();
        }
        else{
            white_sub_formula += "!" + myPair.getKey().toString() + "_" + myPair.getValue().toString();
            white_sub_formula += "&(";
            // all the R are forbidden values
            for (int j=0; j < red.size(); j++){
                forbiddenPlace.addElement(red.elementAt(j).getKey());
            }
            for (int j=0; j < star.size(); j++){
                if(myPair.getKey().equals(star.elementAt(j).getKey()))
                    forbiddenPlace.addElement(star.elementAt(j).getKey());
            }
            for (int j=0; j < pegs; j++) {
                if (!forbiddenPlace.contains(j))
                    white_sub_formula += "(" + redPeg(new Pair<>(j, myPair.getValue())) + ")|";
            }
            white_sub_formula = white_sub_formula.substring(0,white_sub_formula.length()-1);
            white_sub_formula += ")";
        }
        return white_sub_formula;
    }

    /*
    * Create a part of the formula from a peg where the feedback is *
    * In : Pair<Integer,Integer> myPair a pair indicating the current peg to treat.
    */
    private String starPeg(Pair<Integer,Integer> myPair){
        String star_sub_formula = new String();
        for (int i=0;i<white.size();i++) {
            //there is an other peg with value B and same color
            if (white.elementAt(i).getValue().equals(myPair.getValue()))
                return "!" + myPair.getKey().toString() + "_" + myPair.getValue().toString();
        }
        //discard this color on every peg.
        for (int i=0; i < pegs ; i++){
            if(i != 0)
                star_sub_formula += "&!" + Integer.toString(i).charAt(0) + "_" + myPair.getValue().toString();
            else
                star_sub_formula += "!" + Integer.toString(i).charAt(0) + "_" + myPair.getValue().toString();
        }
        return star_sub_formula;
    }

    /*
    * This function get all the open leaf from the semantic tableau and create a consistent solution for the
    * mastermind out of the open leafs.
    */
    private void guess(){
        Vector<Proposition> leafs = s_tableau.getValidLeaf();
        Vector<Pair<Integer,Integer>> prop = new Vector<>();
        Vector<Pair<Integer,Integer>> wrong = new Vector<>();
        Vector<Integer> colorlist = new Vector<>();


        for (int i = 0; i < leafs.size(); i++){
            //get all the non-negation leafs.
            if(leafs.elementAt(i).toString().matches("^[^!][0-9]*_[0-9]")){
                String s = leafs.elementAt(i).toString();
                String ss[] = s.split("_");
                prop.addElement(new Pair<>(Integer.parseInt(ss[0]),Integer.parseInt(ss[1])));
                //get all the negation leafs.
            }else{
                String s = leafs.elementAt(i).toString();
                //remove "!"
                s = s.substring(1,s.length());
                String ss[] = s.split("_");
                wrong.addElement(new Pair<>(Integer.parseInt(ss[0]),Integer.parseInt(ss[1])));
            }
        }
        //a proposition
        int[] proposition = new int[pegs];
        for (int i = 0; i < pegs; i++)
            proposition[i] = -1;

        for (int i = 0; i < pegs; i++){
            for (int j = 0; j < prop.size(); j++){
                if(prop.elementAt(j).getKey() == i)
                    //put a good proposition at index i
                    proposition[i] = prop.elementAt(j).getValue();
            }
            //if some proposition has not been set put a new created one which is not in the wrong proposition
            if(proposition[i] == -1){
                for (int j=0; j < color; j++){
                    colorlist.addElement(j+1);
                }
                for(int j =0; j < wrong.size();j++){
                    if(wrong.elementAt(j).getKey() == i)
                        colorlist.remove(wrong.elementAt(j).getValue());
                }
                proposition[i] = colorlist.elementAt(0);
            }
        }
        for (int i =0;i < pegs ; i++) {
            System.out.print(proposition[i]);
        }
        System.out.println();
    }

    /*
    * The "main" method of the class, it reads a input stream and say if it is consistent or not, if it is consistent
    * it suggest a new solution for the mastermind.
    */
    public void SemanticNode(){
        try {
            String m = new String();
            for (int j = 0 ; j<played; j++){
                String[] token = br.readLine().split("[ \t]+");
                star.clear();
                red.clear();
                white.clear();
                for (int i = 0; i < pegs ; i++) {
                    if(token[i+pegs].equals("*"))
                        star.addElement(new Pair<>(i,Integer.parseInt(token[i])));
                    else if (token[i+pegs].equals("B"))
                        white.addElement(new Pair<>(i,Integer.parseInt(token[i])));
                    else
                        red.addElement(new Pair<>(i,Integer.parseInt(token[i])));
                }
                for (int i = 0; i <red.size();i++){
                    m += redPeg(red.elementAt(i)) + "&";
                }
                for (int i = 0; i < white.size();i++){
                    m += whitePeg(white.elementAt(i)) + "&";
                }
                for (int i = 0; i < star.size(); i++){
                    m += starPeg(star.elementAt(i)) + "&"   ;
                }
            }
            m = m.substring(0,m.length()-1);
            s_tableau = factory.createSemanticNode(m);
        }catch(IOException | BadSyntaxFormException e){
            e.printStackTrace();
        }
        if(s_tableau.open()){
            System.out.println("true");
            guess();
        }else{
            System.out.println("false");
        }
    }

}