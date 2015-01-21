package com.sheniff.instagram;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class StreamActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "26d05aac18d2490e897e86ebdf8f48de";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {
        String popularPhotosURL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        photos  = new ArrayList<>();
        aPhotos = new InstagramPhotosAdapter(this, photos);

        ListView streamListView = (ListView) findViewById(R.id.streamListView);
        streamListView.setAdapter(aPhotos);

        AsyncHttpClient client = new AsyncHttpClient();     // http client

        client.get(popularPhotosURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON    = photosJSON.getJSONObject(i);     // each photo
                        InstagramPhoto photo    = new InstagramPhoto();
                        photo.username          = photoJSON.getJSONObject("user").getString("username");
                        photo.imageUrl          = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.profileImageUrl   = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.likesCount        = photoJSON.getJSONObject("likes").getInt("count");
                        if (!photoJSON.isNull("caption")) {
                            photo.caption       = photoJSON.getJSONObject("caption").getString("text");
                        }
                        photos.add(photo);
                    }
                    aPhotos.notifyDataSetChanged(); // adapter HAS TO notify the view that the update is done
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
