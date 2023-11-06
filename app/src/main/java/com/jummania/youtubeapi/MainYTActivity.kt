package com.jummania.youtubeapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainYTActivity : AppCompatActivity() {

    val arrayList = ArrayList<HashMap<String, String>>()
    private lateinit var progressBar: ProgressBar
    var hashMap: HashMap<String, String>? = null
    var jPlayer: WebYoutubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jPlayer = findViewById(R.id.jPlayer)
        progressBar = findViewById(R.id.progressBar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET,
            getString(R.string.URL),
            null,
            { response: JSONArray ->
                var jsonObject: JSONObject
                try {
                    for (x in 0 until response.length()) {
                        jsonObject = response.getJSONObject(x)
                        hashMap = HashMap()
                        hashMap!!["Id"] = jsonObject.getString("id")
                        hashMap!!["title"] = jsonObject.getString("title")
                        arrayList.add(hashMap!!)
                        recyclerView.adapter = Adapter()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                progressBar.visibility = View.GONE
            }) { _: VolleyError? ->
            progressBar.visibility = View.GONE
        }
        requestQueue.add(jsonArrayRequest)
    }

    private inner class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            hashMap = arrayList[position]
            val videoId = hashMap!!["Id"]
            Picasso.get().load("https://i.ytimg.com/vi/$videoId/0.jpg").into(holder.imageView)
            holder.textView.text = hashMap!!["title"]
            holder.itemView.setOnClickListener { v: View? ->
                jPlayer!!.visibility = View.VISIBLE
                jPlayer!!.loadVideoById(videoId!!, 0)
            }
        }

        override fun getItemCount(): Int {
            return arrayList.size
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageView: ImageView
            val textView: TextView

            init {
                imageView = itemView.findViewById(R.id.imageView)
                textView = itemView.findViewById(R.id.textView)
            }
        }
    }
}