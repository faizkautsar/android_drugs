package com.example.drugs.repositories

import com.example.drugs.models.Narcotic
import com.example.drugs.utils.ArrayResponse
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface NarcoticContract {
    fun getNarcotics(listener: ArrayResponse<Narcotic>)
}

class NarcoticRepository(private val api: ApiService) : NarcoticContract {
    override fun getNarcotics(listener: ArrayResponse<Narcotic>) {
        api.getNarcotic().enqueue(object : Callback<WrappedListResponse<Narcotic>>{
            override fun onFailure(call: Call<WrappedListResponse<Narcotic>>, t: Throwable) = listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedListResponse<Narcotic>>, response: Response<WrappedListResponse<Narcotic>>) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

}