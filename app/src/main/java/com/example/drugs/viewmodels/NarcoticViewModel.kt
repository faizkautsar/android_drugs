package com.example.drugs.viewmodels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Narcotic
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.SingleLiveEvent
import com.example.drugs.webservices.WrappedListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NarcoticViewModel : ViewModel(){
    private val state : SingleLiveEvent<NarcoticState> = SingleLiveEvent()
    private val api = ApiClient.instance()
    private val narcotics = MutableLiveData<List<Narcotic>>()

    private fun setLoading() {  state.value = NarcoticState.Loading(true) }
    private fun hideLoading() {  state.value = NarcoticState.Loading(false) }
    private fun toast(m : String) { state.value = NarcoticState.ShowToast(m) }

    fun fetchNarcotic(){
        setLoading()
        api.getNarcotic().enqueue(object : Callback<WrappedListResponse<Narcotic>>{
            override fun onFailure(call: Call<WrappedListResponse<Narcotic>>, t: Throwable) {
                println(t.message)
                hideLoading()
                toast(t.message.toString())
            }

            override fun onResponse(call: Call<WrappedListResponse<Narcotic>>, response: Response<WrappedListResponse<Narcotic>>)
            {
                if(response.isSuccessful){
                    val body = response.body()
                    narcotics.postValue(body?.data)
                }
                hideLoading()
            }
        })
    }

    fun listenToState() = state
    fun listenToNarcotic() = narcotics
}


sealed class NarcoticState {
    data class Loading(var state : Boolean) : NarcoticState()
    data class ShowToast(var message : String) : NarcoticState()
}