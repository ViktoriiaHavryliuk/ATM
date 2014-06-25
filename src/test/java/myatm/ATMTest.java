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

	// проверяем, что был вызов метода checkPin
	@Test
	public void testThatValidateCardChekedPin() {
		myCard.checkPin(anyInt());
		verify(myCard).checkPin(anyInt());
	}

	//проверка, что карточка валидная 1 ветка
	@Test
	public void testThatCardValidWhenValidData1() {
		when(myCard.checkPin(anyInt())).thenReturn(true);
		when(myCard.isBlocked()).thenReturn(false);
		assertTrue(atm.validateCard(myCard, anyInt()));

	}

	//проверка, что карточка валидная 2 ветка
	@Test
	public void testThatCardValidWhenValidData2() {
		when(myCard.checkPin(anyInt())).thenReturn(false);
		when(myCard.isBlocked()).thenReturn(false);
		assertFalse(atm.validateCard(myCard, anyInt()));

	}

	//проверка, что карточка валидная 3 ветка
	@Test
	public void testThatCardValidWhenValidData3() {
		when(myCard.checkPin(anyInt())).thenReturn(false);
		when(myCard.isBlocked()).thenReturn(true);
		assertFalse(atm.validateCard(myCard, anyInt()));

	}

	//проверка, что карточка валидная 4 ветка
	@Test
	public void testThatCardValidWhenValidData4() {
		when(myCard.checkPin(anyInt())).thenReturn(true);
		when(myCard.isBlocked()).thenReturn(true);
		assertFalse(atm.validateCard(myCard, anyInt()));

	}

	//  проверяем исключение "невалидная карточка" in chechBalance Method
	@Test(expected=NoCardInsertedExeption.class)
	public void testExpectNoCardInsertedExeptionInCheckBalance() {			
		when(atm.validateCard(myCard, anyInt())).thenReturn(false);
		atm.checkBalance();
	}

	// проверяем работу CheckBalance Method с валидной карточкой
	@Test
	public void testCheckBalanceWithValidCardThatReturnBalanceOnAccount(){			
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		when(myCard.getAccount()).thenReturn(myAccount);
		when(myAccount.getBalance()).thenReturn(MoneyinAccount);
		assertThat(atm.checkBalance(), is(MoneyinAccount));
	}

	//  проверяем исключение "недостаточно средств в банкомате" при валидной карточке
	@Test(expected=NotEnoughMoneyInATM.class)
	public void testExpectNotEnoughMoneyInATMExeptionInGetCash() {
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		atm.getCash(inValidamount);
	}

	// проверяем исключение "невалидная карточка" in GetCash Method

	@Test(expected=NoCardInsertedExeption.class)
	public void testThatExpectNoCardInsertedExeptionInGetCash(){
		when(atm.validateCard(myCard, anyInt())).thenReturn(false);	// when
		atm.getCash(inValidamount);						
	}

	// проверяем исключение "недостаточно средств на карточке"
	@Test(expected=NotEnoughMoneyInAccount.class)
	public void testExpectNotEnoughMoneyInAccountExeptionInGetCash() {
		when(atm.validateCard(myCard, anyInt())).thenReturn(true);
		when(myCard.getAccount()).thenReturn(myAccount);
		when(myAccount.getBalance()).thenReturn(MoneyinAccount);
		atm.getCash(amount);
	}

	// работа метода getCash при валидной карточке, достаточном количестве денег на счету и в банкомате,
	//в банкомате становится меньше денег на снятую сумму
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

	//  работа метода getCash при валидной карточке, достаточном количестве денег на счету и в банкомате,
	//сначала выполняется проверка карты на валидность, потом снимаются деньги
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
