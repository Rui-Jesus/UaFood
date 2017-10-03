package com.example.masterdetaildemo.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import com.example.masterdetaildemo.model.UAMenus;
import com.example.masterdetaildemo.model.UaMenusParserUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * helper class to handle the remote resource call
 */

public class UAMenusApiClient {
    private final String url;

    public interface UAMenusApiResponseListener {
        void handleRetrievedResults(UAMenus response);
    }

    public UAMenusApiClient(String url) {
        this.url = url;
    }

    /**
     * calls the remote resource adn delivers the food options to the listener object, asynchronously,
     * by using on the Volley library
     * @param context   calling activity
     * @param selectedSite  the id (name) of the canteen. if null, retrieves all canteens
     * @param resultsHandler  the callback object. note that the response is asynchronous
     */
    public void getMenusForCanteen(Context context, final String selectedSite, final UAMenusApiResponseListener resultsHandler) {

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                            UAMenus menus = UaMenusParserUtility.parseJson(response.toString());
                            // should the results be filter for a given canteen?
                            if (null == selectedSite) {
                                menus.sortByCanteen();
                            } else {
                                menus.filterByCanteen(selectedSite);
                            }
                            resultsHandler.handleRetrievedResults(menus);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FoCa", "Errors calling endpoint: ".concat(error.getLocalizedMessage()));
                    }
                });

        // Add the request to the RequestQueue.
        Log.i("FoCa", "request queued");
        queue.add(jsObjRequest);
    }

    public String getMenusForCanteen2(final String selectedSite) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL("http://services.web.ua.pt/sas/ementas?date=week&place=santiago&format=json");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                forecastJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                forecastJsonStr = null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            forecastJsonStr = null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

        UAMenus menus = UaMenusParserUtility.parseJson(forecastJsonStr.toString());
        // should the results be filter for a given canteen?
        if (null == selectedSite) {
            menus.sortByCanteen();
        } else {
            menus.filterByCanteen(selectedSite);
        }

        return menus.formatedContentsForDebugging();
    }
}
