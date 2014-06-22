package myatm;


public class ATM {
	private double moneyInATM;
	private Card card;
	private boolean validCard = false;
	private int pinCode;

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
		this.card = card;
		if (card.checkPin(pinCode) == false || card.isBlocked() == true ){
			return validCard;
		} else 
			return validCard = true;
	}

	//���������� ������� ����� ���� �� �����
	public double checkBalance()  {
		if (validateCard(card, pinCode) == false){		
			throw new NoCardInsertedExeption();		
		} 
		return card.getAccount().getBalance();
	} 




	//����� ��� ������ ��������� �����
	//����� ���������� �����, ������� � ������� �������� �� ����� ����� ������
	//����� �������� �����, ����� ��� �� ������ ��������� ���������� �� ����� � ����� ���������
	//���� ������������ ����� �� �����, �� ������ �������������� ���������� NotEnoughMoneyInAccount 
	//���� ������������ ����� � ���������, �� ������ �������������� ���������� NotEnoughMoneyInATM 
	//��� �������� ������ �����, ��������� ����� ������ ����������� �� �����, � � ��������� ������ ����������� ���������� �����
	public double getCash(double amount) {

		if (validateCard(card, pinCode) == false){
			throw new NoCardInsertedExeption ("NoCardInserted");
		}

		if (moneyInATM < amount){
			throw new NotEnoughMoneyInATM ("NotEnoughMoneyInATM");
		}

		if (card.getAccount().getBalance() < amount){
			throw new NotEnoughMoneyInAccount ("NotEnoughMoneyInAccount");
		}

		card.getAccount().withdrow(amount);
		moneyInATM -= amount;

		return card.getAccount().getBalance();

	}
}
