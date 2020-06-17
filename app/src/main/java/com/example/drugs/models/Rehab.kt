package com.example.drugs.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rehab(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("inisial") var name : String? = null,
    @SerializedName("tanggal_lahir") var tgl_lahir : String? = null,
    @SerializedName("umur") var umur : Int? = null,
    @SerializedName("keterangan") val keterangan : String? =null,
    @SerializedName("rujukan") var rujukan : String? = null
):Parcelable