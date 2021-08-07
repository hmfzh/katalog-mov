package com.d3if0028.katalogmov.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.adapter.MainAdapter
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.MovieModel
import com.d3if0028.katalogmov.model.MovieResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val moviePopular = 0
const val movieNowPlaying = 1

class MainActivity : AppCompatActivity() {

    private val Tag:String = "MainActivity"

    lateinit var mainAdapter: MainAdapter
    private var movieKategori = 0
    private val api = ApiService().endpoint
    private var isScrolling = false
    private var currentPage = 1
    private var totalPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        setUpRecyleView()
        setUpListener()
    }

    private fun setUpListener() {
        scroolView.setOnScrollChangeListener(object:NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if(scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight){
                    if(!isScrolling){
                        if(currentPage <= totalPage ){
                            getMovieNextPage()
                        }
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        getMovie()
        showLoadingNextPage(false)
    }

    private fun setUpRecyleView() {
       mainAdapter = MainAdapter(arrayListOf(),object:MainAdapter.onAdapterListener{
           override fun onClick(movie: MovieModel) {
               startActivity(Intent(applicationContext,DetailActivity::class.java))
           }

       })
        list_movie.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = mainAdapter
        }
    }

    private fun getMovie(){
        scroolView.scrollTo(0,0)
        currentPage = 1
        showLoading(true)

        var apiCall: Call<MovieResponse>? = null
        when(movieKategori){
            moviePopular ->{
                apiCall = api.getMoviePopular(Constant.API_KEY,1)
            }
            movieNowPlaying->{
                apiCall= api.getMovieNowPlaying(Constant.API_KEY,1)
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

    fun getMovieNextPage(){
        currentPage += 1
        showLoadingNextPage(true)

        var apiCall: Call<MovieResponse>? = null
        when(movieKategori){
            moviePopular ->{
                apiCall = api.getMoviePopular(Constant.API_KEY,currentPage)
            }
            movieNowPlaying->{
                apiCall= api.getMovieNowPlaying(Constant.API_KEY,currentPage)
            }
        }

        apiCall!!
                .enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(call: Call<MovieResponse>,
                                            response: Response<MovieResponse>) {
                        showLoadingNextPage(false)
                        if(response.isSuccessful){
                            showMovieNextPage(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d(Tag,"errorResponse: $t")
                        showLoadingNextPage(false)
                    }

                })
    }

    fun showLoading(loading:Boolean){
        when(loading){
            true -> progress_movie.visibility = View.VISIBLE
            false -> progress_movie.visibility = View.GONE
        }
    }

    fun showLoadingNextPage(loading:Boolean){
        when (loading) {
            true -> {
                isScrolling = true
                progress_movie_next_page.visibility = View.VISIBLE
            }
            false -> {
                isScrolling = false
                progress_movie_next_page . visibility = View.GONE
            }
        }
    }

    fun showMovie(response:MovieResponse){
//        Log.d(Tag,"responseMovie: $response")
//        Log.d(Tag,"total_pages: ${response.total_pages}")
//
//        for (movie in response.results){
//            Log.d(Tag,"movie_title: ${movie.title}" )
//        }
        totalPage = response.total_pages!!.toInt()
        mainAdapter.setData(response.results)
    }

    fun showMovieNextPage(response:MovieResponse){
//        Log.d(Tag,"responseMovie: $response")
//        Log.d(Tag,"total_pages: ${response.total_pages}")
//
//        for (movie in response.results){
//            Log.d(Tag,"movie_title: ${movie.title}" )
//        }
        totalPage = response.total_pages!!.toInt()
        mainAdapter.setDataNextPage(response.results)
        showMessage("Page $currentPage")
    }


    fun showMessage(msg:String){
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_popular -> {
            showMessage("popuar selected")
                movieKategori = moviePopular
                getMovie()
                true
            }
            R.id.action_now_playing -> {
                showMessage("now playing selected")
                movieKategori = movieNowPlaying
                getMovie()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}