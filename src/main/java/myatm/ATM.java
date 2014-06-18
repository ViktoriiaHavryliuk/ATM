package myatm;


public class ATM {
	private double moneyInATM;
	
	//Account myAccount = (Account) myAcc;
	
	//����� �������� ���������� ����� � ��������� 
	ATM(double moneyInATM){
		this.moneyInATM = moneyInATM;
	}

	public double getMoneyInATM() {
		return this.moneyInATM;
	}


	//� ������ ������� ������ ���������� ������ � ������
	//����� ��������� ����� � ���-���, ��������� ���-��� ����� � �� ������������� �� ���
	//���� ������������ ���-��� ��� �������� �������������, ���������� false. ��� ����, ����� ���� ����������� ������� � ATM � ������ ������ ������ ������������ ���������� NoCardInserted
	public boolean validateCard(Card card, int pinCode){	
		Card mycard = (Card)card;
		if (mycard.checkPin(pinCode) == false || mycard.isBlocked() == true ){
			return false;
		} else 
		return true;
	}

	//���������� ������� ����� ���� �� �����
	public double checkBalance(Card card, int pinCode) throws NoCardInsertedExeption {
		Card mycard = (Card)card;
		if (validateCard(mycard, pinCode) == false){		
				throw new NoCardInsertedExeption();		
		} 
			return mycard.getAccount().getBalance();
		} 
		
		
	

	//����� ��� ������ ��������� �����
	//����� ���������� �����, ������� � ������� �������� �� ����� ����� ������
	//����� �������� �����, ����� ��� �� ������ ��������� ���������� �� ����� � ����� ���������
	//���� ������������ ����� �� �����, �� ������ �������������� ���������� NotEnoughMoneyInAccount 
	//���� ������������ ����� � ���������, �� ������ �������������� ���������� NotEnoughMoneyInATM 
	//��� �������� ������ �����, ��������� ����� ������ ����������� �� �����, � � ��������� ������ ����������� ���������� �����
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
