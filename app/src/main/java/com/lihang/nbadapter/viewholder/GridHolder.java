package com.lihang.nbadapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lihang.nbadapter.R;

/**
 * Created by leo
 * on 2019/8/23.
 */
public class GridHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public GridHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
    }
}
