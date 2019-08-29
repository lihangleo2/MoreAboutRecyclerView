package com.lihang.nbadapter;

/**
 * Created by leo
 * on 2019/8/26.
 */
public enum AnimationType {
    TRANSLATE_FROM_RIGHT(R.anim.item_translate_byright),
    TRANSLATE_FROM_LEFT(R.anim.item_translate_byleft),
    TRANSLATE_FROM_BOTTOM(R.anim.item_translate_bybottom),
    SCALE(R.anim.item_scale_anim),
    ALPHA(R.anim.item_alpha_anim);

    private int resId;
    AnimationType(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
