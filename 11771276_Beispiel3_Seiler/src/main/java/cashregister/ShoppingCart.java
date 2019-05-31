/**
 * Filename: ShoppingCart.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 31 May 2019
 */
package cashregister;

import java.util.Collection;

import container.Container;
import rbvs.product.IShoppingCartElement;

/**
 * @author darkt
 *
 */
public class ShoppingCart implements IShoppingCart {

	
	private long id;
	private Collection<IShoppingCartElement> shoppingCartElements = null;
	
	/**
	 * @author: darkt
	 * @description: Constructor for class ShoppingCart.java
	 * @param id
	 */
	public ShoppingCart(long id) {
		this.id = id;
		this.shoppingCartElements = new Container<IShoppingCartElement>();
	}

	/* (non-Javadoc)
	 * @see cashregister.IShoppingCart#addElement(rbvs.product.IShoppingCartElement)
	 */
	@Override
	public void addElement(IShoppingCartElement arg0) {
		if (arg0 == null) return;
		this.shoppingCartElements.add(arg0);
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see cashregister.IShoppingCart#currentElements()
	 */
	@Override
	public Collection<IShoppingCartElement> currentElements() {
		// TODO Auto-generated method stub
		return this.shoppingCartElements;
	}

	/* (non-Javadoc)
	 * @see cashregister.IShoppingCart#getNumberOfElements()
	 */
	@Override
	public int getNumberOfElements() {
		// TODO Auto-generated method stub
		return this.shoppingCartElements.size();
	}

	/* (non-Javadoc)
	 * @see cashregister.IShoppingCart#getShoppingCartID()
	 */
	@Override
	public Long getShoppingCartID() {
		// TODO Auto-generated method stub
		return this.id;
	}

	/* (non-Javadoc)
	 * @see cashregister.IShoppingCart#getTotalPriceOfElements()
	 */
	@Override
	public float getTotalPriceOfElements() {
		// TODO Auto-generated method stub
		return (float) this.shoppingCartElements.stream().mapToDouble(el -> el.getPrice()).sum();
	}

	/* (non-Javadoc)
	 * @see cashregister.IShoppingCart#removeElement(rbvs.product.IShoppingCartElement)
	 */
	@Override
	public boolean removeElement(IShoppingCartElement arg0) {
		if (arg0 == null) return false;
		// TODO Auto-generated method stub
		if (!this.shoppingCartElements.contains(arg0)) return false;
		return this.shoppingCartElements.remove(arg0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShoppingCart [id=" + this.id + ", shoppingCartElements=" + this.shoppingCartElements.toString() + "]";
	}
	

}
