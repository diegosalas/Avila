package com.vectormobile.fundacionavila.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Glossary(
    @SerializedName("name")
    @Expose
    private val name: String,
    @SerializedName("definition")
    @Expose
    private val definition: String
)