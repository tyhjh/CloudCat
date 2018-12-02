package com.dhht.cloudcat.welcome;

import android.Manifest;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dhht.cloudcat.R;
import com.dhht.cloudcat.showPictures.PicturesActivity;

import permison.PermissonUtil;
import permison.listener.PermissionListener;
import util.ScreenUtil;

public class WelcomeActivity extends AppCompatActivity {

    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ScreenUtil.initScreenSize(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissonUtil.checkPermission(WelcomeActivity.this, new PermissionListener() {
            @Override
            public void havePermission() {
                startActivity(new Intent(WelcomeActivity.this, PicturesActivity.class));
                finish();
            }

            @Override
            public void requestPermissionFail() {
                finish();
            }
        }, permissions);
    }
}
