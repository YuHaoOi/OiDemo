package com.yh.basemodule.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.yh.basemodule.R;
import com.yh.basemodule.utils.StatusBarUtil;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public abstract class BaseActivity extends AppCompatActivity implements OnTitleEvent {
    protected ProgressDialog waitDialog;
    public Title title;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        AppManager.activityCreated(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initViews();
        initEvents();
        initData();
    }

    protected abstract void initViews();

    protected abstract void initEvents();

    protected abstract void initData();

    protected void showTitle(String name) {
        supportTitle(true);
        title.setTitleText(name);
        title.setLeftImage(R.mipmap.aaa_back);
        StatusBarUtil.setOiStatusBarColor(this);
        StatusBarUtil.StatusBarDarkMode(this);
    }


    @Override
    protected void onDestroy() {
        AppManager.activityDestroyed(this);
        super.onDestroy();
    }

    public void supportTitle(boolean isSupport) {
        if (isSupport) {
            View v = findViewById(R.id.title);
            if (v != null) {
                title = new Title(v, this);
            } else {
                try {
                    throw new Exception("Cannot find Title");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onLeftClick(View view) {
        hideSoftKeyboard();
        finish();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onCenterClick(View view) {

    }

    public void setLoading(boolean isLoading) {
        try {
            if (isLoading) {
                if (waitDialog == null || !waitDialog.isShowing()) {
                    waitDialog = new ProgressDialog(BaseActivity.this, R.style.MyDialogStyleBottom);
                    waitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    waitDialog.setCanceledOnTouchOutside(false);
                    ImageView view = new ImageView(BaseActivity.this);
                    view.setLayoutParams(new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    Animation loadAnimation = AnimationUtils.loadAnimation(
                            BaseActivity.this, R.anim.rotate);
                    view.startAnimation(loadAnimation);
                    loadAnimation.start();
                    view.setImageResource(R.mipmap.loading);
                    waitDialog.show();
                    waitDialog.setContentView(view);
                }
            } else {
                if (waitDialog != null && waitDialog.isShowing()) {
                    waitDialog.dismiss();
                    waitDialog = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void toast(CharSequence toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void hideSoftKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
