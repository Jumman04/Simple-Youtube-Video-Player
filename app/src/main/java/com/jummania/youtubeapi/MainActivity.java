package com.jummania.youtubeapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    final ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ProgressBar progressBar;
    HashMap<String, String> hashMap;
    WebYoutubePlayer jPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jPlayer = findViewById(R.id.jPlayer);
        progressBar = findViewById(R.id.progressBar);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, getString(R.string.URL), null, response -> {


            JSONObject jsonObject;
            try {
                for (int x = 0; x < response.length(); x++) {
                    jsonObject = response.getJSONObject(x);

                    hashMap = new HashMap<>();

                    hashMap.put("Id", jsonObject.getString("id"));
                    hashMap.put("title", jsonObject.getString("title"));

                    arrayList.add(hashMap);

                    recyclerView.setAdapter(new Adapter());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            progressBar.setVisibility(View.GONE);

        }, error -> progressBar.setVisibility(View.GONE));

        requestQueue.add(jsonArrayRequest);

    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            hashMap = arrayList.get(position);
            String videoId = hashMap.get("Id");
            Picasso.get().load("https://i.ytimg.com/vi/" + videoId + "/0.jpg").into(holder.imageView);
            holder.textView.setText(hashMap.get("title"));
            holder.itemView.setOnClickListener(v -> {
                jPlayer.setVisibility(View.VISIBLE);
                if (videoId != null) jPlayer.loadVideoById(videoId, 0);

            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView imageView;
            final TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}