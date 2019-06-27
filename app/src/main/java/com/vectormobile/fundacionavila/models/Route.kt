package com.vectormobile.fundacionavila.models

import androidx.annotation.Nullable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Route(

    val id: Int,
    val name: String,
    val imgResource: Int,
    val duration: String,
    val video: String = "",
    @SerializedName("image")
    @Expose
    val image: String = "",
    @SerializedName("description")
    @Expose
    val description: String = "",
    @Expose
    val monuments: List<Monument> = emptyList()
)