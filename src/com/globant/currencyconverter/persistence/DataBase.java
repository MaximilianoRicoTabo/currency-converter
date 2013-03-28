package com.globant.currencyconverter.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Represents embedded database.
 *
 * @author Maximiliano Rico &lt;maximiliano.rico@globant.com&gt;
 * @since 0.1
 */
public class DataBase extends SQLiteOpenHelper {

  /**
   * Name of the database table.
   */
  public static final String TABLE_NAME = "currency_values";

  /**
   * The table's primary key name.
   */
  public static final String COLUMN_ID = "_id";

  /**
   * The table column name.
   */
  public static final String COLUMN_CURRENCY_NAME = "currency_name";

  /**
   * The table column name.
   */
  public static final String COLUMN_CURRENCY_VALUE = "currency_value";

  /**
   * database name.
   */
  private static final String DATABASE_NAME = "currency_final.db";

  /**
   * database version.
   */
  private static final int DATABASE_VERSION = 1;

  /**
   * Creates a new {@link DataBase}.
   *
   * @param context The {@link Context}.
   * @param name The database name.
   * @param factory The database Fact
   * @param version
   */
  public DataBase(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Database creation sql statement.
   */
  private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID 
      + " INTEGER PRIMARY KEY, " + COLUMN_CURRENCY_NAME + " text," + COLUMN_CURRENCY_VALUE + " text);";

  /**
   * {@inheritDoc}.
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(DATABASE_CREATE);
    
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(DataBase.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }
}
