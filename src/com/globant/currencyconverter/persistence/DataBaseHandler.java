package com.globant.currencyconverter.persistence;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.globant.currencyconverter.model.Currency;
import com.globant.currencyconverter.model.CurrencyNames;

/**
 * Helper Class to manage embeded database.
 *
 * @author Maximiliano Rico &lt;maximiliano.rico@globant.com&gt;
 * @since 0.1
 */
public class DataBaseHandler {

  // Database fields
  private SQLiteDatabase database;
  private DataBase db;
  private String[] allColumns = { DataBase.COLUMN_ID,
      DataBase.COLUMN_CURRENCY_NAME, DataBase.COLUMN_CURRENCY_VALUE };
  
  public DataBaseHandler(final Context context) {
    db = new DataBase(context);
  }

  /**
   * 
   * @throws SQLException
   */
  public void open() throws SQLException {
    database = db.getWritableDatabase();
  }

  /**
   * 
   */
  public void close() {
    db.close();
  }

  /**
   * 
   * @param name
   * @param value
   * @return
   */
  public Currency createCurrency(final String name, final double value) {
    ContentValues values = new ContentValues();
    values.put(DataBase.COLUMN_CURRENCY_NAME, name);
    values.put(DataBase.COLUMN_CURRENCY_VALUE, value);
    long insertId = database.insert(DataBase.TABLE_NAME, null, values);
    Cursor cursor = database.query(DataBase.TABLE_NAME, allColumns, DataBase.COLUMN_ID
        + " = " + insertId, null, null, null, null);
    cursor.moveToFirst();
    Currency currency = cursorToCurrencyMap(cursor);
    cursor.close();
    return currency;
  }
  
  /**
   * 
   * @param name
   * @param value
   * @return
   */
  public void updateCurrency(final String name, final double value) {
    ContentValues values = new ContentValues();
    values.put(DataBase.COLUMN_CURRENCY_NAME, name);
    values.put(DataBase.COLUMN_CURRENCY_VALUE, value);
    database.update(DataBase.TABLE_NAME, values,
        DataBase.COLUMN_CURRENCY_NAME + " like '" + name +"'", null);
  }

  /**
   * 
   * @return
   */
  public Map<CurrencyNames, Currency> getAllValues() {
    Map<CurrencyNames, Currency> currencies = new HashMap<CurrencyNames, Currency>();
    Cursor cursor = database.query(DataBase.TABLE_NAME,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Currency currency = cursorToCurrencyMap(cursor);
      currencies.put(CurrencyNames.valueOf(currency.getName()), currency);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return currencies;
  }

  /**
   * 
   * @param cursor
   * @return
   */
  private Currency cursorToCurrencyMap(Cursor cursor) {
    Currency currency = new Currency(CurrencyNames.valueOf(cursor.getString(1)),
            Double.parseDouble(cursor.getString(2)));
    return currency;
  }
}
