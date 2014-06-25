package myatm;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Random;

import org.junit.Test;

import org.mockito.InOrder;

public class ATMTest {
	double moneyinATM = 1000;						// given
	double amount = 900;							// given
	double inValidamount = 1500;					// given
	double MoneyinAccount =42;						// given
	private Card myCard = mock(Card.class);			// given
	private Account myAccount = mock(Account.class);// given
	ATM atm = new ATM(moneyinATM);					// given

	@Test
	public void testGetMoneyInATM() {
		assertThat(atm.getMoneyInATM(), is(moneyinATM));
	}

	//игнорим входной параметр?
	@Test
	public void ignoreParameterInChackPinMethod(){
		myCard.checkPin(someRandomInt());  // when  
		verify(myCard).checkPin(anyInt()); // then
	}

	private int someRandomInt() {
		return new Random().nextInt();
	}

	// ���������, ��� ��� ����� ������ checkPin
	@Test
	public void testThatValidateCardChekedPin() {
		myCard.checkPin(anyInt());
		verify(myCard).checkPin(anyInt());
	}

	//��������, ��� �������� �������� 1 �����
	@Test
	public void testThatCardValidWhenValidData1() {
		when(myCard.checkPin(anyInt())).thenReturn(true);
		when(myCard.isBlocked()).thenReturn(false);
		assertTrue(atm.validateCard(myCard, anyInt()));

	}

	//��������, ��� �������� �������� 2 �����
	@Test
	public void testThatCardValidWhenValidData2() {
		when(myCard.checkPin(anyInt())).thenReturn(false);
		when(myCard.isBlocked()).thenReturn(false);
		assertFalse(atm.validateCard(myCard, anyInt()));

	}

	//��������, ��� �������� �������� 3 �����
	@Test
	public void testThatCardValidWhenValidData3() {
		when(myCard.checkPin(anyInt())).thenReturn(false);
		when(myCard.isBlocked()).thenReturn(true);
		assertFalse(atm.validateCard(myCard, anyInt()));

	}

	//��������, ��� �������� �������� 4 �����
	@Test
	public void testThatCardValidWhenValidData4() {
		when(myCard.checkPin(anyInt())).thenReturn(true);
		when(myCard.isBlocked()).thenReturn(true);
		assertFalse(atm.validateCard(myCard, anyInt()));

	}

	// ��������� ���������� "���������� ��������" in CheckBalance Method
	@Test(expected=NoCardInsertedExeption.class)
	public void testExpectNoCardInsertedExeptionInCheckBalance() {			
		when(atm.validateCard(myCard, anyInt())).thenReturn(false);
		atm.checkBalance();
	}

	// ��������� ������ CheckBalance Method � �������� ���������
	@Test
	public void testCheckBalanceWithValidCardThatReturnBalanceOnAccount(){			
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		when(myCard.getAccount()).thenReturn(myAccount);
		when(myAccount.getBalance()).thenReturn(MoneyinAccount);
		assertThat(atm.checkBalance(), is(MoneyinAccount));
	}

	// ��������� ���������� "������������ ������� � ���������" ��� �������� ��������
	@Test(expected=NotEnoughMoneyInATM.class)
	public void testExpectNotEnoughMoneyInATMExeptionInGetCash() {
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		atm.getCash(inValidamount);
	}

	// ��������� ���������� "���������� ��������" in GetCash Method

	@Test(expected=NoCardInsertedExeption.class)
	public void testThatExpectNoCardInsertedExeptionInGetCash(){
		when(atm.validateCard(myCard, anyInt())).thenReturn(false);	// when
		atm.getCash(inValidamount);						
	}

	// ��������� ���������� "������������ ������� �� ��������"
	@Test(expected=NotEnoughMoneyInAccount.class)
	public void testExpectNotEnoughMoneyInAccountExeptionInGetCash() {
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		when(myCard.getAccount()).thenReturn(myAccount);
		when(myAccount.getBalance()).thenReturn(MoneyinAccount);
		atm.getCash(amount);
	}

	// ������ ������ getCash ��� �������� ��������, ����������� ���������� ����� �� ����� � � ���������, � ��������� ���������� ������ ����� �� ������ �����
	@Test
	public void testGetCashWithAllValidParametrsVerifyThatMoneyInAccountBecomeLessThenSpecifiedAmount() {
		double NewAmount = 40;  
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		when(myCard.getAccount()).thenReturn(myAccount);
		when(myAccount.getBalance()).thenReturn(MoneyinAccount);
		atm.getCash(NewAmount);
		double NewmoneyinATM = moneyinATM - NewAmount;
		assertThat(atm.getMoneyInATM(), is(NewmoneyinATM));
	}

	// ������ ������ getCash ��� �������� ��������, ����������� ���������� ����� �� ����� � � ���������, ������� ����������� �������� ����� �� ����������, ����� ��������� ������
	@Test
	public void testGetCashCallsMethodInCorrectOrder() {
		double NewAmount = 40;  
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		when(myCard.getAccount()).thenReturn(myAccount);
		when(myAccount.getBalance()).thenReturn(MoneyinAccount);
		atm.getCash(NewAmount);		  
		InOrder order = inOrder(myCard,myAccount);
		order.verify(myCard, atLeastOnce()).checkPin(anyInt());
		order.verify(myCard, atLeastOnce()).isBlocked();
		order.verify(myAccount, times(1)).withdrow(anyDouble());

	}

}
