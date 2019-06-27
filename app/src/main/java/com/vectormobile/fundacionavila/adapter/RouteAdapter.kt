package com.vectormobile.fundacionavila.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.inflate
import com.vectormobile.fundacionavila.listener.RecyclerMonumentListener
import com.vectormobile.fundacionavila.listener.RecyclerRouteListener
import com.vectormobile.fundacionavila.loadByResource
import kotlinx.android.synthetic.main.recycler_monument.view.*
import com.vectormobile.fundacionavila.models.Route
import kotlinx.android.synthetic.main.recycler_route.view.*


class RouteAdapter(private val routes: List<Route>, private val listener: RecyclerRouteListener)
    : RecyclerView.Adapter<RouteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(parent.inflate(R.layout.recycler_route))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(routes[position], listener)

    override fun getItemCount() = routes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(route: Route, listener: RecyclerRouteListener) = with(itemView) {


            tvTitleRoute.text = route.name

            imgRoute.loadByResource(route.imgResource)
            textDuration.text = route.duration

            setOnClickListener { listener.onClick(route, adapterPosition) }

           // buttonDelete.setOnClickListener { listener.onDelete(route, adapterPosition) }
        }
    }
}