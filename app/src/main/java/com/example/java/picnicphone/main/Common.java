package com.example.java.picnicphone.main;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by yenshen on 2017/6/24.
 */

public class Common {

    public static String URL = "http://10.0.2.2:8081/Picnic_Oracle_Web_Phone/";

    // check if the device connect to the network
    //  檢查網路是否已連線
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected(); // 回傳部林值

        // 上行相當於
        // if (networkInfo != null && networkInfo.isConnected()){
        //     return true;
        // }else{
        //     return false;
        // }
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
