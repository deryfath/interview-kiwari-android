package com.example.dai_01.qiwarytest.extension

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

@Throws(IOException::class)
inline fun <reified T> Retrofit.errorConverter(httpException: HttpException):T{
    val converter:Converter<ResponseBody,T> = this.responseBodyConverter(T::class.java,arrayOfNulls<Annotation>(0))
    val error :T = converter.convert(httpException.response().errorBody())
    return error
}