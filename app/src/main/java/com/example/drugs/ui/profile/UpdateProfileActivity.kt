package com.example.drugs.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.Gravity.apply
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.drugs.R
import com.example.drugs.extensions.disabled
import com.example.drugs.extensions.enabled
import com.example.drugs.extensions.showInfoAlert
import com.example.drugs.extensions.toast
import com.example.drugs.models.User
import com.example.drugs.utils.SingleResponse
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.activity_update_profile.toolbar
import kotlinx.android.synthetic.main.conten_update_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateProfileActivity : AppCompatActivity() {
    private val profileViewModel : ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        setSupportActionBar(toolbar)
        observe()
        update()
        setupToolbar()
        getpasseduser()?.let {

            et_nama.setText(it.nama)
            et_no_telp.setText(it.no_telp)
            et_jalan.setText(it.jalan )
            et_desa.setText(it.desa)
            et_kecamatan.setText(it.kecamatan)
            et_kota.setText(it.kota)
        }

    }
    private fun setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { finish() }
    }
        private fun observe(){
            observeState()
        }

        private fun observeState() = profileViewModel.listenToState().observer(this, Observer { handleUI(it) })

        private fun handleUI(it : ProfileState){
            when(it){
                is ProfileState.SuccessUpdate -> {
                    toast("berhasil update profil")
                    finish()
                }
                is ProfileState.Alert -> showInfoAlert(it.message)
                is ProfileState.Loading -> {
                    if (it.state){
                        btn_update.disabled()
                    }else{
                        btn_update.enabled()
                    }
                }

                is ProfileState.Reset -> {
                    setErrorNama(null)
                    setErrorNoTelp(null)
                    setErrorJalan(null)
                    setErrorDesa(null)
                    setErrorKecamatan(null)
                    setErrorKota(null)
                }
                is ProfileState.Validate -> {
                    setErrorNama(it.nama)
                    setErrorNoTelp(it.no_telp)
                    setErrorJalan(it.jalan)
                    setErrorDesa(it.desa)
                    setErrorKecamatan(it.kecamatan)
                    setErrorKota(it.kota)
                }

            }
        }

        private fun update(){
            btn_update.setOnClickListener {
                val token  = Constants.getToken(this@UpdateProfileActivity)
                val nama = et_nama.text.toString().trim()
                val no_telp = et_no_telp.text.toString().trim()
                val jalan = et_jalan.text.toString().trim()
                val desa = et_desa.text.toString().trim()
                val kecamatan = et_kecamatan.text.toString().trim()
                val kota = et_kota.text.toString().trim()
                //validate here
                val user = User(nama = nama, no_telp = no_telp, jalan = jalan, desa = desa, kecamatan = kecamatan, kota = kota)
                if (profileViewModel.validate(nama, no_telp, jalan, desa, kecamatan, kota)){
                    profileViewModel.Update(token, user)
                }
            }
        }

        private fun getpasseduser() : User ? = intent.getParcelableExtra("USER")
        private fun setErrorNama(err : String?){ til_nama.error = err }
        private fun setErrorNoTelp(err : String?){ til_no_telp.error = err }
        private fun setErrorJalan(err : String?){ til_jalan.error = err }
        private fun setErrorDesa(err : String?){ til_desa.error = err }
        private fun setErrorKecamatan(err : String?){ til_kecamatan.error = err }
        private fun setErrorKota(err : String?){ til_kota.error = err }

    }