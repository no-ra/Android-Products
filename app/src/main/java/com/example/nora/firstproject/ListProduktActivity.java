package com.example.nora.firstproject;



import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.SearchView;


public class ListProduktActivity extends AppCompatActivity {

    DatabaseHelper db;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    ArrayList<Product> allProds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        db = new DatabaseHelper(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        allProds = Product.selectAllForProduct(this.db.getDb(this, DatabaseHelper.DATABASE_NAME));
        Log.d("ListProduktActivity", "Gjithsej: " + allProds.size());
        productAdapter = new ProductAdapter(this, allProds);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(listener);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);




                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            query = query.toLowerCase();

            final List<Product> filteredList = new ArrayList<>();

            for (Product product : allProds) {
                final String text = product.getEmri().toLowerCase();

                if (text.contains(query)) {

                    filteredList.add(product);
                }
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(ListProduktActivity.this));
            productAdapter = new ProductAdapter(ListProduktActivity.this, filteredList);
            recyclerView.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();  // data set changed
            return true;
        }
    };
}