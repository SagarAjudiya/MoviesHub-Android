package com.example.movieshub.data.model.response

import com.google.gson.annotations.SerializedName

data class CastDetail(

    @SerializedName("cast")
    val cast: List<Cast>?,

    @SerializedName("id")
    val id: Int?

)