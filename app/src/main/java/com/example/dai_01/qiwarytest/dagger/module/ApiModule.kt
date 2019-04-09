package com.example.dai_01.qiwarytest.dagger.module

import com.example.dai_01.qiwarytest.dagger.qualifier.Authorized
import com.example.dai_01.qiwarytest.service.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Authorized
    fun apiServiceAuth(@Authorized retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

}