package com.lihang.nbadapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.lihang.nbadapter.R;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class PagerUseHolder extends RecyclerView.ViewHolder {
    public ImageView image;

    public PagerUseHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
    }
}
