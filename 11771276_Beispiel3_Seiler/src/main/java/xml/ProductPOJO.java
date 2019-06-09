/**
 * Filename: ProductPOJO.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 09.06.2019
 */
package xml;

import javax.xml.bind.annotation.*;

import rbvs.product.IShoppingCartElement;

@XmlRootElement
public class ProductPOJO {

	@XmlAttribute(name = "productName")
	private String name;

	
	/**
	 * Constructor for class ProductPOJO.java
	 * @author Alexander Seiler, 11771276
	 */
	public ProductPOJO() {
	}

	/**
	 * Constructor for class ProductPOJO.java
	 * @author Alexander Seiler, 11771276
	 * @param name
	 */
	public ProductPOJO(IShoppingCartElement product) {
		this.name = product.getName();
	}

	/**
	 * Getter-method for name
	 * @author Alexander Seiler, 11771276
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	
	
}
