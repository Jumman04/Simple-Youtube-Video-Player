package com.jummania.youtubeapi

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONObject


class MainActivityKotlin : AppCompatActivity() {

    val arrayList = ArrayList<HashMap<String, String>>()
    private lateinit var progressBar: ProgressBar
    private lateinit var hashMap: HashMap<String, String>
    private lateinit var jPlayer: JPlayerKotlin
    private var width = Resources.getSystem().displayMetrics.heightPixels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jPlayer = findViewById(R.id.jPlayer)
        progressBar = findViewById(R.id.progressBar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val requestQueue = Volley.newRequestQueue(this)

        val jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, getString(R.string.URL), null, { response ->

                var jsonObject: JSONObject
                try {
                    for (x in 0 until response.length()) {
                        jsonObject = response.getJSONObject(x)
                        hashMap = HashMap()
                        hashMap["id"] = jsonObject.getString("id")
                        hashMap["title"] = jsonObject.getString("title")
                        arrayList.add(hashMap)
                    }
                    recyclerView.adapter = Adapter()
                    progressBar.visibility = View.GONE
                } catch (error: Exception) {
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                }

            }, { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(jsonArrayRequest)

        jPlayer.layoutParams.height = (width * 0.3).toInt()


    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                layoutInflater.inflate(R.layout.item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            hashMap = arrayList[position]
            val videoId = hashMap["id"]

            Picasso.get().load("https://i.ytimg.com/vi/$videoId/0.jpg").into(holder.imageView)
            holder.textView.text = hashMap["title"]
            holder.itemView.setOnClickListener {
                jPlayer.visibility = View.VISIBLE
                jPlayer.loadVideoById(videoId!!, 0)
            }
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val textView: TextView = itemView.findViewById(R.id.textView)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) jPlayer.layoutParams.height =
            (width * 0.3).toInt()
        else jPlayer.layoutParams.height = LayoutParams.MATCH_PARENT

    }
}