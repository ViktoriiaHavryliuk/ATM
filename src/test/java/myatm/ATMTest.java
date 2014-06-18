package myatm;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.mockito.verification.VerificationMode;

public class ATMTest {
	double moneyinATM = 1000;						// given
	double amount = 900;							// given
	double inValidamount = 1500;					// given
	double MoneyinAccount =42;						// given
	private Card MyCard = mock(Card.class);			// given
	private Account myAccount = mock(Account.class);// given
	ATM atm = new ATM(moneyinATM);					// given

	@Test
	public void testGetMoneyInATM() {
		assertThat(atm.getMoneyInATM(), is(moneyinATM));
	}

	//надо проигнорить входной параметр?
	@Test
	public void ignoreParameterInChackPinMethod(){
		MyCard.checkPin(someRandomInt());  // when  

		verify(MyCard).checkPin(anyInt()); // then
	}

	private int someRandomInt() {
		return new Random().nextInt();
	}

	// проверяем, что был вызов метода checkPin
	@Test
	public void testThatValidateCardChekedPin() {
		MyCard.checkPin(anyInt());
		verify(MyCard).checkPin(anyInt());
	}

	//проверка, что карточка валидная 1 ветка
	@Test
	public void testThatCardValidWhenValidData1() {
		when(MyCard.checkPin(anyInt())).thenReturn(true);
		when(MyCard.isBlocked()).thenReturn(false);
		assertTrue(atm.validateCard(MyCard, anyInt()));

	}

	//проверка, что карточка валидная 2 ветка
	@Test
	public void testThatCardValidWhenValidData2() {
		when(MyCard.checkPin(anyInt())).thenReturn(false);
		when(MyCard.isBlocked()).thenReturn(false);
		assertFalse(atm.validateCard(MyCard, anyInt()));

	}

	//проверка, что карточка валидная 3 ветка
	@Test
	public void testThatCardValidWhenValidData3() {
		when(MyCard.checkPin(anyInt())).thenReturn(false);
		when(MyCard.isBlocked()).thenReturn(true);
		assertFalse(atm.validateCard(MyCard, anyInt()));

	}

	//проверка, что карточка валидная 4 ветка
	@Test
	public void testThatCardValidWhenValidData4() {
		when(MyCard.checkPin(anyInt())).thenReturn(true);
		when(MyCard.isBlocked()).thenReturn(true);
		assertFalse(atm.validateCard(MyCard, anyInt()));

	}

	// проверяем исключение "невалидная карточка" in CheckBalance Method
	@Test(expected=NoCardInsertedExeption.class)
	public void testExpectNoCardInsertedExeptionInCheckBalance() throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {			
		when(atm.validateCard(MyCard, anyInt())).thenReturn(false);
		atm.checkBalance(MyCard, anyInt());
	}

	// проверяем работу CheckBalance Method с валидной карточкой
	@Test
	public void testCheckBalanceWithValidCardThatReturnBalanceOnAccount() throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {			
		when(atm.validateCard(MyCard, anyInt())).thenReturn(true);
		when(MyCard.getAccount()).thenReturn(myAccount);
		when(myAccount.getBalance()).thenReturn(MoneyinAccount);
		assertThat(atm.checkBalance(MyCard, anyInt()), is(MoneyinAccount));
	}
	
