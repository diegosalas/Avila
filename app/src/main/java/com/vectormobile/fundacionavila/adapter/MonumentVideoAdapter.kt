package com.vectormobile.fundacionavila.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.inflate
import com.vectormobile.fundacionavila.listener.RecyclerMonumentVideoListener
import com.vectormobile.fundacionavila.loadByResource
import com.vectormobile.fundacionavila.models.MonumentVideos
import kotlinx.android.synthetic.main.recycler_monument_video.view.*

import android.net.Uri


class MonumentVideoAdapter(private val monumentVideos: List<MonumentVideos>, private val listener: RecyclerMonumentVideoListener)
    : RecyclerView.Adapter<MonumentVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.recycler_monument_video))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(monumentVideos[position], listener)

    override fun getItemCount() = monumentVideos.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(monumentVideos: MonumentVideos, listener: RecyclerMonumentVideoListener) = with(itemView) {


            if(monumentVideos.image == 0){
                imageViewVideo.loadByResource(R.drawable.img_inicio_avila)

            }else {
                imageViewVideo.loadByResource(monumentVideos.image)
                setOnClickListener { listener.onClick(monumentVideos, adapterPosition) }
            }


        }
    }
}