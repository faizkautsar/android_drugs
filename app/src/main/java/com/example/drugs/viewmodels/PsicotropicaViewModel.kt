package com.example.drugs.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Psicotropica
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.SingleLiveEvent
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PsicotropicaViewModel : ViewModel() {
    private val state: SingleLiveEvent<PsicotropicState> = SingleLiveEvent()
    private val api = ApiClient.instance()
    private val psicotropicas = MutableLiveData<List<Psicotropica>>()

    private fun setLoading() {
        state.value = PsicotropicState.Loading(true)
    }

    private fun hideLoading() {
        state.value = PsicotropicState.Loading(false)
    }

    private fun toast(m: String) {
        state.value = PsicotropicState.ShowToast(m)
    }

    fun fetchPsicotropica() {
        setLoading()
        api.getPsicotropica().enqueue(object : Callback<WrappedListResponse<Psicotropica>> {
                override fun onFailure(
                    call: Call<WrappedListResponse<Psicotropica>>,
                    t: Throwable
                ) {
                    println(t.message)
                    hideLoading()
                    toast(t.message.toString())
                }

                override fun onResponse(
                    call: Call<WrappedListResponse<Psicotropica>>,
                    response: Response<WrappedListResponse<Psicotropica>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        psicotropicas.postValue(body?.data)
                        println(body!!.data)
                    }
                    hideLoading()
                }
            })
        }


    fun listenToState() = state
    fun listenToPsicotropica() = psicotropicas

}
sealed class PsicotropicState {
    data class Loading(var state: Boolean) : PsicotropicState()
    data class ShowToast(var message: String) : PsicotropicState()
}