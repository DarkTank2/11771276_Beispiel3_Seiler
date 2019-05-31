/**
 * Filename: CashRegisterFactory.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 31 May 2019
 */
package cashregister;

/**
 * @author darkt
 *
 */
public class CashRegisterFactory {

	private static long CASH_REGISTER_ID;
	
	public CashRegisterFactory() {
		CashRegisterFactory.CASH_REGISTER_ID = 0;
	}
	
	public static ICashRegister createCashRegister() {
		return new CashRegister(++CashRegisterFactory.CASH_REGISTER_ID);
	}
}
