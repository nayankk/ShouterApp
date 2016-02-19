package com.xc0ffee.shouter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xc0ffee.shouter.R;
import com.xc0ffee.shouter.models.Shouter;

import java.util.List;

public class ShouterArrayAdapter extends ArrayAdapter<Shouter> {

    public ShouterArrayAdapter(Context context, List<Shouter> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Shouter shouter = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shouter_item, parent, false);
        }
        ImageView imProfileImage = (ImageView) convertView.findViewById(R.id.iv_profile);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tv_user);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tv_body);

        tvUserName.setText(shouter.getUser().getName());
        tvBody.setText(shouter.getBody());
        imProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(shouter.getUser().getProfileImageUrl()).into(imProfileImage);
        return convertView;
    }
}
