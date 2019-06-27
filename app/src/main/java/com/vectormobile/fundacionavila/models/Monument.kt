package com.vectormobile.fundacionavila.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.Duration


data class Monument(
    val id: Int,
    val name: String,
    val imgResource: Int,
    val video: String,
    @SerializedName("localization")
    @Expose
    private val localization: String = "",


    @SerializedName("date")
    @Expose
    private val date: String ="",
    @SerializedName("build")
    @Expose
    private val build: String ="",
    @SerializedName("type")
    @Expose
    private val type: String ="",
    @SerializedName("history")
    @Expose
    private val history: String ="",
    @SerializedName("curiosities")
    @Expose
    private val curiosities: String ="",
    @SerializedName("uses")
    @Expose
    private val uses: String ="",
    @SerializedName("images")
    @Expose
    private val images: List<String> = emptyList(),
    @SerializedName("videos")
    @Expose
    private val videos: List<String> = emptyList(),
    @SerializedName("glossary")
    @Expose
    private val glossary: List<Glossary> = emptyList()
)