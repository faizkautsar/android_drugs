package com.example.drugs.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import coil.api.load
import com.example.drugs.R
import com.example.drugs.extensions.gone
import com.example.drugs.extensions.showInfoAlert
import com.example.drugs.extensions.toast
import com.example.drugs.extensions.visible
import com.example.drugs.models.User
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.Constants
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.MediaFile
import pl.aprilapps.easyphotopicker.MediaSource
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private lateinit var easyImage : EasyImage
    private val profileViewModel : ProfileViewModel by viewModel()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)
        observe()
        fetchProfile()
        setupEasyImae()
        pickImage()
        checkPermisson()
        setupToolbar()

    }
    private fun setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener { finish() }
    }
    private fun setupEasyImae(){
        easyImage = EasyImage.Builder(this@ProfileActivity)
            .setFolderName("Choose image")
            .allowMultiple(false)
            .build()
    }


    private fun checkPermisson(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this@ProfileActivity, "Aplikasi tidak berjalan tanpa izin ke storage", Toast.LENGTH_LONG).show()
                finish()
            } else {
                ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 20)
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this@ProfileActivity, "Aplikasi tidak berjalan tanpa izin ke storage", Toast.LENGTH_LONG).show()
                finish()
            } else {
                ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 20)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            20 -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("IntroAct", "Permission has been denied by user")
                } else {
                    Log.i("IntroAct", "Permission has been granted by user")
                }
            }
        }
    }

    private fun pickImage(){
        camera_action .setOnClickListener {
            easyImage.openGallery(this@ProfileActivity)
        }
    }

    private fun observe(){
        observeState()
        observeUser()
        observeImagePath()
    }


    private fun handleImagePath(path: String){
        if(path.isNotEmpty()){
            img_profile.load(File(path))
            profileViewModel.uploadImage(Constants.getToken(this@ProfileActivity), path)
        }
    }
    private fun observeImagePath() = profileViewModel.lisrenToImagePath().observe(this, Observer { handleImagePath(it) })

    private fun handleUser(it: User?){
        it?.let {
            //tx_name.text = it.nama
            img_profile.load(ApiClient.ENDPOINT+"public/"+it.foto)
            img_profile.setOnClickListener {_->
                startActivity(Intent(this,FullscreenActivity::class.java).apply{
                    putExtra("FOTO", ApiClient.ENDPOINT+"public/"+it.foto)
                })
            }
            tx_nama.text = it.nama
            tx_email.text = it.email
            tx_no_telp.text = it.no_telp
            tx_jl.text = it.jalan
            tx_desa.text = it.desa
            tx_kec.text = it.kecamatan
            tx_kota.text = it.kota
            edit_profil.setOnClickListener {_->
                startActivity(Intent(this,UpdateProfileActivity::class.java).apply{
                    putExtra("USER", it)
                })
            }
        }
    }

    private fun handleState(s : ProfileState){
        when(s){
            is ProfileState.ShowToaast -> toast(s.message)
            is ProfileState.Loading -> isLoading(s.state)
            is ProfileState.Success -> fetchProfile()
        }
    }

    private fun isLoading(b: Boolean) = if(b) loading.visible() else loading.gone()
    private fun onPhotoReturned(images : Array<MediaFile>) = profileViewModel.setImagePath(images[0].file.absolutePath)
    private fun observeState() = profileViewModel.listenToState().observer(this, Observer { handleState(it) })
    private fun observeUser() = profileViewModel.listenToUser().observe(this, Observer { handleUser(it) })
    private fun fetchProfile() = profileViewModel.profile(Constants.getToken(this@ProfileActivity))


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        easyImage.handleActivityResult(requestCode, resultCode, data, this@ProfileActivity, object: EasyImage.Callbacks{
            override fun onCanceled(source: MediaSource) {}
            override fun onImagePickerError(error: Throwable, source: MediaSource) {}
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                //img_profile.load(imageFiles[0].file)
                onPhotoReturned(imageFiles)
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }
}
