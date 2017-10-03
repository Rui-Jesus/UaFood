package com.example.masterdetaildemo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.example.masterdetaildemo.model.UAMenus;
import com.example.masterdetaildemo.net.UAMenusApiClient;

public class ItemsListActivity extends AppCompatActivity implements ItemsListFragment.OnListItemSelectedListener{

    private boolean isTwoPane = false;
    public static UAMenusApiClient client = null;
    public static String jsonString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);
        // Call this to determine which layout we are in (tablet or phone)
        determinePaneLayout();

        String url = "http://services.web.ua.pt/sas/ementas?date=week&place=santiago&format=json";
        if (null == client) {
            client = new UAMenusApiClient(url);
        }
        jsonString = client.getMenusForCanteen2();
    }

    private void determinePaneLayout() {
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        if (fragmentItemDetail != null) {
            isTwoPane = true;
            ItemsListFragment fragmentItemsList =
                    (ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
            fragmentItemsList.setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(final Item item) {
        if (isTwoPane) { // single activity with list and detail
            // Replace framelayout with new detail fragment
            ItemDetailFragment fragmentItem = ItemDetailFragment.newInstance(item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItem);
            ft.commit();
        } else { // go to separate activity
            // launch detail activity using intent
            Intent i = new Intent(this, ItemDetailActivity.class);
            i.putExtra("item", item);
            startActivity(i);
        }
    }

}
