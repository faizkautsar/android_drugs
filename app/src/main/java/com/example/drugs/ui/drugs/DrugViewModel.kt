package com.example.drugs.ui.drugs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drugs.models.Adiktif
import com.example.drugs.models.Narcotic
import com.example.drugs.models.Psicotropica
import com.example.drugs.repositories.AddictiveSubstanceRepository
import com.example.drugs.repositories.NarcoticRepository
import com.example.drugs.repositories.PsychotropicRepository
import com.example.drugs.utils.ArrayResponse
import com.example.drugs.webservices.SingleLiveEvent

class DrugViewModel (private val addictiveSubstanceRepository: AddictiveSubstanceRepository,
                     private val narcoticRepository: NarcoticRepository,
                     private val psychotropicRepository: PsychotropicRepository) : ViewModel(){

    private val psychotropics = MutableLiveData<List<Psicotropica>>()
    private val narcotics = MutableLiveData<List<Narcotic>>()
    private val additictives = MutableLiveData<List<Adiktif>>()

    private val state : SingleLiveEvent<DrugState> = SingleLiveEvent()
    private fun setLoading(){ state.value = DrugState.Loading(true) }
    private fun hideLoading(){ state.value = DrugState.Loading(false) }
    private fun toast(message: String){ state.value = DrugState.ShowToast(message) }

    fun fetchPsychotropic(){
        setLoading()
        psychotropicRepository.getPsychotropics(object: ArrayResponse<Psicotropica>{
            override fun onSuccess(datas: List<Psicotropica>?) {
                hideLoading()
                datas?.let { psychotropics.postValue(it) }
            }

            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { toast(it) }
            }
        })
    }

    fun fetchNarcotic(){
        setLoading()
        narcoticRepository.getNarcotics(object: ArrayResponse<Narcotic>{
            override fun onSuccess(datas: List<Narcotic>?) {
                hideLoading()
                datas?.let { narcotics.postValue(it) }
            }
            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { toast(it) }
            }
        })
    }

    fun fetchAddictives(){
        setLoading()
        addictiveSubstanceRepository.getAddictiveSubstances(object: ArrayResponse<Adiktif>{
            override fun onSuccess(datas: List<Adiktif>?) {
                hideLoading()
                datas?.let { additictives.postValue(it) }
            }
            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { toast(it) }
            }
        })
    }

    fun getState() = state
    fun getAddictives() = additictives
    fun getPsychotropics() = psychotropics
    fun getNarcotics() = narcotics

}

sealed class DrugState {
    data class Loading(val state: Boolean) : DrugState()
    data class ShowToast(val message: String): DrugState()
}