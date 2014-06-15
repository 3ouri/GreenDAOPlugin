package com.eska.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.eska.lifeinsurancedao.DaoMaster;
import com.eska.lifeinsurancedao.DaoMaster.DevOpenHelper;
import com.eska.lifeinsurancedao.DaoSession;
import com.eska.lifeinsurancedao.Product;
import com.eska.lifeinsurancedao.ProductDao;

public class GreenDAOPlugin extends CordovaPlugin {

	public static final String ACTION_COUNT_FROM_DB = "countFromDB";
	public static final String ACTION_ADD_TO_DB = "addToDB";

	private SQLiteDatabase db;
	private DaoMaster daoMaster;
	private DaoSession daoSession;
	private ProductDao productDao;
	private Cursor cursor;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		try {
			DevOpenHelper helper = new DaoMaster.DevOpenHelper(this.cordova
					.getActivity().getApplicationContext(), "cordavo-notes-db",
					null);
			db = helper.getWritableDatabase();
			daoMaster = new DaoMaster(db);
			daoSession = daoMaster.newSession();
			productDao = daoSession.getProductDao();
			System.out.println("hello");
			if (action.equals(ACTION_COUNT_FROM_DB)) {
				String textColumn = ProductDao.Properties.Product_Name.columnName;
				String orderBy = textColumn + " COLLATE LOCALIZED ASC";
				cursor = db.query(productDao.getTablename(),
						productDao.getAllColumns(), null, null, null, null,
						orderBy);
				callbackContext.success("Done counting ... Count = "
						+ cursor.getCount());
			}
			if (action.equals(ACTION_ADD_TO_DB)) {
				Product product = new Product(
						null,
						"Life Credit Plan",
						20,
						65,
						2,
						49,
						1,
						1L,
						70,
						6,
						"Is a type of term life insurance designed to pay off the balance due on a loan if the borrower dies before the loan is repaid. The face value of a credit life insurance policy decreases proportionately with an outstanding loan amount as the loan is paid off over time until both reach zero value. Credit life insurance can protect a person\'s dependents as it may also be required by some lenders.");
				productDao.insert(product);
				Log.d("DaoExample",
						"Inserted new product, ID: " + product.getId());
				callbackContext.success("Done Adding Product");
			}
			return true;

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}
	}

}
