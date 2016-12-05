import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        MastermindSemanticNodeFactory fact = new MastermindSemanticNodeFactory();
        SemanticNode sem_tab = null;
        try {
            sem_tab = fact.createSemanticNode("(!1_1|!1_2)&1_1");
        } catch (BadSyntaxFormException e) {
            e.printStackTrace();
        }
        if(sem_tab.open())
            System.out.println("consistent");

        System.out.println(sem_tab.getValidLeaf());
    }
}
