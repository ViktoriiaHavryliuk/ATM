package myatm;

public class MyATM {

    public static void main(String[] args) throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        Card card = null;
        int pinCode = 1234;
        atm.validateCard(card, pinCode);
        atm.checkBalance();
        atm.getCash(999.99);        
    }
}
