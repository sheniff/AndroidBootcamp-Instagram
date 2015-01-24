package com.sheniff.instagram;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sheniff on 1/21/15.
 */
public class InstagramComment {
    private String username;
    private String comment;
    private String imageUrl;

    public InstagramComment(){}

    public InstagramComment(JSONObject commentJSON) throws JSONException {
        this.username = commentJSON.getJSONObject("from").getString("full_name");
        this.imageUrl = commentJSON.getJSONObject("from").getString("profile_picture");
        this.comment  = commentJSON.getString("text");
    }

    public String getUsername() {
        return username;
    }

    public String getComment() {
        return comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
