package com.example.nora.firstproject;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by NORA on 12/11/2015.
 */
public class ProductAdapter extends  RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Product> prod;
    public static final String KEY_CODE = "Produktet_kod";
    public static final String KEY_EMRI = "Produktet_emri";
    public static final String KEY_CMIMI = "Produktet_cmimi";
    public static final String KEY_FOTO = "Produktet_foto";
    private static final String TAG = "ProductAdapter";
    SQLiteDatabase db;
    Context context;

    public ProductAdapter(ListProduktActivity listProduktActivity, List<Product> prod) {
        this.prod = prod;
        Context context = listProduktActivity.getApplicationContext();
        this.db = DatabaseHelper.getDb(context, DatabaseHelper.DATABASE_NAME);
    }

    @Override
    public int getItemCount() {
        return prod.size();
    }


    //removes the rows
    public void delete(int position) {
        Log.d(TAG, "Gjithsej jane " + prod.size() + " produkte");
        if(position >= 0 && position < prod.size()) {
            Product curProd = prod.get(position);
            Product.deleteRowByKodi(this.db, curProd.getKodi());
            prod.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,prod.size());

        }
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.product_list, parent, false);

        return new RecyclerViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        // Get the data model based on position


        final Product product = prod.get(position);

        CardView mCard = holder.mCard;
        mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShtoProduktActivity.class);
                intent.putExtra(KEY_CODE, product.getKodi());
                intent.putExtra(KEY_EMRI, product.getEmri());
                intent.putExtra(KEY_CMIMI, String.valueOf(product.getCmimi()));
                intent.putExtra(KEY_FOTO, product.getImazhPath());
                v.getContext().startActivity(intent);
            }
        });


        mCard.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialog =  new AlertDialog.Builder(v.getContext());


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        v.getContext(), android.R.layout.select_dialog_item
                );

                arrayAdapter.add("Shiko");
                arrayAdapter.add("Fshi");
                alertDialog.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: //Shiko
                                break;

                            case 1: //Fshi
                                Log.d(TAG, "U zgjodh per fshirje position = " + position);
                                delete(position);
                                break;

                            default:
                                break;
                        }
                    }
                });
                    alertDialog.show();
                    return false;

            }


        });



        TextView text1 = holder.prod_em;
        TextView text2 = holder.prod_kd;
        TextView text3 = holder.prod_cm;
        ImageView im1 = holder.im;

        text1.setText(product.getEmri());
        text2.setText(product.getKodi());
        text3.setText(String.valueOf(product.getCmimi()));
        String imazhPath = product.getImazhPath();

        if (imazhPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imazhPath);
            im1.setImageBitmap(bitmap);
            im1.setVisibility(View.VISIBLE);
        } else {
            im1.setVisibility(View.GONE);
        }

    }
}






