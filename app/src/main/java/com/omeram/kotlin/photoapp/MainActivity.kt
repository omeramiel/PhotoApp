package com.omeram.kotlin.photoapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.omeram.kotlin.photoapp.api.PhotoRetriever
import com.omeram.kotlin.photoapp.models.Photo
import com.omeram.kotlin.photoapp.models.PhotoList

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var photos: List<Photo>? = null
    var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        var retriever = PhotoRetriever()
        val callback = object : Callback<PhotoList> {
            override fun onFailure(call: Call<PhotoList>, t: Throwable) {
                Log.e(this@MainActivity.localClassName, "Problem calling API", t)
            }

            override fun onResponse(call: Call<PhotoList>, response: Response<PhotoList>) {
                response.isSuccessful.let {
                    photos = response.body()?.hits
                    adapter = MainAdapter(photos!!, this@MainActivity)
                    recyclerView.adapter = adapter
                }
            }

        }
        retriever.getPhotos(callback)

    }

    override fun onClick(view: View?) {
        val intent = Intent(this, DetailActivity::class.java)
        val holder = view?.tag as MainAdapter.PhotoViewHolder
        intent.putExtra(DetailActivity.PHOTO, adapter?.getPhoto(holder.adapterPosition))
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
