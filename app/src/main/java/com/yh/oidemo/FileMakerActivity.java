package com.yh.oidemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.yh.basemodule.base.BaseActivity;
import com.yh.basemodule.utils.FileMaker;

public class FileMakerActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 10001;
    private static final int PERMISSION_REQUEST_CODE = 10002;

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
                createFile();
                break;
        }
    }

    private void createFile() {
        // SDK 23(6.0)以上需要动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (isGranted != PackageManager.PERMISSION_GRANTED) {
                // 如果用户拒绝了权限，返回true
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    toast("权限之前被拒绝了");
                }
                // 申请权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            } else {
                createFolder();
            }
        } else {
            createFolder();
        }
    }

    private void createFolder() {
        FileMaker fileMaker = FileMaker.getInstance();
        fileMaker.setMainPath(getString(R.string.app_name));
        fileMaker.createFolder(OiConstants.FOLDER_DATA, "data");
        fileMaker.createFolder(OiConstants.FOLDER_IMAGE, "image");
        fileMaker.createFolder(OiConstants.FOLDER_CACHE, "cache");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toast("已经获取到权限");
                } else {
                    // 点击了拒绝，但是没点击不再提醒
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        toast("用户拒绝了权限");
                    } else {
                        // 点击了不在提醒
                        toast("点击了不在提醒");
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                int isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (isGranted != PackageManager.PERMISSION_GRANTED) {
                    toast("权限还是没有开启");
                } else {
                    toast("权限开启了");
                    createFolder();
                }
                break;
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FileMakerActivity.class);
        context.startActivity(intent);
    }
}
