package ba.unsa.etf.rpr;

public class IncorrectArticleException extends Exception{
    public IncorrectArticleException(String errorMessage){
        super(errorMessage);
    }
}
