package com.example.drugs.repositories

import com.example.drugs.models.Adiktif
import com.example.drugs.utils.ArrayResponse
import com.example.drugs.webservices.ApiService
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


interface AddictiveSubstanceContract {
    fun getAddictiveSubstances(listener: ArrayResponse<Adiktif>)
}

class AddictiveSubstanceRepository(private val api: ApiService) : AddictiveSubstanceContract {
    override fun getAddictiveSubstances(listener: ArrayResponse<Adiktif>) {
        api.getAdiktif().enqueue(object: Callback<WrappedListResponse<Adiktif>>{
            override fun onFailure(call: Call<WrappedListResponse<Adiktif>>, t: Throwable) = listener.onFailure(Error(t.message))

            override fun onResponse(call: Call<WrappedListResponse<Adiktif>>, response: Response<WrappedListResponse<Adiktif>>) {
                when{
                    response.isSuccessful -> listener.onSuccess(response.body()!!.data)
                    else -> listener.onFailure(Error(response.message()))
                }
            }
        })
    }

}