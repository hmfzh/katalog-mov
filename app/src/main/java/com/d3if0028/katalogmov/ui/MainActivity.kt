package com.d3if0028.katalogmov.ui

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.MovieResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val Tag:String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onStart() {
        super.onStart()
        getMovie()
    }

    fun getMovie(){
        ApiService().endpoint.getMovieNowPlaying(Constant.API_KEY,1)
                .enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(call: Call<MovieResponse>,
                                            response: Response<MovieResponse>) {
                        if(response.isSuccessful){
                            showMovie(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d(Tag,"errorResponse: $t")
                    }

                })
    }

    fun showMovie(response:MovieResponse){
        Log.d(Tag,"responseMovie: $response")
        Log.d(Tag,"total_pages: ${response.total_pages}")

        for (movie in response.results){
            Log.d(Tag,"movie_title: ${movie.title}" )
        }
    }

    fun showMessage(msg:String){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
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