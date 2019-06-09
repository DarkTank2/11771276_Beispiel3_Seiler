/**
 * Filename: ShoppingCartPOJO.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 09.06.2019
 */
package xml;

import java.util.Collection;

import javax.xml.bind.annotation.*;

import container.Container;
import rbvs.product.IShoppingCartElement;

@XmlRootElement
public class ShoppingCartPOJO {

	@XmlAttribute(name = "CartIdentifier")
	private Long id;
	
	@XmlElement(name = "CurrentProducts")
	private Collection<ProductPOJO> shoppingCartProducts;

	
	/**
	 * Constructor for class ShoppingCartPOJO.java
	 * @author Alexander Seiler, 11771276
	 */
	public ShoppingCartPOJO() {
	}

	/**
	 * Constructor for class ShoppingCartPOJO.java
	 * @author Alexander Seiler, 11771276
	 * @param id
	 * @param shoppingCartProducts
	 */
	public ShoppingCartPOJO(Long id, Collection<IShoppingCartElement> shoppingCartProducts) {
		this.id = id;
		this.shoppingCartProducts = new Container<ProductPOJO>();
		shoppingCartProducts.forEach(el -> this.shoppingCartProducts.add(new ProductPOJO(el)));
//		this.shoppingCartProducts = shoppingCartProducts;
	}

	/**
	 * Getter-method for id
	 * @author Alexander Seiler, 11771276
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Getter-method for shoppingCartProducts
	 * @author Alexander Seiler, 11771276
	 * @return the shoppingCartProducts
	 */
	public Collection<ProductPOJO> getShoppingCartProducts() {
		return this.shoppingCartProducts;
	}
	
	
}
