package com.sheniff.instagram;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CommentsActivity extends ActionBarActivity {
    private ArrayList<InstagramComment> comments;
    private InstagramCommentsAdapter aComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        fetchComments();
    }

    private void fetchComments() {
        String commentsURL = "https://api.instagram.com/v1/media/" + getIntent().getStringExtra("mediaId") + "/comments?client_id=" + StreamActivity.CLIENT_ID;

        comments = new ArrayList<>();
        aComments = new InstagramCommentsAdapter(this, comments);

        ListView lvComments = (ListView) findViewById(R.id.lvComments);
        lvComments.setAdapter(aComments);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(commentsURL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray commentsJSON = null;
                try {
                    commentsJSON = response.getJSONArray("data");

                    for (int i = 0; i < commentsJSON.length(); i++) {
                        JSONObject commentJSON = commentsJSON.getJSONObject(i);     // each photo
                        InstagramComment comm  = new InstagramComment(commentJSON);
                        comments.add(comm);
                    }

                    aComments.notifyDataSetChanged(); // adapter HAS TO notify the view that the update is done
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
