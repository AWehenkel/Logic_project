/**
 * Created by antoinewehenkel on 5/12/16.
 */
/*
Exception thrown when the formula can't be interpreted by the SemanticNodeFactory.
 */
public class BadSyntaxFormException extends Exception{
    public BadSyntaxFormException(String formula){
        System.err.println("The following formula has not a valid syntax: " + formula);
    }
}
