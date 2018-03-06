package com.innovative.imagesdemo.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.innovative.imagesdemo.MainActivity;
import com.innovative.imagesdemo.config.AppConstant;

/**
 * Created by pulkit on 27/7/17.
 */

public class CommonUtil {
    public static boolean checkAndRequestPermission(Activity activity, String permission, int PERMISSION_CODE) {
        if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSION_CODE);
            return false;
        }
    }
}
