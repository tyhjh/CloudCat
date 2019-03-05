package com.dhht.cloudcat.util;

import android.graphics.Bitmap;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v7.graphics.Palette;

public class UiUtil {
    public static void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


    /**
     * 获取bitmap颜色
     *
     * @param bitmap
     * @param color
     * @return
     */
    public static Palette.Swatch getColor(Bitmap bitmap, int color) {
        // Palette的部分
        Palette palette = Palette.generate(bitmap);
        Palette.Swatch swatche = null;
        if (palette != null) {
            switch (color) {
                case 0:
                    swatche = palette.getVibrantSwatch();
                    break;
                case 1:
                    swatche = palette.getLightVibrantSwatch();
                    break;
                case 2:
                    swatche = palette.getDarkVibrantSwatch();
                    break;
                case 3:
                    swatche = palette.getMutedSwatch();
                    break;
                case 4:
                    swatche = palette.getLightMutedSwatch();
                    break;
                case 5:
                    swatche = palette.getDarkMutedSwatch();
                    break;
                default:
                    swatche = palette.getVibrantSwatch();
                    break;
            }
            if (swatche == null) {
                swatche = palette.getVibrantSwatch();
            }
        }
        return swatche;
    }

}
