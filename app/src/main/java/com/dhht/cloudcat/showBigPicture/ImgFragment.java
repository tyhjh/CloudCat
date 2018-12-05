package com.dhht.cloudcat.showBigPicture;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dhht.cloudcat.R;
import com.dhht.cloudcat.app.Const;
import com.dhht.cloudcat.data.Picture;
import com.dhht.cloudcat.util.AppUtil;

import java.io.File;

import snackBar.SnackbarUtil;
import util.ClipbordUtil;
import util.VibrateUtil;



public class ImgFragment extends Fragment implements ImgContract.View {
    PinchImageView iv_action_img;
    Picture picture;
    ImgPresenter mImgPresenter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_big_pic, null, false);
        iv_action_img = view.findViewById(R.id.iv_action_img);
        mImgPresenter = new ImgPresenter(this);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        iv_action_img.setTransitionName("picture" + picture.getName());
        if (picture.getFile() != null) {
            Glide.with(getActivity())
                    .load(picture.getLocalPath())
                    .into(iv_action_img);
        } else {
            Glide.with(getActivity())
                    .load(picture.getRemotePath())
                    .thumbnail(Glide.with(getActivity()).load(picture.getRemoteMiniPath()))
                    .into(iv_action_img);
        }
        iv_action_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipbordUtil.copyTxt(picture.getRemotePath());
                SnackbarUtil.ShortSnackbar(iv_action_img,
                        "图片地址已复制到粘贴板",
                        getResources().getColor(R.color.white),
                        getResources().getColor(R.color.colorPrimary)).show();
            }
        });

        iv_action_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                VibrateUtil.vibrate(Const.Time.longClickVibrateTime);
                if (picture.getFile() == null) {
                    SnackbarUtil.snkbarWait(iv_action_img, "图片下载中");
                    getPresenter().downLoadFile(picture);
                } else {
                    SnackbarUtil.ShortSnackbar(iv_action_img,
                            "图片地址为：" + picture.getLocalPath(),
                            getResources().getColor(R.color.white),
                            getResources().getColor(R.color.colorPrimary)).show();
                }
                return true;
            }
        });

    }

    public void setPictures(Picture picture) {
        this.picture = picture;
    }

    @Override
    public void downLoadFinish(String path) {
        SnackbarUtil.ShortSnackbar(iv_action_img,
                "下载完成，图片地址为：" + path,
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorPrimary)).show();
        AppUtil.sendBroadcastToRefresh(new File(path),getActivity());
    }

    @Override
    public void downLoadFail() {
        SnackbarUtil.ShortSnackbar(iv_action_img,
                "图片下载失败^_^",
                getResources().getColor(R.color.white),
                getResources().getColor(R.color.colorPrimary)).show();
    }


    @Override
    public ImgPresenter getPresenter() {
        return mImgPresenter;
    }
}
