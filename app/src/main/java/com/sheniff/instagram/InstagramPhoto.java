package com.sheniff.instagram;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sheniff on 1/20/15.
 */
public class InstagramPhoto {
    public String username;
    public String caption;
    public String imageUrl;
    public int likesCount;
    public String profileImageUrl;
    public Date date;
    public int commentsCount;
    public ArrayList<InstagramComment> comments;
}
