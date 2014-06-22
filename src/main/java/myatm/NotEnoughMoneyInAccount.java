package myatm;

public class NotEnoughMoneyInAccount extends RuntimeException {
	
	public NotEnoughMoneyInAccount() { super(); }


	public NotEnoughMoneyInAccount(String s) { super(s); }
	

}
