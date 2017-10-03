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
    private UAMenusApiClient client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        // Fetch the item to display from bundle
        Item item = (Item) getIntent().getSerializableExtra("item");
        if (savedInstanceState == null) {
            //API DATA REQUEST

            //String url = this.getString(R.string.food_menus_endpoint);
            /*String url = "http://services.web.ua.pt/sas/ementas?date=week&place=santiago&format=json";
            if (null == client) {
                client = new UAMenusApiClient(url);
            }

            client.getMenusForCanteen(this, item.getTitle(), new UAMenusApiClient.UAMenusApiResponseListener() {
                @Override
                public void handleRetrievedResults(UAMenus response) {
                    //Toast.makeText(SampleClientActivity.this, "Got " + response.getDailyMenusPerCanteen().size() + " results! See the log for more.", Toast.LENGTH_SHORT).show();
                    Log.i("FoCa", response.formatedContentsForDebugging());
                }

            });*/

            String url = "http://services.web.ua.pt/sas/ementas?date=week&place=santiago&format=json";


            if (null == client) {
                client = new UAMenusApiClient(url);
            }

            String information = client.getMenusForCanteen2(item.getTitle());
            item.setInfo(information);
            // Insert detail fragment based on the item passed
            fragmentItemDetail = ItemDetailFragment.newInstance(item); // <-------
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItemDetail);
            ft.commit();
        }
    }
}
