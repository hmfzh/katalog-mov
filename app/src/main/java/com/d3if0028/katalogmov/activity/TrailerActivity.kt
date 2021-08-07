package com.d3if0028.katalogmov.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.adapter.TrailerAdapter
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.TrainlerResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_trailer.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TrailerActivity : AppCompatActivity() {
    private val Tag:String = "TrailerActivity"

    lateinit var trailerAdapter: TrailerAdapter
    lateinit var youTubePlayer: YouTubePlayer
    private var youTubeKey:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)
        setUpView()
        setUpRecyleView()
    }

    override fun onStart() {
        super.onStart()
        getMovieTrailer()
    }

    private fun setUpView() {
        supportActionBar!!.title = Constant.MOVIE_TITLE
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                youTubePlayer = player
                youTubeKey?.let{
                    youTubePlayer.cueVideo(it,0f)
                }
            }
        })
    }


    private fun setUpRecyleView() {
        trailerAdapter = TrailerAdapter(arrayListOf(), object : TrailerAdapter.onAdapterListener {
            override fun onLoad(key: String) {
                youTubeKey = key
            }

            override fun onPlay(key: String) {
                youTubePlayer.loadVideo(key,0f)
            }

        })
        list_video.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = trailerAdapter
        }
    }

    private  fun getMovieTrailer(){
        showLoading(true)
        ApiService().endpoint.getMovieTrailer(Constant.MOVIE_ID, Constant.API_KEY)
                .enqueue(object : Callback<TrainlerResponse> {
                    override fun onResponse(call: Call<TrainlerResponse>, response: Response<TrainlerResponse>) {
                        showLoading(false)
                        if (response.isSuccessful) {
                            showTrailer(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<TrainlerResponse>, t: Throwable) {

                    }

                })

    }

    private fun showLoading(loading: Boolean){
        when(loading){
            true -> {
                progress_video.visibility = View.VISIBLE
            }

            false -> {
                progress_video.visibility = View.GONE
            }
        }
    }

    private fun showTrailer(trailer: TrainlerResponse) {
        trailerAdapter.setData(trailer.results)
//        for (res in trailer.results) {
//            Log.d(Tag, "name_vidio : ${res.name}")
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}