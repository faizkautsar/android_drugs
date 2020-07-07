package com.example.drugs

import android.app.Application
import com.example.drugs.repositories.*
import com.example.drugs.ui.drugs.DrugViewModel
import com.example.drugs.ui.login.LoginViewModel
import com.example.drugs.ui.main.home.HomeViewModel
import com.example.drugs.ui.register.RegisterViewModel
import com.example.drugs.ui.report.ReportViewModel
import com.example.drugs.webservices.ApiClient
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
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { DrugViewModel(get(), get(), get()) }
    viewModel { ReportViewModel(get()) }
}