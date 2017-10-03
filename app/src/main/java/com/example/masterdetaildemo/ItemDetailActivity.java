package com.example.masterdetaildemo;

import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.masterdetaildemo.model.UAMenus;
import com.example.masterdetaildemo.net.UAMenusApiClient;

public class ItemDetailActivity extends AppCompatActivity {
    ItemDetailFragment fragmentItemDetail;
    //private UAMenusApiClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        // Fetch the item to display from bundle
        Item item = (Item) getIntent().getSerializableExtra("item");
        if (savedInstanceState == null) {
            String information = ItemsListActivity.client.getCantines(ItemsListActivity.jsonString, item.getTitle());
            item.setInfo(information);
            // Insert detail fragment based on the item passed
            fragmentItemDetail = ItemDetailFragment.newInstance(item); // <-------
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItemDetail);
            ft.commit();
        }
    }
}
