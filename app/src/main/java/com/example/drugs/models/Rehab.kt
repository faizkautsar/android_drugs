package com.example.drugs.models

import com.google.gson.annotations.SerializedName

data class Rehab(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("nama") var name : String? = null,
    @SerializedName("umur") var umur : Int? = null,
    @SerializedName("rujukan") var rujukan : String? = null
)