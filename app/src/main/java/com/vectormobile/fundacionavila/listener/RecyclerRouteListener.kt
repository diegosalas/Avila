package com.vectormobile.fundacionavila.listener


import com.vectormobile.fundacionavila.models.Route



interface RecyclerRouteListener {

    fun onClick(route: Route, position: Int)
    fun onDelete(route: Route, position: Int)

}