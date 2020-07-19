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
    @SerializedName("jalan")var jalan : String? = null,
    @SerializedName("desa")var desa : String? = null,
    @SerializedName("kecamatan") var kecamatan : String? = null,
    @SerializedName("kota") var kota : String? = null,
    @SerializedName("api_token") var token : String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("alamat") var alamat: String? = null,
    @SerializedName("foto") var foto: String? = null

) : Parcelable