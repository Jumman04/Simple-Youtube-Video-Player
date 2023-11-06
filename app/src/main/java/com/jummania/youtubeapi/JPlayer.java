package com.jummania.youtubeapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class JPlayer extends WebView {


    @SuppressLint("SetJavaScriptEnabled")
    public JPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        getSettings().setJavaScriptEnabled(true);
        getSettings().setMediaPlaybackRequiresUserGesture(false);
        loadUrl("file:///android_asset/yt.html");

    }

    public void loadVideoById(String videoId, int position) {
        loadUrl("javascript:player.loadVideoById('" + videoId + "', " + position + ");");
    }

    public void pause() {
        loadUrl("javascript:player.pauseVideo();");
    }

    public void play() {
        loadUrl("javascript:player.playVideo();");
    }


}
