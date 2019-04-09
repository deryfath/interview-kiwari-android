package com.example.dai_01.qiwarytest.dagger.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.dai_01.qiwarytest.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(val app:App) {

    @Provides
    @Singleton
    fun application() : Application = app

    @Provides
    @Singleton
    fun context() : Context = app.applicationContext

    @Provides
    @Singleton
    fun sharedPreference(app:Application):SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(app)
    }
}