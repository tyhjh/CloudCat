package com.dhht.cloudcat.showBigPicture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dhht.cloudcat.R;
import com.dhht.cloudcat.data.Picture;


public class ImgFragment extends Fragment {
    PinchImageView iv_action_img;
    Picture picture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iv_action_img = (PinchImageView) inflater.inflate(R.layout.fragment_big_pic, null, false);
        return iv_action_img;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        if (picture.getFile() != null) {
            Glide.with(getActivity())
                    .load(picture.getFile())
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
                getActivity().finish();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    public void setPictures(Picture picture) {
        this.picture = picture;
    }
}
