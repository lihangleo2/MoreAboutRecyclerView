package com.lihang.nbadapter.activity;

import android.view.View;

import com.lihang.nbadapter.R;
import com.lihang.nbadapter.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by leo
 * on 2019/8/27.
 */
public class SmartRefreshActivity extends BaseActivity {
    @Override
    public String getActivityTitle() {
        return getString(R.string.smart_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_smart_refresh;
    }

    @Override
    protected void processLogic() {

    }

    @OnClick({R.id.btn_normal, R.id.btn_pureScroll, R.id.btn_only_refresh, R.id.btn_hori_pure, R.id.btn_hori_smart})
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.btn_normal:
                transfer(SmartNormalActivity.class);
                break;
            case R.id.btn_pureScroll:
                transfer(SmartPureScrollActivity.class);
                break;
            case R.id.btn_only_refresh:
                transfer(SmartOnlyRefreshActivity.class);
                break;
            case R.id.btn_hori_pure:
                transfer(SmartHoriPureScrollActivity.class);
                break;
            case R.id.btn_hori_smart:
                transfer(SmartHoriNormalActivity.class);
                break;
        }
    }

}
