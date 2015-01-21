package com.sheniff.instagram;

import android.content.Context;
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

        TextView captionTextView = (TextView) convertView.findViewById(R.id.captionTextView);
        TextView usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        ImageView profileImageView = (ImageView) convertView.findViewById(R.id.profileImageView);

        captionTextView.setText(photo.caption);
        usernameTextView.setText(photo.username);
        // Reset the image from the recycled view
        photoImageView.setImageResource(0);

        // Ask for the photo to be added to the imageView based on the photo url
        Picasso.with(getContext()).load(photo.imageUrl).placeholder(R.drawable.loading).into(photoImageView);
        Picasso.with(getContext()).load(photo.profileImageUrl).placeholder(R.drawable.loading).into(profileImageView);

        return convertView;
    }
}
