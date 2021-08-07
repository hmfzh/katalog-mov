package com.d3if0028.katalogmov.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.model.Constant
import com.d3if0028.katalogmov.model.MovieModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(var movies : ArrayList<MovieModel>, var listener:onAdapterListener): RecyclerView.Adapter<MainAdapter.ViewHoder>() {

    private val Tag:String = "MainActivity"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHoder (
       LayoutInflater.from(parent.context).inflate(R.layout.adapter_main,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        val posterPath = Constant.POSTER_PATH + movie.backdrop_path
//        Log.d(Tag," backdrop_path : ${backdropPath}")
        Picasso.get()
                .load(posterPath)
                .placeholder(R.drawable.placeholder_portrait)
                .error(R.drawable.placeholder_portrait)
                .into(holder.view.image_poster);

        holder.view.image_poster.setOnClickListener {
            listener.onClick(movie)
        }
    }

    override fun getItemCount(): Int = movies.size

    class ViewHoder(view : View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(movies:MovieModel){
            view.text_title.text = movies.title
        }
    }

    public fun setData(newMovies:List<MovieModel>){
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    interface onAdapterListener{
        fun onClick(Movie:MovieModel)
    }

}