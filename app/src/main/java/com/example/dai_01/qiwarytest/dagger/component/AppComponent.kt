package com.example.dai_01.qiwarytest.dagger.component

import com.example.dai_01.qiwarytest.activity.main.MainActivity
import com.example.dai_01.qiwarytest.dagger.module.ApiModule
import com.example.dai_01.qiwarytest.dagger.module.AppModule
import com.example.dai_01.qiwarytest.dagger.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        ApiModule::class
))

interface AppComponent {

    fun inject(mainActivity:MainActivity)
}