import cashregister.CashRegisterFactory;
import cashregister.ICashRegister;
import cashregister.ui.CashRegisterConsoleUI;
import cashregister.ui.CashRegisterGUI;
import cashregister.ui.ICashRegisterUI;
import managementserver.IManagementServer;
import managementserver.ManagementServer;
import managementtools.ManagementServerViewer;
import rbvs.product.CompositeProduct;
import rbvs.product.ProductCategory;
import rbvs.product.SimpleProduct;
import warehouse.IWarehouse;
import warehouse.IWarehouseListener;
import warehouse.Warehouse;
import warehouse.ui.WarehouseManager;

public class RestaurantManagementSystem {

	public static void run() {

//		// Create ManagementServer and Warehouse
		IManagementServer mngServer = ManagementServer.GET_INSTANCE();
		IWarehouse warehouse = Warehouse.GET_INSTANCE();
		
		// TODO: register mngServer as listener at the warehouse
		((Warehouse) warehouse).registerListener((IWarehouseListener) mngServer);

		// TODO: add Products to warehouse
		SimpleProduct keks = new SimpleProduct("Keks", 1, ProductCategory.FOOD);
		SimpleProduct milch = new SimpleProduct("Milch", 1, ProductCategory.BEVERAGE);
		SimpleProduct zucker = new SimpleProduct("Zucker", 1, ProductCategory.FOOD);
		SimpleProduct kakao = new SimpleProduct("Kakao", 1, ProductCategory.FOOD);
		SimpleProduct plamfett = new SimpleProduct("Palmöl", 1, ProductCategory.BEVERAGE);
		CompositeProduct schokolade = new CompositeProduct("Schokolade", 50, ProductCategory.FOOD);
		schokolade.addProduct(milch);
		schokolade.addProduct(zucker);
		schokolade.addProduct(kakao);
		schokolade.addProduct(plamfett);
		CompositeProduct schokokeks = new CompositeProduct("Schokoladekeks", 20, ProductCategory.FOOD);
		schokokeks.addProduct(keks);
		schokokeks.addProduct(schokolade);
		
		warehouse.addProduct(keks);
		warehouse.addProduct(milch);
		warehouse.addProduct(zucker);
		warehouse.addProduct(kakao);
		warehouse.addProduct(plamfett);
		warehouse.addProduct(schokolade);
		warehouse.addProduct(schokokeks);
		
		// TODO: create CashRegister and register it as an observer at the mngServer
		ICashRegister cashRegister = CashRegisterFactory.createCashRegister();
		mngServer.addCashRegister(cashRegister);
		
		// TODO: Create GUI for CashRegister
		ICashRegisterUI console = new CashRegisterConsoleUI();
		ICashRegisterUI gui = new CashRegisterGUI(cashRegister);

		new WarehouseManager(warehouse);
		new ManagementServerViewer(ManagementServer.GET_INSTANCE());

		// TODO: register CashRegisterGUI as an UI at the previously created cashRegister
		cashRegister.registerCashRegisterUI(console);
		cashRegister.registerCashRegisterUI(gui);
	}
}
