package com.vectormobile.fundacionavila.listener


import com.vectormobile.fundacionavila.models.MonumentVideos


interface RecyclerMonumentVideoListener {
    fun onClick(monumentVideo: MonumentVideos, position: Int)

}