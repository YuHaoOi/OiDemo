package com.yh.oidemo.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.R;
import com.yh.oidemo.flowlayout.FlowLayout;
import com.yh.oidemo.flowlayout.TagAdapter;
import com.yh.oidemo.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FlowLayoutActivity extends BaseActivity {

    private List<String> tags = new ArrayList<>(Long.SIZE);

    private TagFlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        mFlowLayout = findViewById(R.id.id_flowlayout);
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }
        mFlowLayout.setAdapter(new TagAdapter<String>(tags) {

            @Override
            public View getView(FlowLayout parent, int position, String value) {
                TextView tv = (TextView) LayoutInflater.from(FlowLayoutActivity.this).inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(value);
                return tv;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return s.equals("标签5");
            }
        });

        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public void onTagClick(View view, int position, FlowLayout parent) {
                Toast.makeText(FlowLayoutActivity.this, tags.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                toast("choose:" + selectPosSet.toString());
            }
        });
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    public static Intent getIntent(Context context) {
        return new Intent(context, FlowLayoutActivity.class);
    }
}
