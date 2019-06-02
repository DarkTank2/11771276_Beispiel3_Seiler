/**
 * Filename: ManagementServer.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 26.05.2019
 */
package managementserver;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import cashregister.ICashRegister;
import cashregister.IObserver;
import cashregister.NotRegisteredException;
import tree.ITree;
import tree.GenericTree;
import container.Container;
import tree.node.*;
import util.searchable.ProductCategoryNodeFilter;
import util.searchable.ProductNameFilter;
import warehouse.IWarehouseListener;
import rbvs.product.*;

public class ManagementServer implements ISubjectManagementServer, IManagementServer, IWarehouseListener {

	private static ManagementServer INSTANCE = null;
	private Collection<ICashRegister> cashRegisters;
	private Collection<IObserver> observer;
	private ITree<IProduct> productAssortment;
	
	private ManagementServer () {
		if (INSTANCE != null) return;
		cashRegisters = new Container<ICashRegister>();
		observer = new Container<IObserver>();
		productAssortment = new GenericTree<IProduct>();
		productAssortment.setRoot(new CategoryTreeNode<IProduct, String>("Products"));
		productAssortment.getRoot().getChildren().add(new ProductCategoryTreeNode<IProduct>(ProductCategory.FOOD));
		productAssortment.getRoot().getChildren().add(new ProductCategoryTreeNode<IProduct>(ProductCategory.BEVERAGE));
		productAssortment.getRoot().getChildren().add(new ProductCategoryTreeNode<IProduct>(ProductCategory.DEFAULT));
		INSTANCE = this;
	}
	
	public static IManagementServer GET_INSTANCE() {
		if (INSTANCE == null) new ManagementServer();
		return INSTANCE;
	}
	
	public void addCashRegister​(ICashRegister cashRegister) {
		if (cashRegister == null) return;
		this.cashRegisters.add(cashRegister);
		if (cashRegister instanceof IObserver) {
			((IObserver) cashRegister).activateNotifications(this);
			((IObserver) cashRegister).notifyChange(this);
			if (this.observer.contains((IObserver) cashRegister)) return;
			this.observer.add((IObserver) cashRegister);
		}
	}
	
	/* (non-Javadoc)
	 * @see managementserver.ISubject#getChanges()
	 */
	@Override
	public ITree<IProduct> getChanges() {
		// TODO Auto-generated method stub
		return this.productAssortment.deepCopy();
	}

	/* (non-Javadoc)
	 * @see managementserver.ISubject#register(cashregister.IObserver)
	 */
	@Override
	public boolean register(IObserver obs) {
//		see flowchart of Notification_IObserver.jpg
		if (obs == null) return false;
		// TODO Auto-generated method stub
		obs.activateNotifications(this);
		obs.notifyChange(this);
		// if (!this.observer.contains(obs)) this.observer.add(obs);
//		nifty little line, used conditional allocation
		return this.observer.contains(obs) ? false : this.observer.add(obs);
	}

