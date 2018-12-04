package com.dhht.cloudcat.showBigPicture;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dhht.cloudcat.R;
import com.dhht.cloudcat.data.Picture;

import java.util.ArrayList;
import java.util.List;

import log.LogUtils;

public class BigPicturesActivity extends AppCompatActivity {

    public static List<Picture> pictureList;
    public static int currenPosition;
    ViewPager vp_pics;
    ConstraintLayout cst_back;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_big_pictures);
        vp_pics = findViewById(R.id.vp_pics);
        cst_back = findViewById(R.id.cst_back);
        initViewPager();
        cst_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    private void initViewPager() {
        vp_pics.setOffscreenPageLimit(1);
        vp_pics.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                ImgFragment imgFragment = new ImgFragment();
                imgFragment.setPictures(pictureList.get(position));
                return imgFragment;
            }

            @Override
            public int getCount() {
                return pictureList.size();
            }



        });
        vp_pics.setCurrentItem(currenPosition);
    }

    public static void setPictureList(List<Picture> pictureList) {
        BigPicturesActivity.pictureList = pictureList;
    }

    public static void setCurrenPosition(int currenPosition) {
        BigPicturesActivity.currenPosition = currenPosition;
    }


}
