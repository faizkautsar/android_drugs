package com.example.drugs.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Psicotropica (
    @SerializedName ("id") var id :Int? = null,
    @SerializedName ("nama") var nama : String? = null,
    @SerializedName ("golongan") var golongan : String? = null,
    @SerializedName ("keterangan") var keterangan :String? = null,
    @SerializedName ("dampak") var dampak :String? = null,
    @SerializedName ("gambar") var gambar :String? = null
) : Parcelable