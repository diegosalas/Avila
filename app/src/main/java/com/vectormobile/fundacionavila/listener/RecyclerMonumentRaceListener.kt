package com.vectormobile.fundacionavila.listener

import com.vectormobile.fundacionavila.models.Monument



interface RecyclerMonumentRaceListener {
    fun onClick(monument: Monument, position: Int)
    fun onDelete(monument: Monument, position: Int)
}