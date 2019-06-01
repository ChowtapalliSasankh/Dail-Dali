package com.accurate.dialdali;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.accurate.dialdali.utility.Constants;
import com.accurate.dialdali.utility.UtilClass;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Advertisement extends YouTubeBaseActivity {
    private YouTubePlayerView videoView;
    private ImageView fbView;
    private ImageView instaView;
    private TextView skipadd;
    private TextView welcome;
    public void initView()
    {
        videoView = (YouTubePlayerView)findViewById(R.id.videoview);
        fbView = (ImageView)findViewById(R.id.fblogo);
        instaView = (ImageView)findViewById(R.id.instalogo);
        skipadd = (TextView)findViewById(R.id.skipadd);
        welcome = (TextView)findViewById(R.id.welcometext);
        welcome.setTypeface(UtilClass.setCustomFont(getApplicationContext()));
        skipadd.setTypeface(UtilClass.setCustomFont(getApplicationContext()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        initView();
        Handler handler = new Handler();
        if(!UtilClass.isNetworkAvailable(getApplicationContext()))
        {

            showDialog(getString(R.string.text_nonetwork),getString(R.string.ok_text));

        }
        handler.postDelayed(task, 10000);
        YouTubePlayer.OnInitializedListener initializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                String url = UtilClass.getDataFromPref(getApplicationContext(), "YouTube_Link");
                String[] sub = url.split("=");
                youTubePlayer.loadVideo(sub[sub.length - 1]);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                showDialog(getString(R.string.error_text), getString(R.string.ok_text));
            }
        };
        videoView.initialize(Constants.API_KEY, initializedListener);
        fbView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Uri uri = Uri.parse(getString(R.string.facebookpage_url));
                try {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                    if (applicationInfo.enabled) {
                        uri = Uri.parse(getString(R.string.facebookpackage_text) + getString(R.string.facebookpage_url));
                    }
                } catch (PackageManager.NameNotFoundException ignored) {
                }
                Intent open = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(open);
            }
        });
        instaView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Uri uri = Uri.parse(getString(R.string.instagrampage_url));
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage(getString(R.string.instagrampackage_text));

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(getString(R.string.instagrampage_url))));
                }
            }
        });
        skipadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(Constants.BACK_VALUE) {
            super.onBackPressed();
        }
    }
    private Runnable task = new Runnable() {
        public void run()
        {
            welcome.setVisibility(View.INVISIBLE);
            skipadd.setVisibility(View.VISIBLE);
        }
    };
    public void showDialog(String message,String text)
    {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(Advertisement.this);
        View mview = getLayoutInflater().inflate(R.layout.dialog_layout,null);
        mbuilder.setView(mview);
        final AlertDialog dialog = mbuilder.create();
        dialog.setCancelable(false);
        ImageView close = (ImageView)mview.findViewById(R.id.dialogclosemark);
        TextView textview = (TextView)mview.findViewById(R.id.dialogtext);
        TextView texttitle = (TextView)mview.findViewById(R.id.dialogtitle);
        textview.setText(message);
        texttitle.setText(R.string.Error_heading);
        Button button = (Button)mview.findViewById(R.id.dialogbutton);
        button.setText(text);
        button.setBackgroundColor(ContextCompat.getColor(this,R.color.colorCardView));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }
}
