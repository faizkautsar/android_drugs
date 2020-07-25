package com.example.drugs.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lapor(
    @SerializedName("id")var id : Int? = null,
    @SerializedName("foto")var foto : String? = null,
    @SerializedName("peran")var peran : String? = null,
    @SerializedName("nama")var nama : String? = null,
    @SerializedName("no_telp")var no_telp : String? = null,
    @SerializedName("jalan")var jalan : String? = null,
    @SerializedName("desa")var desa : String? = null,
    @SerializedName("kecamatan") var kecamatan : String? = null,
    @SerializedName("kota") var kota : String? = null,
    @SerializedName("jenis_narkoba") var jenis_narkoba : String? = null,
    @SerializedName("pekerjaan") var pekerjaan: String? = null,
    @SerializedName("kendaraan") var kendaraan: String? = null,
    @SerializedName("kegiatan") var kegiatan: String? = null,
    @SerializedName("tmpt_transaksi") var tmpt_transaksi: String? = null

): Parcelable