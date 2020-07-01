package com.example.drugs.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

class Rehab (
    @SerializedName("id") var id :Int? = null,
    @SerializedName("umur") var umur : String? = null,
    @SerializedName("rujukan") var rujukan : String? = null
): Parcelable
