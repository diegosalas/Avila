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
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.recycler_monument.view.buttonDelete





class MonumentAdapter(private val monuments: List<Monument>, private val listener: RecyclerMonumentListener)
    : RecyclerView.Adapter<MonumentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       // val view = parent.inflate(R.layout.recycler_monument2)
        when (viewType) {
            0-> {val view = parent.inflate(R.layout.recycler_monument)
                return ViewHolder(view)}
            2-> {val view = parent.inflate(R.layout.recycler_monument)
                return ViewHolder(view)}
            else->{val view = parent.inflate(R.layout.recycler_monument)
                return ViewHolder(view)}
        }


    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(monuments[position], listener)

    override fun getItemCount() = monuments.size

    override fun getItemViewType(position: Int): Int {

        return position % 2 * 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(monument: Monument, listener: RecyclerMonumentListener) = with(itemView) {




            textViewCityName.text = monument.name

            if(monument.id % 2 != 0){
                val layoutParamsRight = imageViewVideo.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParamsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
                imageViewVideo.setLayoutParams(layoutParamsRight)

                val layoutParamsLeft = textViewCityName.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParamsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
                textViewCityName.setLayoutParams(layoutParamsLeft)


            }else{
                val layoutParamsRight = imageViewVideo.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParamsRight.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
                imageViewVideo.setLayoutParams(layoutParamsRight)

                val layoutParamsLeft = textViewCityName.getLayoutParams() as RelativeLayout.LayoutParams
                layoutParamsLeft.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE)
                textViewCityName.setLayoutParams(layoutParamsLeft)
            }


            imageViewVideo.loadByResource(monument.imgResource)

            // Clicks Events
            setOnClickListener { listener.onClick(monument, adapterPosition) }
            buttonDelete.setOnClickListener { listener.onDelete(monument, adapterPosition) }
        }
    }
    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(monument: Monument, listener: RecyclerMonumentListener) = with(itemView) {


            textViewCityName.text = monument.name


            imageViewVideo.loadByResource(monument.imgResource)

            // Clicks Events
            setOnClickListener { listener.onClick(monument, adapterPosition) }
            buttonDelete.setOnClickListener { listener.onDelete(monument, adapterPosition) }
        }
    }


}