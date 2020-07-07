package com.example.drugs.repositories

import com.example.drugs.models.Lapor
import com.example.drugs.models.User
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedResponse
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ContactReport {
    fun report(token: String, lapor: Lapor, listener: SingleResponse<Lapor>)
}

class ReportRepository (private val api: ApiService) : ContactReport {
    override fun report(token: String, lapor: Lapor, listener: SingleResponse<Lapor>) {
        val requestBody = Gson().toJson(lapor)
        println(requestBody)
        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestBody)
        api.lapor(token, body).enqueue(object : Callback<WrappedResponse<Lapor>> {
            override fun onFailure(call: Call<WrappedResponse<Lapor>>, t: Throwable) =
                listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedResponse<Lapor>>, response: Response<WrappedResponse<Lapor>>) {
                when {
                    response.isSuccessful -> {
                        val b = response.body()
                        if (b!!.status) listener.onSuccess(b.data) else listener.onFailure(Error(b.message))
                    }
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }
}