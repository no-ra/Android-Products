
package com.example.nora.firstproject;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class ShtoProduktActivity extends AppCompatActivity {
    DatabaseHelper db;
    ImageView  iv;
    Uri uri = null; //ruhet uri-i fotos se shkrepur
    Intent intent;




    protected String path() { //kthern path-in e fotos
        if(uri != null) {
            return uri.getPath();
        }
        return null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shto);
        this.db = new DatabaseHelper(this);

        EditText prod_obj = (EditText) findViewById(R.id.emer_produkti);
        EditText kodi_obj = (EditText) findViewById(R.id.kod_produkti);
        EditText cmim_obj = (EditText) findViewById(R.id.cmim_produkti);
        ImageView im = (ImageView) findViewById(R.id.imageView);

        String produkti = getIntent().getStringExtra(ProductAdapter.KEY_EMRI);
        String kodi = getIntent().getStringExtra(ProductAdapter.KEY_CODE);
        String cmimi = getIntent().getStringExtra(ProductAdapter.KEY_CMIMI);
        String imazhPath = getIntent().getStringExtra(ProductAdapter.KEY_FOTO);

        prod_obj.setText(produkti);
        kodi_obj.setText(kodi);
        cmim_obj.setText(cmimi);
        if(imazhPath != null) {
            im.setImageBitmap(BitmapFactory.decodeFile(imazhPath));
            uri = Uri.fromFile(new File(imazhPath));
        }

        Button shto=(Button)findViewById(R.id.btn_shto);
        shto.setOnClickListener(shtoBtnListener);

        Button ngarkoImazh = (Button) findViewById(R.id.btn_imazh);
        iv  = (ImageView) findViewById(R.id.imageView);
        ngarkoImazh.setOnClickListener(imazhBtnListener);






       Button b = (Button) findViewById(R.id.btn_shto);
        b.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });




        final String[] option = new String[] {"Shiko","Fshi" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0: //"Shiko"
                        Intent intent = new Intent();
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        File file = new File(path());
                        intent.setDataAndType(Uri.fromFile(file), "image/*");
                        startActivity(intent);
                        break;
                    case 1: //Fshi

                        File f = new File(path());
                        f.delete();
                        iv.setImageBitmap(null);
                        uri = null;
                        break;
                }

            }
        });

        final AlertDialog dialog = builder.create();
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.show();
            }
        });

    } //mbyllja  e method-es onCreate





    protected void onActivityResult(int requestCode,int resultCode, Intent data)  {
        // TODO Auto-generated method stub
        if(data != null) {
            super.onActivityResult(requestCode, resultCode, data);
            Bitmap bp = (Bitmap)data.getExtras().get("data");
            iv.setImageBitmap(bp);
            try {
                uri = Uri.fromFile(Utils.createImageFile(this));
                Utils.compressAndSaveBmp(uri,bp);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,R.string.failed_create_photo,Toast.LENGTH_SHORT).show();
            }

        }

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.liste:
                intent = new Intent(ShtoProduktActivity.this, ListProduktActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shto, menu);
        return true;
    }


     private View.OnClickListener shtoBtnListener=new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            shtoProdukt();
        }
     };


    private View.OnClickListener imazhBtnListener=new View.OnClickListener(){


        @Override
        public void onClick(View v) {

            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);

        }

    };




    public void shtoProdukt() {
        EditText prod_obj = (EditText) findViewById(R.id.emer_produkti);
        EditText kodi_obj = (EditText) findViewById(R.id.kod_produkti);
        EditText cmim_obj = (EditText) findViewById(R.id.cmim_produkti);
        ImageView img = (ImageView) findViewById(R.id.imageView);
        Product p = new Product();

        if( !prod_obj.getText().toString().equals("") && !kodi_obj.getText().toString().equals("") &&
                !cmim_obj.getText().toString().equals("")) {
          p.setEmri(prod_obj.getText().toString());
            p.setKodi(kodi_obj.getText().toString());
            p.setCmimi(Double.parseDouble(cmim_obj.getText().toString()));
            p.setImazh(path());
            p.save(db.getDb(getApplicationContext(), DatabaseHelper.DATABASE_NAME));



            Toast.makeText(ShtoProduktActivity.this, "Produkti u ruajt", Toast.LENGTH_SHORT).show();

            prod_obj.setText("");
            kodi_obj.setText("");
            cmim_obj.setText("");
            uri = null;
            img.setImageResource(0);
        }   else {
            Toast.makeText(ShtoProduktActivity.this, "Fut te dhena", Toast.LENGTH_SHORT).show();
        }
    }
}