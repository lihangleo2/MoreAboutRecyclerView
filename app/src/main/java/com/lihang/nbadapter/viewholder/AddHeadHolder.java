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
public class AddHeadHolder extends RecyclerView.ViewHolder {
    public TextView txt;
    public AddHeadHolder(@NonNull View itemView) {
        super(itemView);
        txt = itemView.findViewById(R.id.txt);
    }
}
