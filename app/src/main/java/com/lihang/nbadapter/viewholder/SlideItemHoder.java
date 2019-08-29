package com.lihang.nbadapter.viewholder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.base.SwipBaseHolder;
import com.lihang.nbadapter.utils.UIUtil;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class SlideItemHoder extends SwipBaseHolder {
    public RelativeLayout slide_itemView;
    public TextView txt_text;
    public TextView zhiding, shanchu;

    public SlideItemHoder(@NonNull View itemView) {
        super(itemView);
        slide_itemView = itemView.findViewById(R.id.slide_itemView);
        txt_text = itemView.findViewById(R.id.txt_text);
        zhiding = itemView.findViewById(R.id.zhiding);
        shanchu = itemView.findViewById(R.id.shanchu);
    }

    @Override
    public int getSlidItemWith() {
        return (int) itemView.getContext().getResources().getDimension(R.dimen.dp_160);
    }
}
