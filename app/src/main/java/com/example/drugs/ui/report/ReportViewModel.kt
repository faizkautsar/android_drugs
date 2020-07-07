package com.example.drugs.ui.report

import androidx.lifecycle.ViewModel
import com.example.drugs.models.Lapor
import com.example.drugs.repositories.ReportRepository
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.SingleLiveEvent

class ReportViewModel(private val reportRepository: ReportRepository) : ViewModel()
{
    private val state: SingleLiveEvent<ReportState> = SingleLiveEvent()

    private fun setLoading(){ state.value = ReportState.Loading(true) }
    private fun hideLoading(){ state.value = ReportState.Loading(false) }
    private fun alert(message: String){ state.value = ReportState.Alert(message) }
    private fun success(){ state.value = ReportState.Success }

    fun report(token: String, lapor: Lapor){
        setLoading()
        reportRepository.report(token, lapor, object : SingleResponse<Lapor>{
            override fun onSuccess(data: Lapor?) {
                hideLoading()
                success()
            }
            override fun onFailure(err: Error) {
                hideLoading()
                err.message?.let { alert(it) }
            }
        })
    }

    fun getState() = state
}

sealed class ReportState {
    object Success: ReportState()
    data class Loading(val state: Boolean) : ReportState()
    data class Alert(val message: String) : ReportState()
}
