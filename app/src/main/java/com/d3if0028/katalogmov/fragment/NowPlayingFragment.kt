package com.d3if0028.katalogmov.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.activity.DetailActivity
import com.d3if0028.katalogmov.adapter.MainAdapter
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.MovieModel
import com.d3if0028.katalogmov.model.MovieResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import kotlinx.android.synthetic.main.fragment_now_playing.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NowPlayingFragment : Fragment() {
    lateinit var  v:View
    lateinit var mainAdapter : MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_now_playing, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        getMovieNowPlaying()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyleView()
    }

    private fun setUpRecyleView() {
        mainAdapter = MainAdapter(arrayListOf(),object:MainAdapter.onAdapterListener{
            override fun onClick(movie: MovieModel) {
                startActivity(Intent(requireContext(), DetailActivity::class.java))
            }

        })
        v.list_movie.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = mainAdapter
        }
    }

    fun getMovieNowPlaying(){
        showLoading(true)
        ApiService().endpoint.getMovieNowPlaying(Constant.API_KEY,1)
                .enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>,
                                            response: Response<MovieResponse>) {
                        showLoading(false)
                        if(response.isSuccessful){
                            showMovie(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        showLoading(false)
                    }

                })
    }

    fun showLoading(loading:Boolean){
        when(loading){
            true -> v.progress_movie.visibility = View.VISIBLE
            false -> v.progress_movie.visibility = View.GONE
        }
    }

    fun showMovie(response: MovieResponse){
//        Log.d(Tag,"responseMovie: $response")
//        Log.d(Tag,"total_pages: ${response.total_pages}")
//
//        for (movie in response.results){
//            Log.d(Tag,"movie_title: ${movie.title}" )
//        }

        mainAdapter.setData(response.results)
    }

}