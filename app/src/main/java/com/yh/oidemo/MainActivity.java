package com.yh.oidemo;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.yh.basemodule.base.BaseActivity;
import com.yh.oidemo.activitys.DrawTextActivity;
import com.yh.oidemo.activitys.FlowLayoutActivity;
import com.yh.oidemo.activitys.MaterialDesignActivity;
import com.yh.oidemo.activitys.PathEffectActivity;
import com.yh.oidemo.activitys.PathMeasureActivity;
import com.yh.oidemo.activitys.XfermodeActivity;
import com.yh.oidemo.util.ItemClickSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        Collections.addAll(funData, "FileMaker", "PathMeasure", "PathEffect", "DrawText", "标签",
                "Xfermode", "材料设计");
        funAdapter = new FunAdapter(funData);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        itemRecycleView.setLayoutManager(manager);
        itemRecycleView.setAdapter(funAdapter);
    }

    // 定义一个新字段表示相册数量
    private static final String BUCKET_ID_COUNT = "bucket_id_count";

    // 查询字段相册封面，相册编号和自己定义的字段：相册编号数量
    private static final String[] PROJECTIONS = {MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_ID, "COUNT(" + MediaStore.Images.Media.BUCKET_ID + ") as " + BUCKET_ID_COUNT};

    // 搜索条件：相册编号不为空，并且以相册编号分组，注意Android的Selection底层实际上用括号括起来的sql语句，所以这里括号可以对应
    private static final String SELECTION = MediaStore.Images.Media.BUCKET_ID + " is not null) group by (" + MediaStore.Images.Media.BUCKET_ID;

    @Override
    protected void initEvents() {
//        //执行查询
//        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, PROJECTIONS, SELECTION, null, MediaStore.Images.Media.DATE_MODIFIED + " DESC");
//        if (cursor == null) {
//            return;
//        }
//        while (cursor.moveToNext()) {
//            // 查询处理的数量是相册的数量，其中size是每个相册的图片个数
//            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//            long size = cursor.getLong(cursor.getColumnIndex(BUCKET_ID_COUNT));
//        }
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
                    case 6: // 材料设计
                        startActivity(MaterialDesignActivity.getIntent(MainActivity.this));
                        break;
                }
            }
        });
    }
}
