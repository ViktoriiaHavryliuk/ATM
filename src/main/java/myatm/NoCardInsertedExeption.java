package myatm;

public class NoCardInsertedExeption extends Exception {

	public NoCardInsertedExeption() { super(); }


	public NoCardInsertedExeption(String s) { super(s); }
	
    /*public NoCardInsertedExeption(String message, Throwable cause) {
        super(message, cause);
    }
    public NoCardInsertedExeption(Throwable cause) {
        super(cause);
    }*/
}

