package com.example.drugs.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Hukum (
    @SerializedName("id") var id :Int? = null,
    @SerializedName("keterangan") var keterangan : String? = null,
    @SerializedName("isi") var isi :String? = null
): Parcelable