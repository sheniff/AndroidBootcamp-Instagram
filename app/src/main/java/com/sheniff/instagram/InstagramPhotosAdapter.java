package com.sheniff.instagram;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sheniff on 1/20/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
        super(context, R.layout.item_photo, photos);
    }

    // Takes a data item at a position, converts it to a row in the listview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        // Check if we are using a recycled view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView captionTextView            = (TextView) convertView.findViewById(R.id.captionTextView);
        TextView usernameTextView           = (TextView) convertView.findViewById(R.id.usernameTextView);
        TextView likesCountTextView         = (TextView) convertView.findViewById(R.id.likesCountTextView);
        TextView commentsCountTextView      = (TextView) convertView.findViewById(R.id.commentsCountTextView);
        ImageView photoImageView            = (ImageView) convertView.findViewById(R.id.photoImageView);
        ImageView profileImageView          = (ImageView) convertView.findViewById(R.id.profileImageView);
        TextView dateTextView               = (TextView) convertView.findViewById(R.id.dateTextView);
        TextView comment1NameTextView       = (TextView) convertView.findViewById(R.id.comment1UsernameTextView);
        TextView comment1CommentTextView    = (TextView) convertView.findViewById(R.id.comment1CommentTextView);
        TextView comment2NameTextView       = (TextView) convertView.findViewById(R.id.comment2UsernameTextView);
        TextView comment2CommentTextView    = (TextView) convertView.findViewById(R.id.comment2CommentTextView);

        captionTextView.setText(photo.getCaption());
        usernameTextView.setText(photo.getUsername());
        dateTextView.setText("🕒 " + DateUtils.getRelativeTimeSpanString(photo.getDate() * 1000));
        likesCountTextView.setText("❤ " + photo.getLikesCount() + " likes");
        commentsCountTextView.setText("view all " + photo.getCommentsCount() + " comments");
        // Reset the image from the recycled view
        photoImageView.setImageResource(0);

        // Ask for the photo to be added to the imageView based on the photo url
        Picasso.with(getContext()).load(photo.getImageUrl()).placeholder(R.drawable.loading).into(photoImageView);
        Picasso.with(getContext()).load(photo.getProfileImageUrl()).placeholder(R.drawable.loading).into(profileImageView);

        // Comments
        if(photo.getComments().size() > 0) {
            comment1NameTextView.setText(photo.getComments().get(0).getUsername());
            comment1CommentTextView.setText(photo.getComments().get(0).getComment());
            comment1NameTextView.setVisibility(View.VISIBLE);
            comment1CommentTextView.setVisibility(View.VISIBLE);
        } else {
            comment1NameTextView.setVisibility(View.INVISIBLE);
            comment1CommentTextView.setVisibility(View.INVISIBLE);
        }

        if(photo.getComments().size() > 1) {
            comment2NameTextView.setText(photo.getComments().get(1).getUsername());
            comment2CommentTextView.setText(photo.getComments().get(1).getComment());
        } else {
            comment2NameTextView.setVisibility(View.INVISIBLE);
            comment2CommentTextView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
