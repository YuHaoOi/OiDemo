package com.yh.oidemo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView itemRecycleView;
    private FunAdapter funAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        itemRecycleView = findViewById(R.id.item_rv);
        List<String> funData = new ArrayList<>();
        Collections.addAll(funData, "FileMaker");
        funAdapter = new FunAdapter(funData);
        itemRecycleView.setAdapter(funAdapter);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {
        ItemClickSupport.addTo(itemRecycleView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //这里的position从1开始
                switch (position) {
                    case 1:
                        FileMakerActivity.startActivity(MainActivity.this);
                        break;
                }
            }
        });
    }
}
