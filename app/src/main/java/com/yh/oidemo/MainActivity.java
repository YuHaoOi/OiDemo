package com.yh.oidemo;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.activitys.DrawTextActivity;
import com.yh.oidemo.activitys.FlowLayoutActivity;
import com.yh.oidemo.activitys.PathEffectActivity;
import com.yh.oidemo.activitys.PathMeasureActivity;
import com.yh.oidemo.activitys.XfermodeActivity;
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
        Collections.addAll(funData, "FileMaker", "PathMeasure", "PathEffect", "DrawText", "标签", "Xfermode");
        funAdapter = new FunAdapter(funData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        itemRecycleView.setLayoutManager(manager);
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
                switch (position) {
                    case 0:
                        FileMakerActivity.startActivity(MainActivity.this);
                        break;
                    case 1:
                        PathMeasureActivity.actionStart(MainActivity.this);
                        break;
                    case 2:
                        PathEffectActivity.actionStart(MainActivity.this);
                        break;
                    case 3:
                        DrawTextActivity.actionStart(MainActivity.this);
                        break;
                    case 4:
                        startActivity(FlowLayoutActivity.getIntent(MainActivity.this));
                        break;
                    case 5:
                        startActivity(XfermodeActivity.getIntent(MainActivity.this));
                        break;
                }
            }
        });
    }
}
