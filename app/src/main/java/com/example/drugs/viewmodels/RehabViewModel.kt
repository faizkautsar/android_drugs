package com.example.drugs.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Rehab
import com.example.drugs.models.Rehabilitation
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.SingleLiveEvent
import com.example.drugs.webservices.WrappedListResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RehabViewModel : ViewModel(){
    private val state: SingleLiveEvent<RehabState> = SingleLiveEvent()
    private val api = ApiClient.instance()
    private val rehabs = MutableLiveData<List<Rehab>>()

    private fun setLoading() {
        state.value = RehabState.Loading(true)
    }

    private fun hideLoading() {
        state.value = RehabState.Loading(false)
    }

    private fun toast(m: String) {
        state.value = RehabState.ShowToast(m)
    }

    fun fetchRehab() {
        setLoading()
        api.getRehab().enqueue(object : Callback<WrappedListResponse<Rehabilitation>> {


            override fun onFailure(call: Call<WrappedListResponse<Rehabilitation>>, t: Throwable) {
            }

            override fun onResponse(call: Call<WrappedListResponse<Rehabilitation>>, response: Response<WrappedListResponse<Rehabilitation>>) {

            }

        })
    }
    fun listenToState() = state
    fun listenToRehab() = rehabs

}


sealed class RehabState {
    data class Loading(var state: Boolean) : RehabState()
    data class ShowToast(var message: String) : RehabState()
}
