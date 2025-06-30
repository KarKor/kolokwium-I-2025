public class NoWinnerException extends Exception {

    public NoWinnerException(String message) { super(message);}

    public String getMessage(){
        return "No winning candidate";
    }
}
