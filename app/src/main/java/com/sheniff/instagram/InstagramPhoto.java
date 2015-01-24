package com.sheniff.instagram;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sheniff on 1/20/15.
 */
public class InstagramPhoto {
    private String id;
    private String username;
    private String caption;
    private String imageUrl;
    private int likesCount;
    private String profileImageUrl;
    private long date;
    private int commentsCount;
    private ArrayList<InstagramComment> comments;

    public InstagramPhoto(){}

    public InstagramPhoto(JSONObject photoJSON) throws JSONException {
        this.id                = photoJSON.getString("id");
        this.username          = photoJSON.getJSONObject("user").getString("full_name");
        this.imageUrl          = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
        this.profileImageUrl   = photoJSON.getJSONObject("user").getString("profile_picture");
        this.likesCount        = photoJSON.getJSONObject("likes").getInt("count");
        this.commentsCount     = photoJSON.getJSONObject("comments").getInt("count");
        this.date              = photoJSON.getLong("created_time");

        if (!photoJSON.isNull("caption")) {
            this.caption       = photoJSON.getJSONObject("caption").getString("text");
        }

        // setting comments
        int numComments = this.commentsCount >= 2 ? 2 : this.commentsCount;
        this.comments = new ArrayList<>();

        for (int comm = 0; comm < numComments; comm++) {
            InstagramComment comment = new InstagramComment(photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(comm));
            this.comments.add(comment);
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public long getDate() {
        return date;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public ArrayList<InstagramComment> getComments() {
        return comments;
    }
}
