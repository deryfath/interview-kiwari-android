package com.example.dai_01.qiwarytest

import android.app.Application
import com.example.dai_01.qiwarytest.dagger.component.AppComponent
import com.example.dai_01.qiwarytest.dagger.component.DaggerAppComponent
import com.example.dai_01.qiwarytest.dagger.module.ApiModule
import com.example.dai_01.qiwarytest.dagger.module.AppModule
import com.example.dai_01.qiwarytest.dagger.module.NetworkModule

class App : Application() {

    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .networkModule(NetworkModule("http://belanja-api.herokuapp.com/"))
                    .apiModule(ApiModule())
                    .build()
    }
}