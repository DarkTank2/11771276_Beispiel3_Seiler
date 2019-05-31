/**
 * Filename: CashRegister.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 31 May 2019
 */
package cashregister;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import cashregister.ui.ICashRegisterUI;
import container.Container;
import managementserver.ISubjectManagementServer;
import paymentprovider.IPayment;
import rbvs.product.IProduct;
import rbvs.product.IShoppingCartElement;
import rbvs.product.Product;
import rbvs.product.SimpleProduct;
import rbvs.record.IInvoice;
import rbvs.record.Invoice;
import rbvs.record.PaymentTransaction;
import tree.ITree;
import tree.ProductTree;
import tree.node.ITreeNode;
import util.Tuple;
import util.searchable.ProductNameFilter;

/**
 * @author darkt
 *
 */
public class CashRegister implements IObserver, ICashRegister {

	private Collection<IShoppingCart> shoppingCarts;
	private Collection<IInvoice> records;
	private Collection<ICashRegisterUI> uis;
	private Collection<Tuple<ISubjectManagementServer, Boolean>> subjects; // ToDo: check what generics Tuple needs
	
	private long id;
	private ITree<IProduct> products;
	
	private long counter;
	
	/**
	 * @author: darkt
	 * @description: Constructor for class CashRegister.java
	 * @param id
	 */
	public CashRegister(long id) {
		this.id = id;
		this.shoppingCarts = new Container<IShoppingCart>();
		this.records = new Container<IInvoice>();
		this.uis = new Container<ICashRegisterUI>();
		this.subjects = new Container<Tuple<ISubjectManagementServer, Boolean>>(); // ToDo: check generics for Tuple
		this.products = new ProductTree();
		
		this.counter = 0;
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#addProductToShoppingCart(long, rbvs.product.IShoppingCartElement)
	 */
	@Override
	public boolean addProductToShoppingCart(long arg0, IShoppingCartElement arg1) {
		if (arg1 == null) return false;
		Collection<IShoppingCart> l = this.shoppingCarts.stream()
				.filter(el -> el.getShoppingCartID() == arg0)
				.collect(Collectors.toList());
		if (l.size() == 0) return false;
//		I do not know if it is possible to get more than one element here since the id must be unique
//		but just in case there are more than one shopping carts with this id, ad the element to all of them
		l.stream().forEach(el -> el.addElement(arg1.deepCopy()));
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#addShoppingCart()
	 */
	@Override
	public Long addShoppingCart() {
		// TODO Auto-generated method stub
		IShoppingCart tmp = new ShoppingCart(counter);
		++counter;
		this.shoppingCarts.add(tmp);
		return tmp.getShoppingCartID();
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#displayProducts()
	 */
	@Override
	public void displayProducts() {
		// TODO Auto-generated method stub
		this.uis.forEach(el -> el.displayProducts(this.products));

	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#displayRecords()
	 */
	@Override
	public void displayRecords() {
		// TODO Auto-generated method stub
		this.uis.forEach(el -> el.displayRecords(this.records));
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#displayShoppingCart(long)
	 */
	@Override
	public void displayShoppingCart(long id) {
		// TODO Auto-generated method stub
		IShoppingCart crt = this.shoppingCarts.stream().filter(el -> el.getShoppingCartID() == id).collect(Collectors.toList()).get(0);
		if (crt == null) return;
		this.uis.forEach(el -> el.displayShoppingCart(crt));
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#displayShoppingCarts()
	 */
	@Override
	public void displayShoppingCarts() {
		// TODO Auto-generated method stub
		this.uis.forEach(el -> el.displayShoppingCarts(this.shoppingCarts));
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#getID()
	 */
	@Override
	public long getID() {
		// TODO Auto-generated method stub
		return this.id;
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#getShoppingCarts()
	 */
	@Override
	public Collection<IShoppingCart> getShoppingCarts() {
		// TODO Auto-generated method stub
		return this.shoppingCarts;
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#payShoppingCart(long, paymentprovider.IPayment)
	 */
	@Override
	public IInvoice payShoppingCart(long arg0, IPayment arg1) throws ShoppingCartNotFoundException {
		// TODO Auto-generated method stub
		if (arg1 == null) return null;
		List<IShoppingCart> l = this.shoppingCarts
				.stream()
				.filter(el -> el.getShoppingCartID() == arg0)
				.collect(Collectors.toList());
		if (l.size() == 0) return null;
		PaymentTransaction transaction = arg1.pay(l.get(0).getTotalPriceOfElements());
		IInvoice inv = new Invoice();
		inv.addPaymentTransaction(transaction);
		inv.setInvoiceProducts(
				l.get(0) // get first element e.g. the shopping cart
				.currentElements() // get all added products from that shopping cart
				.stream()
				.map(el -> el.deepCopy()) // map all the products to their copy
				.collect(Collectors.toList()) // collect them to pass them as a collection of copies
		);
		this.records.add(inv);
		// removes each element from the shopping cart
		l.get(0).currentElements().stream().forEach(el -> l.get(0).removeElement(el));
		return inv;
	}
	
	protected IShoppingCart findShoppingCartById(long id) {
		// this shall work even if there is no cart with given id since the list then shall be empty and so the zero-indexed element will be NULL
		return this.shoppingCarts.stream().filter(el -> el.getShoppingCartID() == id).collect(Collectors.toList()).get(0);
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#registerCashRegisterUI(cashregister.ui.ICashRegisterUI)
	 */
	@Override
	public void registerCashRegisterUI(ICashRegisterUI ui) {
		// TODO Auto-generated method stub
		if (ui == null) return;
		this.uis.add(ui);
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#selectProduct(java.lang.String)
	 */
	@Override
	public IShoppingCartElement selectProduct(String product) {
		if (product == null) return null;
		// TODO Auto-generated method stub
		Collection<ITreeNode<IProduct>> l = this.products.searchByFilter(new ProductNameFilter(), product);
		if (l.size() == 0) return null;
		Iterator<ITreeNode<IProduct>> itr = l.iterator();
		if(itr.hasNext()) return (Product) itr.next().nodeValue();
		return null;
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#selectProduct(rbvs.product.Product)
	 */
	@Override
	public IShoppingCartElement selectProduct(Product product) {
		if (product == null) return null;
		// TODO Auto-generated method stub
		ITreeNode<IProduct> tmp = this.products.findNode(product);
		if (tmp == null) return null;
		return (Product) tmp.nodeValue();
	}

	/* (non-Javadoc)
	 * @see cashregister.ICashRegister#unregisterCashRegisterUI(cashregister.ui.ICashRegisterUI)
	 */
	@Override
	public void unregisterCashRegisterUI(ICashRegisterUI ui) {
		// TODO Auto-generated method stub
		if (ui == null) return;
		this.uis.remove(ui);
	}

	/* (non-Javadoc)
	 * @see cashregister.IObserver#activateNotifications(managementserver.ISubjectManagementServer)
	 */
	@Override
	public void activateNotifications(ISubjectManagementServer subject) {
		if (subject == null) return;
		// TODO Auto-generated method stub
		List<Tuple<ISubjectManagementServer, Boolean>> l = this.subjects
				.stream()
				.filter(el -> el.getValueA().equals(subject))
				.collect(Collectors.toList());
		if (l.size() == 0) return;
		l.get(0).setValueB(true);
	}

	/* (non-Javadoc)
	 * @see cashregister.IObserver#deactivateNotifications(managementserver.ISubjectManagementServer)
	 */
	@Override
	public void deactivateNotifications(ISubjectManagementServer subject) {
		if (subject == null) return;
		// TODO Auto-generated method stub
		List<Tuple<ISubjectManagementServer, Boolean>> l = this.subjects
				.stream()
				.filter(el -> el.getValueA().equals(subject))
				.collect(Collectors.toList());
		if (l.size() == 0) return;
		l.get(0).setValueB(false);
	}

	/* (non-Javadoc)
	 * @see cashregister.IObserver#notifyChange(managementserver.ISubjectManagementServer)
	 */
	@Override
	public void notifyChange(ISubjectManagementServer subject) {
		// TODO Auto-generated method stub
		if (subject == null) return;
		Tuple<ISubjectManagementServer, Boolean> tmp = this.subjects.stream().filter(el -> el.valueA.equals(subject)).collect(Collectors.toList()).get(0);
		if (tmp == null) return;
		if (tmp.getValueB().booleanValue()) this.products = subject.getChanges(); // not sure if this is the right way since the tree gets overwritten by the changes and not adapted
	}

}
