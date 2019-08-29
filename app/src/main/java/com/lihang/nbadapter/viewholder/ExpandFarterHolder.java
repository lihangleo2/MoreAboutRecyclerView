package com.lihang.nbadapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lihang.nbadapter.R;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class ExpandFarterHolder extends RecyclerView.ViewHolder {
    public TextView txt_title;
    public ImageView image_gou;

    public ExpandFarterHolder(@NonNull View itemView) {
        super(itemView);
        txt_title = itemView.findViewById(R.id.txt_title);
        image_gou = itemView.findViewById(R.id.image_gou);
    }
}
