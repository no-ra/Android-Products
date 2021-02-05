package com.example.nora.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridViewAdapter mAdapter;
    private ArrayList<String> listMenu;
    private ArrayList<Integer> listImages;

    private GridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MyService.class);
        startService(intent);
        startService(new Intent(this, MyService.class));


        prepareList();

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridViewAdapter(this,listMenu, listImages);

        // Set custom adapter to gridview
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);

        // Implement On Item click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(MainActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (position)
                {
                    case 0:
                        intent = new Intent(MainActivity.this, ShtoProduktActivity.class);
                        startActivity(intent);


                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, ListProduktActivity.class);
                        startActivity(intent);



                        break;
                    default:
                        break;
                }

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void prepareList()
    {
        listMenu = new ArrayList<String>();

        listMenu.add("Shto Produkt");           // position -> 0
        listMenu.add("Lista e Produkteve");     // position -> 1


        listImages = new ArrayList<Integer>();
        listImages.add(R.drawable.ic_plus);
        listImages.add(R.drawable.ic_reader);

    }
}
