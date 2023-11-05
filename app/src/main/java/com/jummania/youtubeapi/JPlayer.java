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

        loadDataWithBaseURL("http://www.youtube.com",
                "<!DOCTYPE html>\n" + "<html>\n" + "   <style type=\"text/css\">\n" + "      iframe {\n" + "      position: absolute;\n" + "      border: none;\n" + "      height: 100%;\n" + "      width: 100%;\n" + "      top: 0;\n" + "      left: 0;\n" + "      bottom: 0;\n" + "      right: 0;\n" + "      }\n" + "   </style>\n" + "   <body>\n" + "      <div id=\"player\"></div>\n" + "      <script>\n" + "         var tag = document.createElement('script');\n" + "         tag.src = \"https://www.youtube.com/iframe_api\";\n" + "         var firstScriptTag = document.getElementsByTagName('script')[0];\n" + "         firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);\n" + "         \n" + "         function onYouTubeIframeAPIReady() {\n" + "           player = new YT.Player('player', {\n" + "             playerVars: {\n" + "               'playsinline': 0,\n" + "               'controls': 0,\n" + "               'playerVars': 0,\n" + "               'showinfo': 0\n" + "             \n" + "             }\n" + "            \n" + "           });\n" + "         }\n" + "         \n" + "      </script>\n" + "   </body>\n" + "</html>", "text/html", "UTF-8", null);
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
