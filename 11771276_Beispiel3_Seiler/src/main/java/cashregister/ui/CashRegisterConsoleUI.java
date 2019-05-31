/**
 * Filename: CashRegisterConsoleUI.java
 * Description: 
 * @author Alexander Seiler, 11771276
 * @since 31 May 2019
 */
package cashregister.ui;

import java.util.Collection;

import cashregister.IShoppingCart;
import rbvs.product.IProduct;
import rbvs.record.IInvoice;
import rbvs.record.PaymentTransaction;
import tree.ITree;

/**
 * @author darkt
 *
 */
public class CashRegisterConsoleUI implements ICashRegisterUI {
	

	/**
	 * @author: darkt
	 * @description: Constructor for class CashRegisterConsoleUI.java
	 */
	public CashRegisterConsoleUI() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see cashregister.ui.ICashRegisterUI#displayProducts(tree.ITree)
	 */
	@Override
	public void displayProducts(ITree<IProduct> arg0) {
		if (arg0 == null) return;
		// TODO Auto-generated method stub
		System.out.println("Displaying products in CashRegisterConsoleUI:");
		System.out.print(arg0.generateConsoleView("   "));
		System.out.println("");
	}

	/* (non-Javadoc)
	 * @see cashregister.ui.ICashRegisterUI#displayRecords(java.util.Collection)
	 */
	@Override
	public void displayRecords(Collection<IInvoice> records) {
		if (records == null) return;
		// TODO Auto-generated method stub
		records.forEach(rec -> {
			System.out.println("| Displaying record in CashRegisterConsoleUI |");
			System.out.println("+--------------------------------------------+");
			System.out.println("|                                             ");
			System.out.println("|  | Displaying ShoppingCartElements |");
			System.out.println("|  +----------------+----------------+");
			System.out.println("|  | Name           | Price          |");
			System.out.println("|  +----------------+----------------+");
			rec.attachedShoppingCardElements().forEach(el -> {
				System.out.println("|  | " + el.getName() + " | " + el.getPrice());
				System.out.println("|  +----------------+----------------+");
			});
			System.out.println("|                                             ");
			System.out.println("+--------------------------------------------+");
			System.out.println("|                                             ");
			this.displayTransaction(rec.getPaymentTransaction(), "|  ");
			System.out.println("|                                             ");
			System.out.println("+--------------------------------------------+");
			System.out.println("");
		});
	}

	/* (non-Javadoc)
	 * @see cashregister.ui.ICashRegisterUI#displayShoppingCart(cashregister.IShoppingCart)
	 */
	@Override
	public void displayShoppingCart(IShoppingCart shoppingCart) {
		if (shoppingCart == null) return;
		// TODO Auto-generated method stub
		System.out.println("| Displaying ShoppingCart in CashRegisterConsoleUI |");
		System.out.println("+--------------+-----------------------------------+");
		System.out.println("| ID           | " + shoppingCart.getShoppingCartID());
		System.out.println("+--------------+-----------------------------------+");
		System.out.println("| Elements are | " + shoppingCart.currentElements().toString());
		System.out.println("+--------------+-----------------------------------+");
		System.out.println("| Total price  |  " + shoppingCart.getTotalPriceOfElements());
		System.out.println("+--------------+-----------------------------------+");
		System.out.println("");
	}

	/* (non-Javadoc)
	 * @see cashregister.ui.ICashRegisterUI#displayShoppingCarts(java.util.Collection)
	 */
	@Override
	public void displayShoppingCarts(Collection<IShoppingCart> shoppingCarts) {
		if (shoppingCarts == null) return;
		// TODO Auto-generated method stub
		shoppingCarts.forEach(el -> this.displayShoppingCart(el));
	}

	/* (non-Javadoc)
	 * @see cashregister.ui.ICashRegisterUI#displayTransaction(rbvs.record.PaymentTransaction)
	 */
	@Override
	public void displayTransaction(PaymentTransaction transaction) {
		if (transaction == null) return;
		// TODO Auto-generated method stub
		System.out.println("| Displaying PaymentTransaction in CashRegisterConsoleUI |");
		System.out.println("+-----------------+--------------------------------------+");
		System.out.println("| Transaction-ID  | " + transaction.getTransactionId());
		System.out.println("+-----------------+--------------------------------------+");
		System.out.println("| Date of Payment | " + transaction.getTimestamp());
		System.out.println("+-----------------+--------------------------------------+");
		System.out.println("| Provider-Name   | " + transaction.getPaymentProviderName());
		System.out.println("+-----------------+--------------------------------------+");
		System.out.println("| Amount          | " + transaction.getPaidPrice());
		System.out.println("+-----------------+--------------------------------------+");
		System.out.println("");
	}
	
	private void displayTransaction(PaymentTransaction transaction, String spacer) {
		if (transaction == null) return;
		// TODO Auto-generated method stub
		System.out.println(spacer + "| Displaying PaymentTransaction |");
		System.out.println(spacer + "+-----------------+-------------+");
		System.out.println(spacer + "| Transaction-ID  | " + transaction.getTransactionId());
		System.out.println(spacer + "+-----------------+-------------+");
		System.out.println(spacer + "| Date of Payment | " + transaction.getTimestamp());
		System.out.println(spacer + "+-----------------+-------------+");
		System.out.println(spacer + "| Provider-Name   | " + transaction.getPaymentProviderName());
		System.out.println(spacer + "+-----------------+-------------+");
		System.out.println(spacer + "| Amount          | " + transaction.getPaidPrice());
		System.out.println(spacer + "+-----------------+-------------+");
	}

	/* (non-Javadoc)
	 * @see cashregister.ui.ICashRegisterUI#pushUpdateInformation(tree.ITree, java.util.Collection, java.util.Collection)
	 */
	@Override
	public void pushUpdateInformation(ITree<IProduct> arg0, Collection<IShoppingCart> arg1, Collection<IInvoice> arg2) {
		// TODO Auto-generated method stub
		// Any update notification is discarded. So basically here is nothing done?!
		return;

	}

}
