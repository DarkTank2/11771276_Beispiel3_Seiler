/**
 * Filename: ShoppingCartNotFoundException.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 31 May 2019
 */
package cashregister;

/**
 * @author darkt
 *
 */
public class ShoppingCartNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @author: darkt
	 * @description: Constructor for class ShoppingCartNotFoundException.java
	 * @param message
	 */
	public ShoppingCartNotFoundException(long cashRegisterId, long shoppingCartId) {
		super("Shopping-cart with ID '" + shoppingCartId + "' not found in cash-register with ID '" + cashRegisterId + "'!");
		// TODO Auto-generated constructor stub
	}
	
	

}
