package com.example.drugs.repositories

import com.example.drugs.models.Psicotropica
import com.example.drugs.utils.ArrayResponse
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface PsychotropicContract {
    fun getPsychotropics(listener: ArrayResponse<Psicotropica>)
}

class PsychotropicRepository (private val api: ApiService) : PsychotropicContract {
    override fun getPsychotropics(listener: ArrayResponse<Psicotropica>) {
        api.getPsicotropica().enqueue(object: Callback<WrappedListResponse<Psicotropica>>{
            override fun onFailure(call: Call<WrappedListResponse<Psicotropica>>, t: Throwable) = listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedListResponse<Psicotropica>>, response: Response<WrappedListResponse<Psicotropica>>) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

}