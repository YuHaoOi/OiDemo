package com.yh.oidemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.R;
import com.yh.oidemo.views.PathPainterEffect;

public class PathEffectActivity extends BaseActivity implements View.OnClickListener {

    private PathPainterEffect pathPainterEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_effect);
    }

    @Override
    protected void initViews() {
        Button dashPathEffectBtn = findViewById(R.id.dash_path_effect_btn);
        dashPathEffectBtn.setOnClickListener(this);

        pathPainterEffect = findViewById(R.id.dash_paint_effect);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dash_path_effect_btn:
                pathPainterEffect.startAnim();
                break;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PathEffectActivity.class);
        context.startActivity(intent);
    }


}