	/* (non-Javadoc)
	 * @see managementserver.ISubject#unregister(cashregister.IObserver)
	 */
	@Override
	public boolean unregister(IObserver obs) {
//		see flowchart of Notification_IObserver.jpg
		if (obs == null) return false;
		// TODO Auto-generated method stub
		obs.deactivateNotifications(this);
		return this.observer.contains(obs) ? this.observer.remove(obs) : false;
	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#addCashRegister(cashregister.ICashRegister)
	 */
	@Override
	public void addCashRegister(ICashRegister cashRegister) {
		if (cashRegister == null) return;
		// TODO Auto-generated method stub
		this.cashRegisters.add(cashRegister);
		if (cashRegister instanceof IObserver) {
			this.observer.add((IObserver) cashRegister);
			((IObserver) cashRegister).activateNotifications(this);
			((IObserver) cashRegister).notifyChange(this);
		}
	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#propagateProducts()
	 */
	@Override
	public void propagateProducts() {
		// TODO Auto-generated method stub
		this.observer.forEach(obs -> obs.notifyChange(this));
	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#retrieveProductSortiment()
	 */
	@Override
	public ITree<IProduct> retrieveProductSortiment() {
		// TODO Auto-generated method stub
		return this.productAssortment.deepCopy();
	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#retrieveRegisteredCashRegisters()
	 */
	@Override
	public Collection<ICashRegister> retrieveRegisteredCashRegisters() {
		// TODO Auto-generated method stub
		return this.cashRegisters;
	}

	@Override
	public ICashRegister retrieveRegisteredCashRegister(Long cashRegisterId) throws NotRegisteredException {
		// TODO Auto-generated method stub
		List<ICashRegister> l = this.cashRegisters
				.stream()
				.filter(el -> el.getID() == cashRegisterId)
				.collect(Collectors.toList());
		if (l.size() == 0) throw new NotRegisteredException("Cash-Register with ID '" + cashRegisterId + "' is not registered at the Management-Server!");
		return l.get(0);
	}

	@Override
	public void unregisterCashRegister(ICashRegister cashRegister) throws NotRegisteredException {
		// TODO Auto-generated method stub
		if (cashRegister == null) return;
		if (!this.cashRegisters.contains(cashRegister)) throw new NotRegisteredException("CashRegister is not registered!");
		this.cashRegisters.remove(cashRegister);
		if (cashRegister instanceof IObserver) {
			IObserver tmp = (IObserver) cashRegister;
			if (this.observer.contains(tmp)) {
				this.observer.remove(tmp);
				tmp.deactivateNotifications(this);
			}
		}
	}

	@Override
	public void notifyChange(IProduct product) {
		if (product == null) return;
		// TODO Auto-generated method stub
		// strategy:
			// find all nodes as a collection (method to return a collection of nodes, recurseively)
			// update the node
			// if product has changed category, delete it from the direct child of the category node and add it to the new category
			// if the product is a composite-product, check its children against the existing node's children and also update them if neccessary
		IProduct tmp = this.productAssortment.findNode(product).nodeValue();
		if (product.getCategory() != tmp.getCategory()) {
			// shift product from immediate child to the right category
			
			// remove from the current category
			switch (tmp.getCategory()) {
			case FOOD:
				this.productAssortment
					.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.FOOD)
					.iterator()
					.next()
					.removeNodeByValue(product);
				break;
			case BEVERAGE:
				this.productAssortment
					.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.BEVERAGE)
					.iterator()
					.next()
					.removeNodeByValue(product);
				break;
			case DEFAULT:
				this.productAssortment
					.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.DEFAULT)
					.iterator()
					.next()
					.removeNodeByValue(product);
				break;
			default:
				break;
			}
			
			// add to new category
			switch (product.getCategory()) {
			case FOOD:
				this.productAssortment
					.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.FOOD)
					.iterator()
					.next()
					.getChildren()
					.add(new ProductTreeNode(product));
				break;
			case BEVERAGE:
				this.productAssortment
					.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.BEVERAGE)
					.iterator()
					.next()
					.getChildren()
					.add(new ProductTreeNode(product));
				break;
			default:
				this.productAssortment
					.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.DEFAULT)
					.iterator()
					.next()
					.getChildren()
					.add(new ProductTreeNode(product));
				break;
			}
		}
		
		// get collection of all matching nodes
		Collection<ITreeNode<IProduct>> l = this.productAssortment.getRoot().searchByFilter(new ProductNameFilter(), product);
		l.forEach(el -> {
			// update each product
			el.nodeValue().update(product);
			// if the product is a CompositeProduct
			if (product instanceof CompositeProduct) {
				((CompositeProduct) product).getProducts().forEach(prod -> {
					// check each product contained in the compositeproduct passed to exist in each node
					if (!((CompositeProduct) el.nodeValue()).getProducts().contains(prod)) {
						((CompositeProduct) el.nodeValue()).getProducts().add(prod);
					}
				});
			}
		});
	}

	@Override
	public void productAdded(IProduct product) {
		if (product == null) return;
		// TODO Auto-generated method stub
		switch (product.getCategory()) {
		case FOOD:
			this.productAssortment
				.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.FOOD)
				.iterator()
				.next()
				.getChildren()
				.add(new ProductTreeNode(product));
			break;
		case BEVERAGE:
			this.productAssortment
				.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.BEVERAGE)
				.iterator()
				.next()
				.getChildren()
				.add(new ProductTreeNode(product));
			break;
		default:
			this.productAssortment
				.searchByFilter(new ProductCategoryNodeFilter(), ProductCategory.DEFAULT)
				.iterator()
				.next()
				.getChildren()
				.add(new ProductTreeNode(product));
			break;
		
		}
	}

	@Override
	public void productRemoved(IProduct product) {
		if (product == null) return;
		// TODO Auto-generated method stub
		this.productAssortment.removeNode(product);
//		ensure that all products that match the passed product are deleted
		if (this.productAssortment.findNode(product) != null) this.productRemoved(product);
		return;
	}

}
