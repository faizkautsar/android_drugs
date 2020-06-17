package com.example.drugs.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.w3c.dom.Text
import java.io.File
@Parcelize
data class Narcotic (
    @SerializedName("id") var id : Int? = null,
    @SerializedName("nama") var nama : String? = null,
    @SerializedName("jenis") var jenis : String? = null,
    @SerializedName("golongan") var golongan : String? = null,
    @SerializedName("dampak") var dampak : String? = null,
    @SerializedName("keterangan") var keterangan : String? = null,
    @SerializedName("gambar") var gambar : String? = null
): Parcelable