package com.yh.oidemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.R;
import com.yh.oidemo.views.PathPainter;
import com.yh.oidemo.views.PathTan;

public class PathMeasureActivity extends BaseActivity implements View.OnClickListener {

    private PathPainter pathPainter;
    private PathTan pathTan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);
    }

    @Override
    protected void initViews() {
        Button getSegmentBtn = findViewById(R.id.get_segment_btn);
        getSegmentBtn.setOnClickListener(this);
        Button pathTanBtn = findViewById(R.id.get_pos_tan);
        pathTanBtn.setOnClickListener(this);

        pathPainter = findViewById(R.id.path_painter1);
        pathTan = findViewById(R.id.path_tan);

    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PathMeasureActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_segment_btn:
                pathPainter.startAnim();
                break;
            case R.id.get_pos_tan:
                pathTan.startAnim();
                break;
        }
    }
}
