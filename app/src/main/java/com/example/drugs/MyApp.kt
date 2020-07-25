package com.example.drugs

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.drugs.repositories.*
import com.example.drugs.ui.drugs.DrugViewModel
import com.example.drugs.ui.login.LoginViewModel
import com.example.drugs.ui.main.home.HomeViewModel
import com.example.drugs.ui.profile.ProfileViewModel
import com.example.drugs.ui.register.RegisterViewModel
import com.example.drugs.ui.report.ReportViewModel
import com.example.drugs.webservices.ApiClient
import com.example.drugs.webservices.Constants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(retrofitModules, repositoryModule, viewModelModule))
        }
        setupNotificationManager()
    }

    private fun setupNotificationManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(Constants.channelId, Constants.channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.description = Constants.channelDesc
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

}



val retrofitModules = module {
    single { ApiClient.instance() }
}

val repositoryModule = module {
    factory { UserRepository(get()) }
    factory { AddictiveSubstanceRepository(get()) }
    factory { NarcoticRepository(get()) }
    factory { RehabilitationRepository(get()) }
    factory { PsychotropicRepository(get()) }
    factory { ReportRepository(get()) }
    factory { FirebaseRepository() }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DrugViewModel(get(), get(), get()) }
    viewModel { ReportViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}