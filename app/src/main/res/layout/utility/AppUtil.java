package com.accurate.accurategems.utility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.accurate.accurategems.Model.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 10/7/2018.
 */

public class AppUtil
{
    public static Retrofit getRetrofitObj(){
    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static AlertDialog.Builder createDialogBox(final Context c, String title, String text)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage(text);
        builder.setTitle(title);
        builder.setPositiveButton("Exit App", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) c).finish();
            }
        });
        return builder;
    }
}
