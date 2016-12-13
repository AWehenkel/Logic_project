public class Main {

    public static void main(String[] args) {

        int values[] = new int[3];

        for (int i = 0; i < args.length; i++) {
            try {
                values[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        MastermindLogicer m = new MastermindLogicer(values[0], values[1], values[2]);
        m.SemanticNode();
    }
}