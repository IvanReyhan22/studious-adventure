package com.ezyindustries.goes_englishcourse.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezyindustries.goes_englishcourse.R;

public class refViewHolder extends RecyclerView.ViewHolder{

    public TextView Stitle;
    public ImageView Simage;

    public refViewHolder(@NonNull View itemView) {
        super(itemView);

        Stitle = (TextView) itemView.findViewById(R.id.Stitle);
        Simage = (ImageView) itemView.findViewById(R.id.Simage);
    }
}
