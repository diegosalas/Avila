package com.vectormobile.fundacionavila.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.inflate
import com.vectormobile.fundacionavila.listener.RecyclerMonumentPhotoListener
import com.vectormobile.fundacionavila.loadByResource
import com.vectormobile.fundacionavila.models.MonumentPhotos
import kotlinx.android.synthetic.main.recycler_monument_photos.view.*



class MonumentPhotoAdapter(private val monumentPhoto: List<MonumentPhotos>, private val listener: RecyclerMonumentPhotoListener)
    : RecyclerView.Adapter<MonumentPhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.recycler_monument_photos))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(monumentPhoto[position], listener)

    override fun getItemCount() = monumentPhoto.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(monumentPhoto: MonumentPhotos, listener: RecyclerMonumentPhotoListener) = with(itemView) {


            if(monumentPhoto.image == 0){
                imageViewPhoto.loadByResource(R.drawable.img_inicio_avila)

            }else {
                imageViewPhoto.loadByResource(monumentPhoto.image)
                setOnClickListener { listener.onClick(monumentPhoto, adapterPosition) }
            }


        }
    }
}