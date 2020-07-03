package com.example.drugs.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Rehabilitation
import com.example.drugs.repositories.RehabilitationRepository
import com.example.drugs.utils.ArrayResponse
import com.example.drugs.webservices.SingleLiveEvent

class HomeViewModel(private val rehabilitationRepo: RehabilitationRepository) : ViewModel(){
    private val state: SingleLiveEvent<HomeState> = SingleLiveEvent()
    private val rehabilitations = MutableLiveData<List<Rehabilitation>>()
    private val rehabStatistic = MutableLiveData<HashMap<String, List<Rehabilitation>>>()


    private fun setLoading(){ state.value = HomeState.Loading(true) }
    private fun hideLoading(){ state.value = HomeState.Loading(false) }
    private fun toast(message: String){ state.value = HomeState.ShowToast(message) }

    //statistic
    fun fetchRehabilitations(){
        setLoading()
        rehabilitationRepo.getRehabilitations(object: ArrayResponse<Rehabilitation>{
            override fun onSuccess(datas: List<Rehabilitation>?) {
                hideLoading()
                datas?.let {
                    rehabilitations.postValue(it)
                    transformRehabStatistic(it)
                }
            }
            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { toast(it) }
            }
        })
    }

    private fun transformRehabStatistic(rehabs: List<Rehabilitation>){
        rehabStatistic.postValue(rehabs.groupBy { it.hospitalRefer.toString() } as HashMap<String, List<Rehabilitation>>?)
    }

    fun getRehabs() = rehabilitations
    fun getState() = state
    fun getRehabStats() = rehabStatistic
}

sealed class HomeState {
    data class Loading(val state : Boolean) : HomeState()
    data class ShowToast(val message: String) : HomeState()
}