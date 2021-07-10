package com.d3if0028.katalogmov.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.model.MovieModel
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(var movies : ArrayList<MovieModel>): RecyclerView.Adapter<MainAdapter.ViewHoder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHoder (
       LayoutInflater.from(parent.context).inflate(R.layout.adapter_main,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
      holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    class ViewHoder(view : View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(movies:MovieModel){
            view.text_title.text = movies.title
        }
    }

}