package com.example.drugs.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id")var id : Int? = null,
    @SerializedName("nama")var nama : String? = null,
    @SerializedName("email")var email : String? = null,
    @SerializedName("no_telp")var no_telp : String? = null,
    @SerializedName("alamat")var alamat : String? = null,
    @SerializedName("desa")var desa : String? = null,
    @SerializedName("kecamatan") var kecamatan : String? = null,
    @SerializedName("kode_pos") var kode_pos : String? = null,
    @SerializedName("api_token") var token : String? = null
) : Parcelable