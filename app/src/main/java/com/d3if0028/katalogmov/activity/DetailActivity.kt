package com.d3if0028.katalogmov.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.DetailResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private val Tag:String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setUpView()
        setUpListener()
    }

    override fun onStart() {
        super.onStart()
        getMovieDetail()
    }

    private fun setUpView(){
        setSupportActionBar(findViewById(R.id.toolbar))
//        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpListener(){
        fab.setOnClickListener{
            startActivity(Intent(applicationContext,TrailerActivity::class.java))
        }
    }

    private fun getMovieDetail(){
        ApiService().endpoint.getMovieDetail(Constant.MOVIE_ID,Constant.API_KEY)
            .enqueue(object: Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful){
                        showMovie(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Log.d(Tag,t.toString())
                }

            })
    }

    fun showMovie(detail:DetailResponse){
        val backdropPath = Constant.BACKDROP_PATH + detail.backdrop_path
        Picasso.get()
                .load(backdropPath)
                .placeholder(R.drawable.placeholder_portrait)
                .error(R.drawable.placeholder_portrait)
                .fit().centerCrop()
                .into(image_poster)

        text_title.text = detail.title
        text_vote.text = detail.vote_avarage.toString()
        text_overview.text = detail.overview

        for (genre in detail.genres!!){
            text_genre.text = "${genre.name}"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}