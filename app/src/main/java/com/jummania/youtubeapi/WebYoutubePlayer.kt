package com.jummania.youtubeapi


import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebView
import android.widget.Toast


class WebYoutubePlayer : WebView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(
        context,
        attributeSet,
        android.R.attr.webViewStyle
    )

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        onInit()
    }

    fun loadVideoById(videoId: String, position: Int) {
        loadUrl("javascript:player.loadVideoById('$videoId', $position);")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun onInit() {
        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = true
        settings.useWideViewPort = true
        settings.setSupportZoom(false)
        settings.builtInZoomControls = false
        settings.loadWithOverviewMode = true
        scrollBarStyle = SCROLLBARS_OUTSIDE_OVERLAY
        isScrollbarFadingEnabled = false


      // Toast.makeText(context,htmlData,Toast.LENGTH_SHORT).show()
        loadUrl("file:///android_asset/yt.html")
    }


    private val htmlData by lazy {
        val inp =  context.assets.open("yt.html")
        val data = byteArrayOf()
        inp.read(data)
        inp.close()
        String(data)
    }
}

