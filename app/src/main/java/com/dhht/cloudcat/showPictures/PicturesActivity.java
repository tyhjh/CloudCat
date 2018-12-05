package com.dhht.cloudcat.showPictures;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
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
    ObjectAnimator objectAnimator;

    DrawerLayout drawer;
    NavigationView nvView;
    String userName;

    ImageView ivAvatar, ivBigAvatar;
    TextView tvSignature;
    EditText tvUserName;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = SharedPreferencesUtil.getString(Const.Txt.userName, getResources().getString(R.string.userName));
        initView();
        initRcycleView();
        mPresenter = new PicturePresenter(this);
        mPresenter.getAllPic(userName);
    }

    private void initView() {
        FrameLayout fl_main = findViewById(R.id.fl_main);
        nvView = findViewById(R.id.nv_view);
        UiUtil.disableNavigationViewScrollbars(nvView);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {
                String newName=tvUserName.getText().toString().trim();
                if (!userName.equals(newName)) {
                    SharedPreferencesUtil.save(Const.Txt.userName, newName);
                    mPresenter.clearDatabase();
                    startActivity(new Intent(PicturesActivity.this, PicturesActivity.class));
                    finish();
                }
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        toggle.syncState();
        UltimateBar.Companion.with(this)
                .statusDark(true)
                .statusDrawable(getResources().getDrawable(R.drawable.colorPrimary))
                .create()
                .drawableBarDrawer(drawer, fl_main, nvView);

        tvUserName = nvView.getHeaderView(0).findViewById(R.id.tv_userName);
        tvSignature = nvView.getHeaderView(0).findViewById(R.id.tv_signature);
        ivBigAvatar = nvView.getHeaderView(0).findViewById(R.id.iv_bigAvatar);

        Glide.with(PicturesActivity.this)
                .load(R.drawable.ic_avatar_defult).
                apply(RequestOptions.circleCropTransform())
                .into(ivBigAvatar);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head_bg);
        int txtColor = UiUtil.getColor(bitmap, 5).getRgb();
        tvSignature.setTextColor(txtColor);
        tvUserName.setTextColor(txtColor);
        tvUserName.setText(userName);
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PicturePickUtil.setCreatNewFile(false);
                showChooseWayView();
            }
        });
        ivAvatar = findViewById(R.id.iv_avatar);
        Glide.with(PicturesActivity.this)
                .load(R.drawable.ic_oder_span).
                apply(RequestOptions.circleCropTransform())
                .apply(GlileUtil.getListPicOption())
                .into(ivAvatar);

        objectAnimator = ObjectAnimator.ofFloat(ivAvatar, "rotation", 0f, 90f);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(new OvershootInterpolator());

        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objectAnimator.start();
                VibrateUtil.vibrate(Const.Time.animVibrateTime);
                if (spanCount >= spanCountMax) {
                    spanCount = 1;
                } else {
                    spanCount++;
                }
                SharedPreferencesUtil.save(Const.Txt.spanCountTxt, spanCount);
                rvPictures.setLayoutManager(new GridLayoutManager(PicturesActivity.this, spanCount));
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
        spanCount = SharedPreferencesUtil.getInt(Const.Txt.spanCountTxt, Const.Number.defultSpanCount);
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

    /**
     * 展示，刷新图片
     *
     * @param pictureList
     */
    @Override
    public void showPic(List<Picture> pictureList) {
        mPictureAdapter.replaceData(pictureList);
    }

    /**
     * 删除图片
     *
     * @param position
     */
    @Override
    public void deletePic(int position) {
        mPictureAdapter.removeData(position);
    }

    /**
     * 添加图片
     *
     * @param picture
     */
    @Override
    public void addPic(Picture picture) {
        mPictureAdapter.insertData(0, picture);
    }

    @Override
    public void addPicFail(String msg) {
        SnackbarUtil.ShortSnackbar(ivAvatar, msg,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorPrimary)).show();
    }


    @Override
    public void uploadPicFinish() {
        SnackbarUtil.ShortSnackbar(ivAvatar,
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
