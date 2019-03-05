package com.dhht.cloudcat.welcome;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.dhht.cloudcat.R;
import com.dhht.cloudcat.app.MyApplication;
import com.dhht.cloudcat.showPictures.PicturesActivity;

import permison.PermissonUtil;
import permison.listener.PermissionListener;

public class WelcomeActivity extends AppCompatActivity {

    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }



    @Override
    protected void onResume() {
        super.onResume();
        PermissonUtil.checkPermission(WelcomeActivity.this, new PermissionListener() {
            @Override
            public void havePermission() {
                MyApplication.initDir();
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
