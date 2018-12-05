package com.ezyindustries.goes_englishcourse.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezyindustries.goes_englishcourse.R;

public class refViewHolderLearn extends RecyclerView.ViewHolder{

    public TextView Title;

    public refViewHolderLearn(@NonNull View itemView) {
        super(itemView);

        Title = (TextView) itemView.findViewById(R.id.Judul);

    }
}
