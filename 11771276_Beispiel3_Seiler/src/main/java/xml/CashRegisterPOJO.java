/**
 * Filename: CashRegisterPOJO.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 09.06.2019
 */
package xml;

import java.util.Collection;

import javax.xml.bind.annotation.*;

import cashregister.IShoppingCart;
import container.Container;

@XmlRootElement
public class CashRegisterPOJO {

	@XmlElement(name = "ShoppingCart")
	private Collection<ShoppingCartPOJO> shoppingCarts;

	
	/**
	 * Constructor for class CashRegisterPOJO.java
	 * @author Alexander Seiler, 11771276
	 */
	public CashRegisterPOJO() {
	}

	/**
	 * Constructor for class CashRegisterPOJO.java
	 * @author Alexander Seiler, 11771276
	 * @param shoppingCarts
	 */
	public CashRegisterPOJO(Collection<IShoppingCart> shoppingCarts) {
		this.shoppingCarts = new Container<ShoppingCartPOJO>();
		shoppingCarts.forEach(el -> this.shoppingCarts.add(new ShoppingCartPOJO(el.getShoppingCartID(), el.currentElements())));
//		this.shoppingCarts = shoppingCarts;
	}

	/**
	 * Getter-method for shoppingCarts
	 * @author Alexander Seiler, 11771276
	 * @return the shoppingCarts
	 */
	public Collection<ShoppingCartPOJO> getShoppingCarts() {
		return this.shoppingCarts;
	}
	
	
}
