package com.lihang.nbadapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lihang.nbadapter.R;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class MainHolder extends RecyclerView.ViewHolder {
    public TextView txt_index;
    public TextView txt_title;
    public TextView txt_des;

    public MainHolder(@NonNull View itemView) {
        super(itemView);
        txt_index = itemView.findViewById(R.id.txt_index);
        txt_title = itemView.findViewById(R.id.txt_title);
        txt_des = itemView.findViewById(R.id.txt_des);
    }
}
