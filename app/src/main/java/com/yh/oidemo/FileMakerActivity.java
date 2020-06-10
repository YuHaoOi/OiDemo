package com.yh.oidemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yh.basemodule.base.BaseActivity;

public class FileMakerActivity extends BaseActivity implements View.OnClickListener {

    private Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_maker);
    }

    @Override
    protected void initViews() {
        createBtn = findViewById(R.id.create_btn);
    }

    @Override
    protected void initEvents() {
        createBtn.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_btn:

                break;
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FileMakerActivity.class);
        context.startActivity(intent);
    }
}
