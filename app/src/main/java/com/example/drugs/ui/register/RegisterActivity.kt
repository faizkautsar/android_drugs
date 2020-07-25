package com.example.drugs.ui.register

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.drugs.R
import com.example.drugs.extensions.disabled
import com.example.drugs.extensions.enabled
import com.example.drugs.extensions.showInfoAlert
import com.example.drugs.models.User
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : AppCompatActivity() {
    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        observe()
        reg()
    }

    private fun observe(){
        observeState()
    }

    private fun observeState() = registerViewModel.getState().observer(this, Observer { handleUI(it) })

    private fun handleUI(it : RegisterState){
        when(it){
            is RegisterState.Alert -> showInfoAlert(it.message)
            is RegisterState.Loading -> {
                if (it.state){
                    btn_register.disabled()
                }else{
                    btn_register.enabled()
                }
            }

            is RegisterState.Success-> {
                popup(it.param, "verifikasi email dahulu")
            }
            is RegisterState.Reset -> {
                setErrorNama(null)
                setErrorEmail(null)
                setErrorPassword(null)
                setErrorRepassword(null)
                setErrorNoTelp(null)
                setErrorJalan(null)
                setErrorDesa(null)
                setErrorKecamatan(null)
                setErrorKota(null)

            }
            is RegisterState.Validate -> {
                setErrorNama(it.nama)
                setErrorEmail(it.email)
                setErrorPassword(it.pass)
                setErrorRepassword(it.rePass)
                setErrorNoTelp(it.no_telp)
                setErrorJalan(it.jalan)
                setErrorDesa(it.desa)
                setErrorKecamatan(it.kecamatan)
                setErrorKota(it.kota)
            }

        }
    }

    private fun reg(){
        btn_register.setOnClickListener {
            val nama = ed_nama.text.toString().trim()
            val email = ed_email.text.toString().trim()
            val pass = ed_pass.text.toString().trim()
            val repass = ed_repass.text.toString().trim()
            val no_telp = ed_no_telp.text.toString().trim()
            val jalan = ed_jalan.text.toString().trim()
            val desa = ed_desa.text.toString().trim()
            val kecamatan = ed_kecamatan.text.toString().trim()
            val kota = ed_kota.text.toString().trim()
            //validate here
            val user = User(nama = nama,password = pass ,email = email, no_telp = no_telp, jalan = jalan, desa = desa,
                kecamatan = kecamatan, kota = kota)
            if (registerViewModel.Validate(nama, email, pass, repass, no_telp, jalan, desa, kecamatan, kota)){
                registerViewModel.register(user)
            }
        }
    }

    private fun popup(token : String, message: String){
        AlertDialog.Builder(this@RegisterActivity).apply {
            setMessage(message)
            setNegativeButton("profil"){dialog, _ ->
                dialog.dismiss()
                Constants.setToken(this@RegisterActivity, "Bearer ${token}")
                setResult(Activity.RESULT_OK)
                finish()
                //startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
        }.show()
    }

    private fun nameFilter(){

    }

    private fun setErrorNama(err : String?){ til_nama.error = err }
    private fun setErrorEmail(err : String?){ til_email.error = err }
    private fun setErrorPassword(err : String?){ til_pass.error = err }
    private fun setErrorRepassword(err : String?){ til_repass.error = err }
    private fun setErrorNoTelp(err : String?){ til_no_telp.error = err }
    private fun setErrorJalan(err : String?){ til_jalan.error = err }
    private fun setErrorDesa(err : String?){ til_desa.error = err }
    private fun setErrorKecamatan(err : String?){ til_kecamatan.error = err }
    private fun setErrorKota(err : String?){ til_kota.error = err }

}
