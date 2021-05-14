package com.yh.oidemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.R;
import com.yh.oidemo.views.ProgressTextView;

public class DrawTextActivity extends BaseActivity implements View.OnClickListener {

    private ProgressTextView progressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_text);
    }

    @Override
    protected void initViews() {
        Button drawProgressBtn = findViewById(R.id.draw_progress_btn);
        drawProgressBtn.setOnClickListener(this);
        progressTextView = findViewById(R.id.progress_view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.draw_progress_btn:
                progressTextView.startAnim();
                break;
        }
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DrawTextActivity.class);
        context.startActivity(intent);
    }
}
