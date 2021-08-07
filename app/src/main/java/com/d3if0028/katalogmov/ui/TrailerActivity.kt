package com.d3if0028.katalogmov.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.TrainlerResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrailerActivity : AppCompatActivity() {
    private val Tag:String = "TrailerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)
        setUpView()
        setUpListener()
    }

    override fun onStart() {
        super.onStart()
        getMovieTrailer()
    }

    private fun setUpView() {

    }

    private fun setUpListener() {

    }

    private  fun getMovieTrailer(){
        showLoading(true)
        ApiService().endpoint.getMovieTrailer(Constant.MOVIE_ID,Constant.API_KEY)
                .enqueue(object : Callback<TrainlerResponse>{
                    override fun onResponse(call: Call<TrainlerResponse>, response: Response<TrainlerResponse>) {
                        showLoading(false)
                        if (response.isSuccessful){
                            showTrailer(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<TrainlerResponse>, t: Throwable) {

                    }

                })

    }

    private fun showLoading(loading:Boolean){
        when(loading){
            true -> {

            }

            false -> {

            }
        }
    }

    private fun showTrailer(trailer : TrainlerResponse) {
        for (res in trailer.results) {
            Log.d(Tag, "name_vidio : ${res.name}")
        }
    }
}