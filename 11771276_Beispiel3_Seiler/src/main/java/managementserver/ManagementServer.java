/**
 * Filename: ManagementServer.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 26.05.2019
 */
package managementserver;

import java.util.Collection;

import cashregister.ICashRegister;
import cashregister.IObserver;
import cashregister.NotRegisteredException;
import rbvs.product.IProduct;
import tree.ITree;
import tree.GenericTree;
import container.Container;
import tree.node.*;
import rbvs.product.*;

public class ManagementServer implements ISubjectManagementServer, IManagementServer {

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
	
	/* (non-Javadoc)
	 * @see managementserver.ISubject#getChanges()
	 */
	@Override
	public ITree<IProduct> getChanges() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see managementserver.ISubject#register(cashregister.IObserver)
	 */
	@Override
	public boolean register(IObserver obs) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see managementserver.ISubject#unregister(cashregister.IObserver)
	 */
	@Override
	public boolean unregister(IObserver obs) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#addCashRegister(cashregister.ICashRegister)
	 */
	@Override
	public void addCashRegister(ICashRegister cashRegister) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#propagateProducts()
	 */
	@Override
	public void propagateProducts() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#retrieveProductSortiment()
	 */
	@Override
	public ITree<IProduct> retrieveProductSortiment() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see managementserver.IManagementServer#retrieveRegisteredCashRegisters()
	 */
	@Override
	public Collection<ICashRegister> retrieveRegisteredCashRegisters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICashRegister retrieveRegisteredCashRegister(Long cashRegisterId) throws NotRegisteredException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unregisterCashRegister(ICashRegister cashRegister) throws NotRegisteredException {
		// TODO Auto-generated method stub
		
	}

}
