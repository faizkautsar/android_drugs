package com.example.drugs.repositories

import com.example.drugs.models.Rehabilitation
import com.example.drugs.utils.ArrayResponse
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface RehabilitationContract {
    fun getRehabilitations(listener: ArrayResponse<Rehabilitation>)
}


class RehabilitationRepository(private val api: ApiService) : RehabilitationContract{
    override fun getRehabilitations(listener: ArrayResponse<Rehabilitation>) {
        api.getRehab().enqueue(object: Callback<WrappedListResponse<Rehabilitation>>{
            override fun onFailure(call: Call<WrappedListResponse<Rehabilitation>>, t: Throwable) = listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedListResponse<Rehabilitation>>, response: Response<WrappedListResponse<Rehabilitation>>) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

}