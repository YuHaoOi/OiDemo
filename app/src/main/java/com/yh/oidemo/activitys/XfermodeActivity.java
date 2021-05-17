package com.yh.oidemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.R;

public class XfermodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfermode);
    }


    public static Intent getIntent(Context context) {
        return new Intent(context, XfermodeActivity.class);
    }
}
