package com.d3if0028.katalogmov.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.activity.DetailActivity
import com.d3if0028.katalogmov.activity.movieNowPlaying
import com.d3if0028.katalogmov.activity.moviePopular
import com.d3if0028.katalogmov.adapter.MainAdapter
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.MovieModel
import com.d3if0028.katalogmov.model.MovieResponse
import com.d3if0028.katalogmov.retrofit.ApiService
import kotlinx.android.synthetic.main.fragment_popular.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularFragment : Fragment() {

    lateinit var  v:View
    lateinit var mainAdapter : MainAdapter

    private var isScrolling = false
    private var currentPage = 1
    private var totalPage = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_popular, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        getMoviePopular()
        showLoadingNextPage(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyleView()
        setUpListener()
    }

    private fun setUpListener() {
        v.scroolView.setOnScrollChangeListener(object: NestedScrollView.OnScrollChangeListener{
            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                if(scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight){
                    if(!isScrolling){
                        if(currentPage <= totalPage ){
                            getMoviePopularNextPage()
                        }
                    }
                }
            }
        })
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

    fun getMoviePopular(){
        showLoading(true)
        ApiService().endpoint.getMoviePopular(Constant.API_KEY,1)
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

    fun getMoviePopularNextPage(){
        currentPage += 1
        showLoadingNextPage(true)
        ApiService().endpoint.getMoviePopular(Constant.API_KEY,currentPage)
                .enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>,
                                            response: Response<MovieResponse>) {
                        showLoadingNextPage(false)
                        if(response.isSuccessful){
                            showMovieNextPage(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        showLoadingNextPage(false)
                    }

                })
    }

    fun showLoading(loading:Boolean){
        when(loading){
            true -> v.progress_movie.visibility = View.VISIBLE
            false -> v.progress_movie.visibility = View.GONE
        }
    }

    fun showLoadingNextPage(loading:Boolean){
        when(loading){
            true -> {
                isScrolling= true
                v.progress_movie_next_page.visibility = View.VISIBLE}
            false -> {
                isScrolling = false
                v.progress_movie_next_page.visibility = View.GONE
            }
        }
    }

    fun showMovie(response: MovieResponse){
//        Log.d(Tag,"responseMovie: $response")
//        Log.d(Tag,"total_pages: ${response.total_pages}")
//
//        for (movie in response.results){
//            Log.d(Tag,"movie_title: ${movie.title}" )
//        }
    totalPage = response.total_pages!!
        mainAdapter.setData(response.results)
    }

    fun showMovieNextPage(response: MovieResponse){
//        Log.d(Tag,"responseMovie: $response")
//        Log.d(Tag,"total_pages: ${response.total_pages}")
//
//        for (movie in response.results){
//            Log.d(Tag,"movie_title: ${movie.title}" )
//        }
        totalPage = response.total_pages!!
        mainAdapter.setDataNextPage(response.results)
        Toast.makeText(requireContext(),"Page $currentPage",Toast.LENGTH_SHORT).show()
    }
}