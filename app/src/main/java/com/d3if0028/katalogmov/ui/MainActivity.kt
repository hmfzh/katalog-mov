package com.d3if0028.katalogmov.ui

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.adapter.MainAdapter
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.MovieResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val moviePopular = 0
const val moveNowPlaying = 1

class MainActivity : AppCompatActivity() {

    private val Tag:String = "MainActivity"

    lateinit var mainAdapter: MainAdapter
    private var movieKategori = 0
    private val api = ApiService().endpoint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setUpRecyleView()
    }

    override fun onStart() {
        super.onStart()
        getMovie()
    }

    private fun setUpRecyleView() {
       mainAdapter = MainAdapter(arrayListOf())
        list_movie.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = mainAdapter
        }
    }

    fun getMovie(){
        showLoading(true)

        var apiCall: Call<MovieResponse>? = null
        when(movieKategori){
            moviePopular ->{
                apiCall = api.getMoviePopular(Constant.API_KEY,1)
            }
            moveNowPlaying->{
                apiCall= api.getMoviePopular(Constant.API_KEY,1)
            }
        }

        apiCall!!
                .enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(call: Call<MovieResponse>,
                                            response: Response<MovieResponse>) {
                        showLoading(false)
                        if(response.isSuccessful){
                            showMovie(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d(Tag,"errorResponse: $t")
                        showLoading(false)
                    }

                })
    }

    fun showLoading(loading:Boolean){
        when(loading){
            true -> progress_movie.visibility = View.VISIBLE
            false -> progress_movie.visibility = View.GONE
        }
    }

    fun showMovie(response:MovieResponse){
//        Log.d(Tag,"responseMovie: $response")
//        Log.d(Tag,"total_pages: ${response.total_pages}")
//
//        for (movie in response.results){
//            Log.d(Tag,"movie_title: ${movie.title}" )
//        }

        mainAdapter.setData(response.results)
    }

    fun showMessage(msg:String){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_popular -> {
            showMessage("popuar selected")
                movieKategori = moviePopular
                getMovie()
                true
            }
            R.id.action_now_playing -> {
                showMessage("now playing selected")
                movieKategori = moveNowPlaying
                getMovie()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}