	// проверяем исключение "недостаточно средств в банкомате" при валидной карточке
		@Test(expected=NotEnoughMoneyInATM.class)
		public void testExpectNotEnoughMoneyInATMExeptionInGetCash() 
				throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
			when(atm.validateCard(MyCard, anyInt())).thenReturn(true);
			atm.getCash(MyCard, anyInt(), inValidamount);
		}
		
		// проверяем исключение "невалидная карточка" in GetCash Method

		@Test(expected=NoCardInsertedExeption.class)
		public void testThatExpectNoCardInsertedExeptionInGetCash() throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
			when(atm.validateCard(MyCard, anyInt())).thenReturn(false);	// when
			atm.getCash(MyCard, anyInt(), inValidamount);						
		}
		
		// проверяем исключение "недостаточно средств на карточке"
		@Test(expected=NotEnoughMoneyInAccount.class)
		public void testExpectNotEnoughMoneyInAccountExeptionInGetCash() 
				throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
			when(atm.validateCard(MyCard, anyInt())).thenReturn(true);
			when(MyCard.getAccount()).thenReturn(myAccount);
			when(myAccount.getBalance()).thenReturn(MoneyinAccount);
			atm.getCash(MyCard, anyInt(), amount);
		}
		
		// работа метода getCash при валидной карточке, достаточном количестве денег на счету и в банкомате
		@Test
		public void testGetCashWithAllValidParametrs() throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
			double NewAmount = 40;		
			when(atm.validateCard(MyCard, anyInt())).thenReturn(true);
			when(MyCard.getAccount()).thenReturn(myAccount);
			when(myAccount.getBalance()).thenReturn(MoneyinAccount);
			//verify(acc, atLeastOnce()).getBalance();
			//verify(myAccount.getBalance(), atLeast(5)).withdraw(NewAmount);
			verify (atm.getCash(MyCard, anyInt(), NewAmount));
		}



	/*


	// ожидаем исключения, карточка невалидная

	@Test//(expected=NoCardInsertedExeption.class)
	public void testThatValidateCardExpectExeption() throws NoCardInsertedExeption {
		double moneyinATM = 1000;
		ATM atm = new ATM(moneyinATM);
		Card MockedInvalidCard = mock(Card.class);					// given
		when(MockedInvalidCard.checkPin(anyInt())).thenReturn(true);	// when
		atm.checkBalance(MockedInvalidCard, anyInt());
		verify(MockedInvalidCard).getAccount();						

	}

	// проверяем исключение "невалидная карточка" in GetCash Method
	@Test(expected=NoCardInsertedExeption.class)
	public void testExpectNoCardInsertedExeptionInGetCash() throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
		double moneyinATM = 1000;
		double amount = 900;
		ATM atm = new ATM(moneyinATM);
		Card MockedInvalidCard = mock(Card.class);
		when(atm.validateCard(MockedInvalidCard, anyInt())).thenReturn(false);
		atm.getCash(MockedInvalidCard, anyInt(), amount);
	}


	// проверяем исключение "недостаточно средств в банкомате"
	@Test(expected=NotEnoughMoneyInATM.class)
	public void testExpectNotEnoughMoneyInATMExeptionInGetCash() 
			throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
		double moneyinATM = 1000;
		double amount = 1100;
		ATM atm = new ATM(moneyinATM);
		Card MockedValidCard = mock(Card.class);
		when(atm.validateCard(MockedValidCard, anyInt())).thenReturn(true);
		atm.getCash(MockedValidCard, anyInt(), amount);
	}

	// проверяем исключение "недостаточно средств на карточке"
	@Test(expected=NotEnoughMoneyInAccount.class)
	public void testExpectNotEnoughMoneyInAccountExeptionInGetCash() 
			throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
		double moneyinATM = 1000;
		double amount = 900;
		double MoneyinAccount =42;
		int pin = 1234;
		ATM atm = new ATM(moneyinATM);
		Card MockedValidCard = mock(Card.class);

		when(atm.validateCard(MockedValidCard, anyInt())).thenReturn(true);
		when(MockedValidCard.getAccount().getBalance()).thenReturn(MoneyinAccount);
		atm.getCash(MockedValidCard, anyInt(), amount);
	}

	// указываем моку что именно он должен вернуть
	@Test
	public void stubParameter(){
		double moneyinATM = 1000;
		int pin = someRandomInt();
		Card MockedCard = mock(Card.class);            // given
		ATM atm = new ATM(moneyinATM);

		when(atm.validateCard(MockedCard, pin)).thenReturn(true); // так
		//stub(foo.foo("qwe")).toReturn("asd"); // или так
		//doReturn("asd").when(foo).foo("qwe"); // или так

		assertEquals(true, MockedCard.checkPin(pin));  // when, then
	}

	//надо проигнорить входной параметр?
	@Test
	public void ignoreParameter(){
		Card MockedCard = mock(Card.class);    // given

		MockedCard.checkPin(someRandomInt());  // when  

		verify(MockedCard).checkPin(anyInt()); // then
	}

	private int someRandomInt() {
		return new Random().nextInt();
	}


	@Test
	public void testCheckBalance() {
		double moneyinATM = 1000;
		double amount = 40;
		double MoneyinAccount =42;
		int pin = 1234;
		ATM atm = new ATM(moneyinATM);
		Card MockedValidCard = mock(Card.class);
		when(MockedValidCard.isBlocked()).thenReturn(false);
		when(MockedValidCard.checkPin(pin)).thenReturn(true);
		when(MockedValidCard.getAccount().getBalance()).thenReturn(MoneyinAccount);
		verify(MockedValidCard.getAccount().getBalance());
		assertThat(MockedValidCard.getAccount().getBalance(), is(MoneyinAccount));
	}

	@Test
	public void testGetCash() throws NoCardInsertedExeption, NotEnoughMoneyInATM, NotEnoughMoneyInAccount {
		double moneyinATM = 1000;
		double amount = 40;
		double MoneyinAccount =42;
		int pin = 1234;
		ATM atm = new ATM(moneyinATM);
		Card MockedValidCard = mock(Card.class);		
		when(MockedValidCard.isBlocked()).thenReturn(false);
		when(MockedValidCard.checkPin(pin)).thenReturn(true);
		when(MockedValidCard.getAccount().getBalance()).thenReturn(MoneyinAccount);
		atm.getCash(MockedValidCard, anyInt(), amount);
	}*/

}
