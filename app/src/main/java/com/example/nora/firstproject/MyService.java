package com.example.nora.firstproject;

/**
 * Created by NORA on 1/25/2016.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;



public class MyService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;




    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
       // Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

        handler = new Handler();
        runnable = new Runnable() {

            public void run() {
                //Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();

                SharedPreferences manager = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = manager.edit();
                int sharedPreferenceCount = manager.getInt("shared_count", 0);


                int totalProducts = Product.countProduct(DatabaseHelper.getInstance(context, DatabaseHelper.DATABASE_NAME).getWritableDatabase());

                if (sharedPreferenceCount < totalProducts) {
                    Toast.makeText(context, "Keni shtuar: " + (totalProducts - sharedPreferenceCount), Toast.LENGTH_SHORT).show();
                    editor.putInt("shared_count", totalProducts);
                    editor.apply();
                } else if (sharedPreferenceCount > totalProducts) {
                    Toast.makeText(context, "Keni hequr: " + (sharedPreferenceCount - totalProducts), Toast.LENGTH_SHORT).show();

                }
                editor.putInt("shared_count", totalProducts);
                editor.apply();
                handler.postDelayed(runnable, 10000);

            }
        };

        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {

       // Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }

}