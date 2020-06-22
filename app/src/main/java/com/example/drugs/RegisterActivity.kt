package com.example.drugs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.drugs.extensions.disabled
import com.example.drugs.extensions.enabled
import com.example.drugs.viewmodels.UserState
import com.example.drugs.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.http.Field

class RegisterActivity : AppCompatActivity() {

    private lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.listenToState().observer(this, Observer { handleUI(it) })
        reg()
    }

    private fun handleUI(it : UserState){
        when(it){
            is UserState.ShowToast -> toast(it.message)
            is UserState.IsLoading -> {
                if (it.state){
                    btn_register.disabled()
                }else{
                    btn_register.enabled()
                }
            }

            is UserState.Success-> startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun reg(){
        btn_register.setOnClickListener {
            val nama = ed_nama.text.toString().trim()
            val email = ed_email.text.toString().trim()
            val pass = ed_pass.text.toString().trim()
            val no_telp = ed_no_telp.text.toString().trim()
            val alamat = ed_alamat.text.toString().trim()
            val desa = ed_desa.text.toString().trim()
            val kecamatan = ed_kecamatan.text.toString().trim()
            val kode_pos = ed_kode_pos.text.toString().trim()

            userViewModel.register(nama, email, pass, no_telp, alamat, desa, kecamatan, kode_pos)
        }

    }

    private fun toast(message : String) = Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
}
