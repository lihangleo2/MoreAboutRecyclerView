package com.lihang.nbadapter.activity;

import android.animation.Animator;
import com.lihang.nbadapter.R;
import com.lihang.nbadapter.base.BaseActivity;
import com.lihang.smartloadview.SmartLoadingView;

import butterknife.BindView;

/**
 * Created by leo
 * on 2019/8/22.
 */
public class AnimationActivity extends BaseActivity {

    //这是我自定义view,现在用起来发现，代码确实比较麻烦，后期优化
    @BindView(R.id.smartLoadingView_linear)
    SmartLoadingView smartLoadingView;
    @BindView(R.id.smartLoadingView_grid)
    SmartLoadingView smartLoadingView_grid;
    @BindView(R.id.smartLoadingView_strag)
    SmartLoadingView smartLoadingView_strag;
    @BindView(R.id.smartLoadingView_withMuti)
    SmartLoadingView smartLoadingView_withMuti;

    @Override
    public String getActivityTitle() {
        return getString(R.string.animation_use);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_animation;
    }


    @Override
    protected void onResume() {
        super.onResume();
        smartLoadingView.reset();
        smartLoadingView_grid.reset();
        smartLoadingView_strag.reset();
        smartLoadingView_withMuti.reset();
    }

    @Override
    protected void processLogic() {
        setListener();
    }

    private void setListener() {
        smartLoadingView.setLoginClickListener(new SmartLoadingView.LoginClickListener() {
            @Override
            public void click() {
                smartLoadingView.loginSuccess(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        transfer(LinearAnimActivity.class);
                        overridePendingTransition(R.anim.alpha_activity_in, R.anim.alpha_activity_out);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

        smartLoadingView_grid.setLoginClickListener(new SmartLoadingView.LoginClickListener() {
            @Override
            public void click() {
                smartLoadingView_grid.loginSuccess(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        transfer(GridAnimActivity.class);
                        overridePendingTransition(R.anim.alpha_activity_in, R.anim.alpha_activity_out);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

        smartLoadingView_strag.setLoginClickListener(new SmartLoadingView.LoginClickListener() {
            @Override
            public void click() {
                smartLoadingView_strag.loginSuccess(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        transfer(PullAnimActivity.class);
                        overridePendingTransition(R.anim.alpha_activity_in, R.anim.alpha_activity_out);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });


        smartLoadingView_withMuti.setLoginClickListener(new SmartLoadingView.LoginClickListener() {
            @Override
            public void click() {
                smartLoadingView_withMuti.loginSuccess(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        transfer(MutifyActivity.class);
                        overridePendingTransition(R.anim.alpha_activity_in, R.anim.alpha_activity_out);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
    }





}
