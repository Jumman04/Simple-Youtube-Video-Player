package com.jummania.youtubeapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    YouTubePlayer jummania_youtube_player;

    ProgressBar progressBar;
    ListView listView;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        youTubePlayerView = findViewById(R.id.youtube);
        listView = findViewById(R.id.listView);


        youTubePlayerView.initialize("Jumman", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

                jummania_youtube_player = youTubePlayer;

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://test-jumman.000webhostapp.com/test/json.json";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                for (int x = 0; x < response.length(); x++) {

                    JSONObject jsonObject = null;
                    try {
                        jsonObject = response.getJSONObject(x);

                        hashMap = new HashMap<>();

                        hashMap.put("Id", jsonObject.getString("Id"));

                        arrayList.add(hashMap);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                listView.setAdapter(new MyAdepter());

                progressBar.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        });

        requestQueue.add(jsonArrayRequest);


    }

    private class MyAdepter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();

            View myView = layoutInflater.inflate(R.layout.item, viewGroup, false);


            ImageView imageView = myView.findViewById(R.id.imageView);

            hashMap = arrayList.get(i);


            Picasso.get()
                    .load("https://i.ytimg.com/vi/" + hashMap.get("Id") + "/0.jpg")
                    .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jummania_youtube_player.loadVideo(hashMap.get("Id"));
                    youTubePlayerView.setVisibility(View.VISIBLE);
                }
            });


            return myView;
        }
    }
}