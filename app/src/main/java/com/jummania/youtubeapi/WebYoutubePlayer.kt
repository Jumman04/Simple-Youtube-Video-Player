package com.jummania.youtubeapi


import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView


class WebYoutubePlayer(context: Context, attrs: AttributeSet?) : WebView(context, attrs) {

    init {
        onInit()
    }

    fun loadVideoById(videoId: String, position: Int) {
        loadUrl("javascript:player.loadVideoById('$videoId', $position);")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun onInit() {
        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = true
        loadUrl("file:///android_asset/yt.html")
    }

}

