package com.example.nora.firstproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Created by NORA on 12/11/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Produkt.db";

    private SQLiteDatabase db;
    private static DatabaseHelper mHelper;
    private static String mDbName;
    private static Context mContext;
    private static int SCHEMA = 1 ;
    private static final Object mLock = new Object();

    private DatabaseHelper(Context context, String dbName) {
        super(context, dbName, null, SCHEMA);
        mContext = context;
        mDbName = dbName;
    }

    public DatabaseHelper(Context context) {
        //super(context, DATABASE_NAME, null, 1);
        this(context, DATABASE_NAME);
    }

    public static DatabaseHelper getInstance(Context context, String dbName) {
        synchronized (mLock) {
            if (mHelper == null || context != mContext || !dbName.equals(mDbName)) {
                mHelper = new DatabaseHelper(context.getApplicationContext(), dbName);

            }

            return mHelper;
        }
    }

    public synchronized static SQLiteDatabase getDb(Context context, String dbName) {
        SQLiteDatabase db = getInstance(context.getApplicationContext(), dbName).getWritableDatabase();
        if (!db.inTransaction() && Build.VERSION.SDK_INT > 15) {
            if (!db.isWriteAheadLoggingEnabled())
                db.enableWriteAheadLogging();
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format(
                "CREATE TABLE %s (%s text, %s text, %s real,  %s text, %s integer primary key autoincrement)",
                Product.TABLE_NAME, Product.COL_EMRI, Product.COL_KODI, Product.COL_CMIMI, Product.COL_IMAZH, Product.COL_ID);
        db.execSQL(query);
        Log.d("DatabaseHelper:", "Tabela u krijua!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + Product.TABLE_NAME);
        Log.d("DatabaseHelper:", "Tabela u fshi!");
        onCreate(db);
    }


}