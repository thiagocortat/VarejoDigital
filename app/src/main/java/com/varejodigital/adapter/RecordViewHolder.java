package com.varejodigital.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.varejodigital.R;


/**
 * Created by thiagocortat on 1/15/15.
 */
public class RecordViewHolder extends RecyclerView.ViewHolder {

    protected ImageView vImageView;
    protected TextView vName;
    protected TextView vSurname;
    protected TextView vEmail;
    protected TextView vTitle;

    public RecordViewHolder(View v) {
        super(v);
        vImageView = (ImageView) v.findViewById(R.id.imageView);
        vName =  (TextView) v.findViewById(R.id.txtName);
        vSurname = (TextView)  v.findViewById(R.id.txtSurname);
        vEmail = (TextView)  v.findViewById(R.id.txtEmail);
        vTitle = (TextView) v.findViewById(R.id.title);
    }
}
