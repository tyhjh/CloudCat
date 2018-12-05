package com.dhht.cloudcat.showPictures;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dhht.cloudcat.R;
import com.dhht.cloudcat.app.Const;
import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.showBigPicture.BigPicturesActivity;
import com.dhht.cloudcat.util.GlileUtil;
import com.dhht.cloudcat.util.UiUtil;
import com.example.bottomsheet.BottomFragment;
import com.example.recyclelibrary.CommonAdapter;
import com.example.recyclelibrary.CommonViewHolder;
import com.github.zackratos.ultimatebar.UltimateBar;
import com.yorhp.picturepick.OnPickListener;
import com.yorhp.picturepick.PicturePickUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import snackBar.SnackbarUtil;
import util.ScreenUtil;
import util.SharedPreferencesUtil;
import util.VibrateUtil;

public class PicturesActivity extends AppCompatActivity implements PicturesContract.View {

    RecyclerView rvPictures;
    CommonAdapter<Picture> mPictureAdapter;
    PicturePresenter mPresenter;
    private static int spanCount = 4;
    private static int spanCountMax = 10;

    DrawerLayout drawer;
    NavigationView nv_view;

    ImageView iv_avatar, iv_bigAvatar;
    TextView tv_userName, tv_signature;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new PicturePresenter(this);
        mPresenter.getAllPic("Tyhj");
        initRcycleView();
        initView();
    }

    private void initView() {
        FrameLayout fl_main = findViewById(R.id.fl_main);
        nv_view = findViewById(R.id.nv_view);
        UiUtil.disableNavigationViewScrollbars(nv_view);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        UltimateBar.Companion.with(this)
                .statusDrawable(getResources().getDrawable(R.drawable.colorPrimary))           // 状态栏背景，默认 null
                .create()
                .drawableBarDrawer(drawer, fl_main, nv_view);


        iv_bigAvatar=nv_view.getHeaderView(0).findViewById(R.id.iv_bigAvatar);

        Glide.with(PicturesActivity.this)
                .load(R.drawable.ic_avatar_defult).
                apply(RequestOptions.circleCropTransform())
                .into(iv_bigAvatar);



        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PicturePickUtil.setCreatNewFile(false);
                showChooseWayView();
            }
        });
        iv_avatar = findViewById(R.id.iv_avatar);
        Glide.with(PicturesActivity.this)
                .load(R.drawable.ic_avatar_defult).
                apply(RequestOptions.circleCropTransform())
                .apply(GlileUtil.getListPicOption())
                .into(iv_avatar);

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if (spanCount >= spanCountMax) {
                    spanCount = 1;
                } else {
                    spanCount++;
                }
                SharedPreferencesUtil.save(getResources().getString(R.string.spanCount), spanCount);
                rvPictures.setLayoutManager(new GridLayoutManager(PicturesActivity.this, spanCount));*/
                drawer.openDrawer(GravityCompat.START);
            }
        });

    }


    private void showChooseWayView() {
        View bottomsheetView = LayoutInflater.from(PicturesActivity.this).inflate(R.layout.bottomsheet_chooseway, null);
        final BottomFragment fragment = new BottomFragment();
        fragment.setView(bottomsheetView);
        fragment.setBackgroundDim(true);
        fragment.show(getFragmentManager(), "bottomsheetView");
        bottomsheetView.findViewById(R.id.camoral).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.dismiss();
                PicturePickUtil.pickByCamera(PicturesActivity.this, new OnPickListener() {
                    @Override
                    public void pickPicture(File file) {
                        mPresenter.addPic(file, mPictureAdapter.getDatas());
                    }
                });
            }
        });
        bottomsheetView.findViewById(R.id.images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.dismiss();
                PicturePickUtil.pickByAlbum(PicturesActivity.this, new OnPickListener() {
                    @Override
                    public void pickPicture(File file) {
                        mPresenter.addPic(file, mPictureAdapter.getDatas());
                    }
                });
            }
        });

    }

    /**
     * 初始化recyclerView
     */
    private void initRcycleView() {
        spanCount = SharedPreferencesUtil.getInt(getResources().getString(R.string.spanCount), 4);
        rvPictures = findViewById(R.id.rv_pictures);
        mPictureAdapter = new CommonAdapter<Picture>(this, new ArrayList<Picture>(0), R.layout.item_picture) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onBindView(final CommonViewHolder commonViewHolder, final Picture picture) {
                final ImageView imageView = commonViewHolder.getView(R.id.iv_pic);
                int leath = ScreenUtil.screenWidth / spanCount;
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(leath, leath));

                if (picture.getFile() != null) {
                    Glide.with(PicturesActivity.this)
                            .load(picture.getLocalPath())
                            .apply(GlileUtil.getListPicOption())
                            .into(imageView);
                } else {
                    Glide.with(PicturesActivity.this)
                            .load(picture.getRemoteMiniPath())
                            .apply(GlileUtil.getListPicOption())
                            .into(imageView);
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PicturesActivity.this, BigPicturesActivity.class);
                        BigPicturesActivity.setPictureList(mPictureAdapter.getDatas());
                        BigPicturesActivity.setCurrenPosition(commonViewHolder.getAdapterPosition());
                        startActivity(intent);
                    }
                });

                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        VibrateUtil.vibrate(Const.Time.longClickVibrateTime);
                        mPresenter.deletePic(picture);
                        deletePic(commonViewHolder.getAdapterPosition());
                        return false;
                    }
                });
            }
        };
        rvPictures.setLayoutManager(new GridLayoutManager(PicturesActivity.this, spanCount));
        rvPictures.setAdapter(mPictureAdapter);
    }

    //展示，刷新图片
    @Override
    public void showPic(List<Picture> pictureList) {
        mPictureAdapter.replaceData(pictureList);
    }

    //删除图片
    @Override
    public void deletePic(int position) {
        mPictureAdapter.removeData(position);
    }

    //添加图片
    @Override
    public void addPic(Picture picture) {
        mPictureAdapter.insertData(0, picture);
    }

    @Override
    public void addPicFail(String msg) {
        SnackbarUtil.ShortSnackbar(iv_avatar, msg,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorPrimary)).show();
    }


    @Override
    public void uploadPicFinish() {
        SnackbarUtil.ShortSnackbar(iv_avatar,
                getResources().getString(R.string.uploadPicOk),
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorPrimary)).show();
    }


    @Override
    public PicturePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(false);
        }
    }


}
