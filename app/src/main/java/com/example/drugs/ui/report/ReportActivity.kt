package com.example.drugs.ui.report

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.drugs.R
import com.example.drugs.extensions.disabled
import com.example.drugs.extensions.enabled
import com.example.drugs.extensions.showInfoAlert
import com.example.drugs.models.Lapor
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_report.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReportActivity : AppCompatActivity() {

    private val reportViewModel : ReportViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        observe()
        lapor()
    }

    private fun observe(){
        observeState()
    }
    private fun observeState() = reportViewModel.getState().observer(this, Observer { handleUI(it) })

    private fun handleUI(it : ReportState){
        when(it){
            is ReportState.Alert -> showInfoAlert(it.message)
            is ReportState.Loading -> {
                if (it.state){
                    btn_lapor.disabled()
                }else{
                    btn_lapor.enabled()
                }
            }
            is ReportState.Success-> {
                toast("berhasil")
                finish()
            }
            is ReportState.Reset -> {
                setErrorNama(null)
                setErrorNoTelp(null)
                setErrorJalan(null)
                setErrorDesa(null)
                setErrorKecamatan(null)
                setErrorKota(null)
                setErrorJenisNarkoba(null)
                setErrorPekerjaan(null)
                setErrorKegiatan(null)
                setErrorTransaksi(null)

            }
            is ReportState.Validate -> {
                setErrorNama(it.nama)
                setErrorNoTelp(it.no_telp)
                setErrorJalan(it.jalan)
                setErrorDesa(it.desa)
                setErrorKecamatan(it.kecamatan)
                setErrorKota(it.kota)
                setErrorJenisNarkoba(it.jenis_narkoba)
                setErrorPekerjaan(it.pekerjaan)
                setErrorKegiatan(it.kegiatan)
                setErrorTransaksi(it.tmpt_transaksi)

                 }
        }
    }

    private fun lapor() {
        btn_lapor.setOnClickListener {
            val peran = til_peran.getSelectedItem().toString().trim()
            val nama = ed_nama_terlapor.text.toString().trim()
            val no_telp = ed_no_telp.text.toString().trim()
            val jalan = ed_jalan.text.toString().trim()
            val desa = ed_desa.text.toString().trim()
            val kecamatan = ed_kecamatan.text.toString().trim()
            val kota = ed_kota.text.toString().trim()
            val jenis_narkoba = ed_jenis_narkoba.text.toString().trim()
            val pekerjaan = ed_pekerjaan.text.toString().trim()
            val kendaraan = ed_kendaraan.text.toString().trim()
            val kegiatan = ed_kegiatan.text.toString().trim()
            val tmpt_transaksi = ed_tmpt_transaksi.text.toString().trim()

            //validate here
            val token = Constants.getToken(this)
            val lapor = Lapor(peran = peran, nama = nama, no_telp = no_telp, jalan = jalan, desa = desa,
                kecamatan = kecamatan, kota = kota, jenis_narkoba = jenis_narkoba, pekerjaan = pekerjaan,
                kendaraan = kendaraan, kegiatan = kegiatan, tmpt_transaksi = tmpt_transaksi)
            if (reportViewModel.Validate(
                    nama, no_telp, jalan, desa, kecamatan, kota, jenis_narkoba, pekerjaan, kegiatan,
                    tmpt_transaksi)) {
                reportViewModel.report(token, lapor)
            }
        }
    }
    private fun toast(message : String) = Toast.makeText(this@ReportActivity, message, Toast.LENGTH_LONG).show()

    private fun setErrorNama(err : String?){ til_nama.error = err }
    private fun setErrorNoTelp(err : String?){ til_no_telp.error = err }
    private fun setErrorJalan(err : String?){ til_alamat.error = err }
    private fun setErrorDesa(err : String?){ til_desa.error = err }
    private fun setErrorKecamatan(err : String?){ til_kecamatan.error = err }
    private fun setErrorKota(err : String?){ til_kota.error = err }
    private fun setErrorJenisNarkoba(err : String?){ til_jenis_narkoba.error = err }
    private fun setErrorPekerjaan(err : String?){ til_pekerjaan.error = err }
    private fun setErrorKegiatan(err : String?){ til_kegiatan.error = err }
    private fun setErrorTransaksi(err : String?){ til_tmpt_transaksi.error = err }

}