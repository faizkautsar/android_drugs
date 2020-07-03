package com.example.drugs.models

import com.google.gson.annotations.SerializedName

data class Rehabilitation(
    @SerializedName("id") var id : Int? = null,
    @SerializedName("inisial") var initial : String? = null,
    @SerializedName("nama_lengkap") var fullName : String? = null,
    @SerializedName("tanggal_lahir") var tanggalLahir : String? = null,
    @SerializedName("umur") var age : Int? = null,
    @SerializedName("alamat") var address : String? = null,
    @SerializedName("keterangan") var description : String? = null,
    @SerializedName("pekerjaan") var job : String? = null,
    @SerializedName("rujukan") var hospitalRefer : String? = null
)