package myatm;

public class NotEnoughMoneyInATM extends RuntimeException {
	
	public NotEnoughMoneyInATM() { super(); }


	public NotEnoughMoneyInATM(String s) { super(s); }

}
