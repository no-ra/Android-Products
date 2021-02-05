package com.example.nora.firstproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by NORA on 12/11/2015.
 */
public class Product {
    public static final String TABLE_NAME ="produkt";
    public static final String COL_EMRI= "Pershkrimi";
    public static final String COL_KODI= "Kodi";
    public static final String COL_CMIMI= "Cmimi";
    public static final String COL_IMAZH = "imazh";
    public static final String COL_ID = "IDProdukti";

    private String emri;
    private String kodi;
    private double cmimi;
    private String imazh;

    public void setImazh(String imazh) { this.imazh = imazh;}

    public String getImazhPath() { return imazh;}

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getEmri(){
        return emri;
    }

    public void setKodi(String kodi){
        this.kodi = kodi;
    }

    public String getKodi(){
        return kodi;
    }

    public void setCmimi(double cmimi){
        this.cmimi = cmimi;
    }

    public double getCmimi() { return cmimi; }


    public void save(SQLiteDatabase db) {
        ContentValues cv=getContentValues();
        if(!exists(db)) {
            db.insert(TABLE_NAME, COL_KODI, cv);
        }
        else {
            db.update(TABLE_NAME,cv,COL_KODI+"=?",new String[]{kodi});
        }
    }

     protected ContentValues getContentValues(){
        ContentValues cv = new ContentValues();
        cv.put(COL_EMRI, emri);
        cv.put(COL_KODI, kodi);
        cv.put(COL_CMIMI, cmimi);
        cv.put(COL_IMAZH, imazh);
        return cv;
    }

    private boolean exists(SQLiteDatabase db) {
        String sql="SELECT COUNT(*) FROM "+TABLE_NAME+" WHERE "+COL_KODI+"=?";
        Cursor c = db.rawQuery(sql, new String[]{kodi});
        int count = 0;
        if(c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count > 0 ? true : false;
    }

    private static Product readCurrentItem(Cursor c) {
        Product p = new Product();
        int emriIndex = c.getColumnIndex(COL_EMRI);
        int kodiIndex = c.getColumnIndex(COL_KODI);
        int cmimiIndex = c.getColumnIndex(COL_CMIMI);
        int imazhIndex = c.getColumnIndex(COL_IMAZH);
        int idIndex = c.getColumnIndex(COL_ID);

        p.setEmri(getStr(c, COL_EMRI));
        p.setKodi(getStr(c, COL_KODI));
        p.setCmimi(getDouble(c, COL_CMIMI));
        p.setImazh(getStr(c, COL_IMAZH));
        return p;
    }


    private static double getDouble(Cursor c, String emerKolone) {
        int index = c.getColumnIndex((emerKolone));
        return c.getDouble(index);
    }

    private static String getStr(Cursor c, String emerKolone) {
        int index = c.getColumnIndex(emerKolone);
        return c.getString(index);
    }
    public static ArrayList<Product> selectAllForProduct(SQLiteDatabase db) {
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(sql, new String[]{});
        ArrayList<Product> p= new ArrayList<>();
        while (c.moveToNext()) {
            p.add(readCurrentItem(c));
        }
        c.close();
        return p;
    }


    public static ArrayList<Product> AllProducts(SQLiteDatabase db,String emerProdukti) {
         String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " +COL_EMRI + "=?";
         Cursor c = db.rawQuery(sql, new String[]{emerProdukti});
         ArrayList<Product> p= new ArrayList<>();
         while (c.moveToNext()) {
             p.add(readCurrentItem(c));
         }
         c.close();
         return p;
     }

     //gjejme count e produkteve
    public static int  countProduct(SQLiteDatabase db) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(sql,null);
        int count = 0;
        if(c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    //delete row from database.
    public static void  deleteRows(SQLiteDatabase db,final int id) {
        //String sql = "Delete * FROM " + TABLE_NAME;
        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{id + ""});

    }


    // fshi produkt sipas kodit
    public static void  deleteRowByKodi(SQLiteDatabase db, final String kodi) {
        db.delete(TABLE_NAME, COL_KODI + "=?", new String[]{kodi + ""});
    }




    public static Product instanceFromDb(SQLiteDatabase db,String productCode){
        String sql="SELECT * FROM "+TABLE_NAME+" WHERE "+COL_KODI+"=?";
        Cursor c=db.rawQuery(sql,new String[]{productCode});
        Product p=null;
        if(c.moveToFirst()){
            p=readCurrentItem(c);
        }
        c.close();
        return p;

    }
    public static void deleteAll(SQLiteDatabase db) {
        db.delete(TABLE_NAME, null, null);
    }

}
