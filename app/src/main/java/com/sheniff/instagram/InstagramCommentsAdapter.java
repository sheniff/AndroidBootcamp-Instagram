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
 * Created by sheniff on 1/21/15.
 */
public class InstagramCommentsAdapter extends ArrayAdapter<InstagramComment> {
    public InstagramCommentsAdapter(Context context, List<InstagramComment> comments) {
        super(context, R.layout.item_comment, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramComment comment = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.commentNameTextView);
        TextView commentTextView = (TextView) convertView.findViewById(R.id.commentTextView);
        ImageView profileImageView = (ImageView) convertView.findViewById(R.id.imgvCommentsProfile);

        nameTextView.setText(comment.getUsername());
        commentTextView.setText(comment.getComment());

        Picasso.with(getContext()).load(comment.getImageUrl()).placeholder(R.drawable.loading).into(profileImageView);

        return convertView;
    }
}
