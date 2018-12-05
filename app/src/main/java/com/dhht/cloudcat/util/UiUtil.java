package com.dhht.cloudcat.util;

import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;

import com.dhht.cloudcat.R;

public class UiUtil {
    public static void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}
