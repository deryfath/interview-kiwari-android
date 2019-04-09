package com.example.dai_01.qiwarytest.dagger.module

import android.app.Application
import android.content.SharedPreferences
import com.example.dai_01.qiwarytest.BuildConfig
import com.example.dai_01.qiwarytest.dagger.qualifier.Authorized
import com.example.dai_01.qiwarytest.service.Interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule(val baseUrl:String) {

    @Provides
    @Singleton
    fun cache(app: Application): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        val cache = Cache(app.cacheDir, cacheSize)
        return cache
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    fun httpClient(logger: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(logger)
        builder.cache(cache)
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    @Authorized
    fun httpClientAuth(logger: HttpLoggingInterceptor, cache: Cache, pref: SharedPreferences): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.addInterceptor(AuthInterceptor(pref))
        builder.addInterceptor(logger)
        builder.cache(cache)
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    @Authorized
    fun retrofitAuth(@Authorized client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

}