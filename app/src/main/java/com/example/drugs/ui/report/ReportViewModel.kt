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
    private fun reset() { state.value = ReportState.Reset }

    fun Validate(nama : String,no_telp: String, jalan : String, desa: String,
                 kecamatan: String, kota: String, jenis_narkoba : String, pekerjaan : String,
                 kegiatan: String, tmpt_transaksi: String ) : Boolean {
        reset()
        if (nama.isEmpty()) {
            state.value = ReportState.Validate(nama = "Nama tidak boleh kosong!")
            return false
        }
        if(nama.length < 3){
            state.value = ReportState.Validate(nama = "Nama minimal 3 karakter")
            return false
        }
        if (no_telp.isEmpty()) {
            state.value = ReportState.Validate(no_telp = "Nomer telepon tidak boleh kosong!")
            return false
        }
        if (!(no_telp.length >= 10 && no_telp.length <= 13)) {
            state.value = ReportState.Validate(no_telp = "Nomer telepon tidak valid")
            return false
        }
        if (jalan.isEmpty()) {
            state.value = ReportState.Validate(jalan = "Nama jalan tidak boleh kosong!")
            return false
        }
        if(jalan.length < 8){
            state.value = ReportState.Validate(jalan = "Nama jalan minimal 10 karakter")
            return false
        }
        if (desa.isEmpty()) {
            state.value = ReportState.Validate(desa = "Desa/Dusun tidak boleh kosong!")
            return false
        }
        if(desa.length < 4){
            state.value = ReportState.Validate(desa = "Nama desa minimal 4 karakter")
            return false
        }
        if (kecamatan.isEmpty()) {
            state.value = ReportState.Validate(kecamatan = "Nama kecamatan tidak boleh kosong!")
            return false
        }
        if(kecamatan.length < 5){
            state.value = ReportState.Validate(kecamatan = "Nama kecamatan minimal 5 karakter")
            return false
        }
        if (kota.isEmpty()) {
            state.value = ReportState.Validate(kota = "Kota/Kabupaten tidak boleh kosong!")
            return false
        }
        if(kota.length < 4){
            state.value = ReportState.Validate(kota = "Nama kota minimal 4 karakter")
            return false
        }

        if (jenis_narkoba.isEmpty()) {
            state.value = ReportState.Validate(jenis_narkoba = "Jenis narkoba tidak boleh kosong!")
            return false
        }
        if(jenis_narkoba.length < 4){
            state.value = ReportState.Validate(jenis_narkoba = "Nama narkoba minimal 4 karakter")
            return false
        }

        if (pekerjaan.isEmpty()) {
            state.value = ReportState.Validate(pekerjaan = "Pekerjaan tidak boleh kosong!")
            return false
        }
        if(pekerjaan.length <6){
            state.value = ReportState.Validate(pekerjaan = "Nama jalan minimal 6 karakter")
            return false
        }
        if (kegiatan.isEmpty()) {
            state.value = ReportState.Validate(kegiatan = "Kegiatan tidak boleh kosong!")
            return false
        }
        if(kegiatan.length < 6){
            state.value = ReportState.Validate(kegiatan = "Kegiatan jalan minimal 6 karakter")
            return false
        }
        if (tmpt_transaksi.isEmpty()) {
            state.value =
                ReportState.Validate(tmpt_transaksi = "Tempat transaksi/menggunakan narkoba tidak boleh kosong!")
            return false
        }
        if(tmpt_transaksi.length < 6){
            state.value = ReportState.Validate(tmpt_transaksi = "Transaksi/ Penggunaan minimal 6 karakter")
            return false
        }
        return true
    }
        fun report(token: String, lapor: Lapor, urlFoto:String){
        setLoading()
        reportRepository.report(token, lapor, urlFoto, object : SingleResponse<Lapor>{
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
    data class Validate(
        var nama : String? = null,
        var no_telp : String? = null,
        var jalan : String? = null,
        var desa : String? = null,
        var kecamatan : String? = null,
        var kota : String? = null,
        var jenis_narkoba : String? = null,
        var pekerjaan : String? = null,
        var kegiatan : String? = null,
        var tmpt_transaksi : String? = null
         ) : ReportState()
    object Reset : ReportState()
}
