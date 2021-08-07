package com.d3if0028.katalogmov.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.d3if0028.katalogmov.R
import com.d3if0028.katalogmov.model.TrailerModel
import kotlinx.android.synthetic.main.adapter_trailer.view.*

class TrailerAdapter(var vidios : ArrayList<TrailerModel>, var listener:onAdapterListener):
    RecyclerView.Adapter<TrailerAdapter.ViewHoder>() {

    private val Tag:String = "TrailerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHoder (
       LayoutInflater.from(parent.context).inflate(R.layout.adapter_trailer,parent,false)
    )

    override fun onBindViewHolder(holder: ViewHoder, position: Int) {
        val video = vidios[position]
        holder.bind(video)
//        Log.d(Tag," backdrop_path : ${backdropPath}")

        holder.view.text_video.setOnClickListener {
            listener.onPlay(video.key!!)
        }
    }

    override fun getItemCount(): Int = vidios.size

    class ViewHoder(view : View): RecyclerView.ViewHolder(view) {
        val view = view
        fun bind(videos:TrailerModel){
            view.text_video.text = videos.name
        }
    }

    public fun setData(newVidios:List<TrailerModel>){
        vidios.clear()
        vidios.addAll(newVidios)
        notifyDataSetChanged()
        listener.onLoad(newVidios[0].key!!)
    }

    interface onAdapterListener{
        fun onLoad(key:String)
        fun onPlay(key:String)
    }

}