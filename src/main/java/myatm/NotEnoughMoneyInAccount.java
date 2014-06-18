package myatm;

public class NotEnoughMoneyInAccount extends Exception {
	public NotEnoughMoneyInAccount (String messadge){
		super (messadge);
	}
	public String toString(){
        return "NotEnoughMoneyInAccount";
    }
	

}
