package com.dhht.cloudcat.showPictures;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.dhht.cloudcat.R;
import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.showBigPicture.BigPicturesActivity;
import com.dhht.cloudcat.util.GlileUtil;
import com.dhht.cloudcat.util.SharedPreferenceUtil;
import com.example.bottomsheet.BottomFragment;
import com.example.recyclelibrary.CommonAdapter;
import com.example.recyclelibrary.CommonViewHolder;
import com.yorhp.picturepick.OnPickListener;
import com.yorhp.picturepick.PicturePickUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import snackBar.SnackbarUtil;
import util.ScreenUtil;
import util.UiUtil;

public class PicturesActivity extends AppCompatActivity implements PicturesContract.View {

    RecyclerView rvPictures;
    CommonAdapter<Picture> mPictureAdapter;
    PicturesContract.Presenter mPresenter;
    private static int spanCount = 3;
    private static int spanCountMax = 10;

    ImageView iv_avatar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new PicturePresenter(this);
        mPresenter.getAllPic("Tyhj");

        initRcycleView();
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PicturePickUtil.setCreatNewFile(false);
                showChooseWayView();
            }
        });
        iv_avatar = findViewById(R.id.iv_avatar);
        Glide.with(PicturesActivity.this).load(R.drawable.ic_avatar_defult).into(iv_avatar);
        UiUtil.setCircleShape(iv_avatar, 20);

        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (spanCount >= spanCountMax) {
                    spanCount = 1;
                } else {
                    spanCount++;
                }
                SharedPreferenceUtil.saveInt(getResources().getString(R.string.spanCount),spanCount);
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
        spanCount = SharedPreferenceUtil.getIntValue(getResources().getString(R.string.spanCount));
        rvPictures = findViewById(R.id.rv_pictures);
        mPictureAdapter = new CommonAdapter<Picture>(this, new ArrayList<Picture>(0), R.layout.item_picture) {
            @Override
            public void onBindView(final CommonViewHolder commonViewHolder, final Picture picture) {
                ImageView imageView = commonViewHolder.getView(R.id.iv_pic);
                int leath = ScreenUtil.SCREEN_WIDTH / spanCount;
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(leath, (int) (leath * 0.98)));

                if (picture.getFile() != null) {
                    Glide.with(PicturesActivity.this)
                            .load(picture.getFile())
                            .apply(GlileUtil.getListPicOption(leath, (int) (leath * 0.98)))
                            .into(imageView);
                } else {
                    Glide.with(PicturesActivity.this)
                            .load(picture.getRemoteMiniPath())
                            .apply(GlileUtil.getListPicOption(leath, (int) (leath * 0.98)))
                            .into(imageView);
                }

                imageView.setOnClickListener(new View.OnClickListener() {
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
                        mPresenter.deletePic(picture);
                        deletePic(commonViewHolder.getAdapterPosition());
                        return true;
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


}
