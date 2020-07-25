package com.example.drugs.repositories

import com.example.drugs.models.Lapor
import com.example.drugs.models.User
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedResponse
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

interface ContactReport {
    fun report(token: String, lapor: Lapor, urlFoto: String, listener: SingleResponse<Lapor>)
}

class ReportRepository (private val api: ApiService) : ContactReport {
    private fun createPartFromString(string: String): RequestBody = RequestBody.create(
        MultipartBody.FORM, string
    )


    override fun report(
        token: String,
        lapor: Lapor,
        urlFoto: String,
        listener: SingleResponse<Lapor>
    ) {
        val map = HashMap<String, RequestBody>()
        map["peran"] = createPartFromString(lapor.peran!!)
        map["nama"] = createPartFromString(lapor.nama!!)
        map["no_telp"] = createPartFromString(lapor.no_telp!!)
        map["jalan"] = createPartFromString(lapor.jalan!!)
        map["desa"] = createPartFromString(lapor.desa!!)
        map["kecamatan"] = createPartFromString(lapor.kecamatan!!)
        map["kota"] = createPartFromString(lapor.kota!!)
        map["jenis_narkoba"] = createPartFromString(lapor.jenis_narkoba!!)
        map["pekerjaan"] = createPartFromString(lapor.pekerjaan!!)
        map["kendaraan"] = createPartFromString(lapor.kendaraan!!)
        map["kegiatan"] = createPartFromString(lapor.kegiatan!!)
        map["tmpt_transaksi"] = createPartFromString(lapor.tmpt_transaksi!!)

        val file = File(urlFoto)
        val requestBodyForFile = RequestBody.create(MediaType.parse("image/*"), file)
        val image = MultipartBody.Part.createFormData("foto", file.name, requestBodyForFile)
        api.lapor(token, map, image).enqueue(object : Callback<WrappedResponse<Lapor>> {
            override fun onFailure(call: Call<WrappedResponse<Lapor>>, t: Throwable) {
                listener.onFailure(Error(t.message))
            }

            override fun onResponse(
                call: Call<WrappedResponse<Lapor>>,
                response: Response<WrappedResponse<Lapor>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status!!) {
                        listener.onSuccess(body.data)
                    } else {
                        listener.onFailure(Error("Gagal saat report. Mungkin nomor telepon sudah pernah didaftarkan"))
                    }
                } else {
                    listener.onFailure(Error(response.message()))
                }
            }

        })
    }
}
//    override fun report(token: String, lapor: Lapor, listener: SingleResponse<Lapor>) {
//        val requestBody = Gson().toJson(lapor)
//        println(requestBody)
//        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody)
//        api.lapor(token, body).enqueue(object : Callback<WrappedResponse<Lapor>> {
//            override fun onFailure(call: Call<WrappedResponse<Lapor>>, t: Throwable) =
//                listener.onFailure(Error(t.message))
//
//            override fun onResponse(call: Call<WrappedResponse<Lapor>>, response: Response<WrappedResponse<Lapor>>) {
//                when {
//                    response.isSuccessful -> {
//                        val b = response.body()
//                        if (b!!.status) listener.onSuccess(b.data) else listener.onFailure(Error(b.message))
//                    }
//                    else -> listener.onFailure(Error(response.message()))
//                }
//            }
//        })
//    }
