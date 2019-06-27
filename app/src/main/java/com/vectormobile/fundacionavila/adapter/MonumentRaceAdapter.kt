package com.vectormobile.fundacionavila.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.inflate
import com.vectormobile.fundacionavila.listener.RecyclerMonumentListener
import com.vectormobile.fundacionavila.loadByResource
import com.vectormobile.fundacionavila.models.Monument
import kotlinx.android.synthetic.main.recycler_monument.view.*
import com.vectormobile.fundacionavila.listener.RecyclerMonumentRaceListener
import kotlinx.android.synthetic.main.recycler_monument.view.buttonDelete





class MonumentRaceAdapter(private val monuments: List<Monument>, private val listener: RecyclerMonumentRaceListener)
    : RecyclerView.Adapter<MonumentRaceAdapter.ViewHolder>() {

   // override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = parent.inflate(R.layout.recycler_monument)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        MonumentRaceAdapter.ViewHolder(parent.inflate(R.layout.recycler_monument_race))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(monuments[position], listener)

    override fun getItemCount() = monuments.size

    override fun getItemViewType(position: Int): Int {

        return position % 2 * 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(monument: Monument, listener: RecyclerMonumentRaceListener) = with(itemView) {




            textViewCityName.text = monument.name




            imageViewVideo.loadByResource(monument.imgResource)

            // Clicks Events
            setOnClickListener { listener.onClick(monument, adapterPosition) }
            buttonDelete.setOnClickListener { listener.onDelete(monument, adapterPosition) }
        }
    }
    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(monument: Monument, listener: RecyclerMonumentListener) = with(itemView) {


            textViewCityName.text = "$monument.id)   $monument.name"


            imageViewVideo.loadByResource(monument.imgResource)

            // Clicks Events
            setOnClickListener { listener.onClick(monument, adapterPosition) }
            buttonDelete.setOnClickListener { listener.onDelete(monument, adapterPosition) }
        }
    }


}