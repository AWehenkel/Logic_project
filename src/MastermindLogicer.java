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
    private Vector<Pair<Integer,Integer>> rouge;
    private Vector<Pair<Integer,Integer>> blanc;
    private Vector<Pair<Integer,Integer>> etoile;

    private MastermindSemanticNodeFactory factory;
    private int P;
    private int C;
    private int R;

    /*
    * In : int C : the number of color
    *      int P : the number of pegs
    *      int R : the number of guess
    */
    public MastermindLogicer(int C, int P, int R){
        this.P = P;
        this.C = C;
        this.R = R;
        rouge = new Vector<>();
        blanc = new Vector<>();
        etoile = new Vector<>();
        factory = new MastermindSemanticNodeFactory();
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    /*
    * Create a part of the formula from a peg where the value is R
    */
    private String R(Pair<Integer,Integer> myPair){
        String r = new String();
        r += myPair.getKey().toString() + "_" + myPair.getValue().toString();
        //exclude all color different from the one at this position
        for(int i = 0 ; i < C ; i++){
            if(i+1 != myPair.getValue()){
                r += "&!" + myPair.getKey().toString() + "_" + Integer.toString(i+1).charAt(0);
            }
        }
        return r;
    }

    /*
    * Create a part of the formula from a peg where the value is B
    */
    private String B(Pair<Integer,Integer> myPair){
        String b = new String();
        Vector<Integer> forbiddenPlace = new Vector<>();
        forbiddenPlace.addElement(myPair.getKey());
        //particular case where Only one B and the rest of R

        if(blanc.size() == 1 && rouge.size() == P-1){
            b += "!" + myPair.getKey().toString() + "_" + myPair.getValue().toString() + "&" + myPair.getKey().toString() + "_" + myPair.getValue().toString();
        }
        else{
            b += "!" + myPair.getKey().toString() + "_" + myPair.getValue().toString();
            b += "&(";
            // all the R are forbidden values
            for (int j=0; j < rouge.size(); j++){
                forbiddenPlace.addElement(rouge.elementAt(j).getKey());
            }
            for (int j=0; j < etoile.size(); j++){
                if(myPair.getKey().equals(etoile.elementAt(j).getKey()))
                    forbiddenPlace.addElement(etoile.elementAt(j).getKey());
            }
            for (int j=0; j < P; j++) {
                if (!forbiddenPlace.contains(j))
                    b += "(" + R(new Pair<>(j, myPair.getValue())) + ")|";
            }
            b = b.substring(0,b.length()-1);
            b += ")";
        }
        return b;
    }

    /*
    * Create a part of the formula from a peg where the value is *
    */
    private String E(Pair<Integer,Integer> myPair){
        String e = new String();
        for (int i=0;i<blanc.size();i++) {
            //there is an other peg with value B and same color
            if (blanc.elementAt(i).getValue() == myPair.getValue())
                return "!" + myPair.getKey().toString() + "_" + myPair.getValue().toString();
        }
        //discard this color on every peg.
        for (int i=0; i < P ; i++){
            if(i != 0)
                e += "&!" + Integer.toString(i).charAt(0) + "_" + myPair.getValue().toString();
            else
                e += "!" + Integer.toString(i).charAt(0) + "_" + myPair.getValue().toString();
        }
        return e;
    }

    /*
    * This function get all the open leaf from the semantic tableau and create a valid solution for the
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
        int[] proposition = new int[P];
        for (int i = 0; i < P; i++)
            proposition[i] = -1;

        for (int i = 0; i < P; i++){
            for (int j = 0; j < prop.size(); j++){
                if(prop.elementAt(j).getKey() == i)
                    //put a good proposition at index i
                    proposition[i] = prop.elementAt(j).getValue();
            }
            //if some proposition has not been set put a new created one which is not in the wrong proposition
            if(proposition[i] == -1){
                for (int j=0; j < C; j++){
                    colorlist.addElement(j+1);
                }
                for(int j =0; j < wrong.size();j++){
                    if(wrong.elementAt(j).getKey() == i)
                        colorlist.remove(wrong.elementAt(j).getValue());
                }
                proposition[i] = colorlist.elementAt(0);
            }
        }
        for (int i =0;i < P ; i++) {
            System.out.print(proposition[i]);
        }
        System.out.println();
    }

    //The "main" method of the class, it reads a input stream and say if it is consitant or not, if it is consistant
    // it suggest a new one.
    public void SemanticNode(){
        try {
            String m = new String();
            for (int j = 0 ; j<R; j++){
                String[] token = br.readLine().split("[ \t]+");
                etoile.clear();
                blanc.clear();
                rouge.clear();
                for (int i = 0; i < P ; i++) {
                    if(token[i+P].equals("*"))
                        etoile.addElement(new Pair<>(i,Integer.parseInt(token[i])));
                    else if (token[i+P].equals("B"))
                        blanc.addElement(new Pair<>(i,Integer.parseInt(token[i])));
                    else
                        rouge.addElement(new Pair<>(i,Integer.parseInt(token[i])));
                }
                for (int i = 0; i <rouge.size();i++){
                    m += R(rouge.elementAt(i)) + "&";
                }
                for (int i = 0; i <blanc.size();i++){
                    m += B(blanc.elementAt(i)) + "&";
                }
                for (int i = 0; i < etoile.size(); i++){
                    m += E(etoile.elementAt(i)) + "&"   ;
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