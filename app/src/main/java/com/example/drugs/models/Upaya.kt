package com.example.drugs.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize

class Upaya (
    @SerializedName("id") var id :Int? = null,
    @SerializedName("aspek") var aspek : String? = null,
    @SerializedName("keterangan") var keterangan : String? = null
): Parcelable