package myatm;


public class ATM {
	private double moneyInATM;
	
	//Account myAccount = (Account) myAcc;
	
	//ћожно задавать количество денег в банкомате 
	ATM(double moneyInATM){
		this.moneyInATM = moneyInATM;
	}

	public double getMoneyInATM() {
		return this.moneyInATM;
	}


	//— вызова данного метода начинаетс€ работа с картой
	//ћетод принимает карту и пин-код, провер€ет пин-код карты и не заблокирована ли она
	//≈сли неправильный пин-код или карточка заблокирована, возвращаем false. ѕри этом, вызов всех последующих методов у ATM с данной картой должен генерировать исключение NoCardInserted
	public boolean validateCard(Card card, int pinCode){	
		Card mycard = (Card)card;
		if (mycard.checkPin(pinCode) == false || mycard.isBlocked() == true ){
			return false;
		} else 
		return true;
	}

	//¬озвращает сколько денег есть на счету
	public double checkBalance(Card card, int pinCode) throws NoCardInsertedExeption {
		Card mycard = (Card)card;
		if (validateCard(mycard, pinCode) == false){		
				throw new NoCardInsertedExeption();		
		} 
			return mycard.getAccount().getBalance();
		} 
		
		
	

	//ћетод дл€ сн€ти€ указанной суммы
	//ћетод возвращает сумму, котора€ у клиента осталась на счету после сн€ти€
	// роме проверки счета, метод так же должен провер€ть достаточно ли денег в самом банкомате
	//≈сли недостаточно денег на счете, то должно генерироватьс€ исключение NotEnoughMoneyInAccount 
	//≈сли недостаточно денег в банкомате, то должно генерироватьс€ исключение NotEnoughMoneyInATM 
	//ѕри успешном сн€тии денег, указанна€ сумма должна списыватьс€ со счета, и в банкомате должно уменьшатьс€ количество денег
	public double getCash(Card card, int pinCode, double amount) throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount{
		
		Card mycard = (Card)card;
		

		if (validateCard(mycard, pinCode) == false){
			throw new NoCardInsertedExeption ("NoCardInserted");
		}

		if (moneyInATM < amount){
			throw new NotEnoughMoneyInATM ("NotEnoughMoneyInATM");
		}

		if (mycard.getAccount().getBalance() < amount){
			throw new NotEnoughMoneyInAccount ("NotEnoughMoneyInAccount");
		}
		
		mycard.getAccount().withdrow(amount);
		moneyInATM -= amount;
		
		return mycard.getAccount().getBalance();

	}
}
