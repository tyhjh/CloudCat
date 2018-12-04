package com.dhht.cloudcat.util;

import com.bumptech.glide.request.RequestOptions;
import com.dhht.cloudcat.R;

public class GlileUtil {

    RequestOptions options = new RequestOptions()
            .placeholder(R.color.whitesmoke)
            .error(R.color.whitesmoke)
            .override(88, 88);


    public static RequestOptions getListPicOption() {
        return new RequestOptions()
                .placeholder(R.color.whitesmoke)
                .error(R.color.whitesmoke)
                .dontAnimate();
    }
}
