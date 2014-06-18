package myatm;

public class NotEnoughMoneyInATM extends Exception {
	public NotEnoughMoneyInATM (String messadge){
		super (messadge);
	}
	
	public String toString(){
        return "NotEnoughMoneyinATM";
    }

}
