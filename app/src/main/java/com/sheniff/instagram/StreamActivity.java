package com.sheniff.instagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class StreamActivity extends ActionBarActivity {

    public static final String CLIENT_ID = "26d05aac18d2490e897e86ebdf8f48de";
    private ArrayList<InstagramPhoto> photos;
    private ListView streamListView;
    private InstagramPhotosAdapter aPhotos;
    private PullToRefreshLayout mPullToRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        fetchPopularPhotos();

        // find the PtR layout
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.ptr_layout);

        ActionBarPullToRefresh.from(this)
                .allChildrenArePullable()
                .options(Options.create()
                        .scrollDistance(.35f)
                        .refreshOnUp(true)
                        .build())
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        fetchPopularPhotos();
                    }
                })
                .setup(mPullToRefreshLayout);
    }

    private void setupListViewListener() {
        streamListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(StreamActivity.this, CommentsActivity.class);
                i.putExtra("mediaId", photos.get(position).getId());
                startActivity(i);
            }
        });
    }

    private void fetchPopularPhotos() {
        String popularPhotosURL = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        photos  = new ArrayList<>();
        aPhotos = new InstagramPhotosAdapter(this, photos);

        streamListView = (ListView) findViewById(R.id.streamListView);
        streamListView.setAdapter(aPhotos);

        AsyncHttpClient client = new AsyncHttpClient();     // http client

        client.get(popularPhotosURL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mPullToRefreshLayout.setRefreshComplete();
                JSONArray photosJSON = null;
                try {
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);     // each photo
                        InstagramPhoto photo = new InstagramPhoto(photoJSON);
                        photos.add(photo);
                    }

                    setupListViewListener();

                    aPhotos.notifyDataSetChanged(); // adapter HAS TO notify the view that the update is done
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                mPullToRefreshLayout.setRefreshComplete();
            }
        });
    }
}
