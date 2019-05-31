/**
 * Filename: ShoppingCartFactory.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 31 May 2019
 */
package cashregister;

/**
 * @author darkt
 *
 */
public class ShoppingCartFactory {

	private static long SHOPPING_CART_ID;

	/**
	 * @author: darkt
	 * @description: Constructor for class ShoppingCartFactory.java
	 * @param sHOPPING_CART_ID
	 */
	public ShoppingCartFactory() {
		ShoppingCartFactory.SHOPPING_CART_ID = 0;
	}
	
	public static IShoppingCart createShoppingCart() {
		return new ShoppingCart(++ShoppingCartFactory.SHOPPING_CART_ID);
	}
	
	
	public static IShoppingCart createShoppingCart(long id) {
//		compare with documentation, makes no sense to me but ok
		if (id < ShoppingCartFactory.SHOPPING_CART_ID) ShoppingCartFactory.SHOPPING_CART_ID = id + 1;
		return new ShoppingCart(id);
	}
	
}
