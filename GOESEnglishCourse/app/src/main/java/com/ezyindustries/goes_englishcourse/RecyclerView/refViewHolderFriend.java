package com.ezyindustries.goes_englishcourse.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ezyindustries.goes_englishcourse.R;

public class refViewHolderFriend extends RecyclerView.ViewHolder{

    public TextView Name,Description;
    public ImageView Simage;
    public ProgressBar loading;

    public refViewHolderFriend(@NonNull View itemView) {
        super(itemView);

        Name = (TextView) itemView.findViewById(R.id.Name);
        Simage = (ImageView) itemView.findViewById(R.id.profile_image);
        Description = (TextView) itemView.findViewById(R.id.Describe);
        loading = (ProgressBar) itemView.findViewById(R.id.loading);
    }

}
