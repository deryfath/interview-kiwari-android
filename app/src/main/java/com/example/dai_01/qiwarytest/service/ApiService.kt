package com.example.dai_01.qiwarytest.service

import com.example.dai_01.qiwarytest.model.ProductRequest
import com.example.dai_01.qiwarytest.model.ProductResponse
import com.example.dai_01.qiwarytest.model.Products
import com.example.dai_01.qiwarytest.model.ResponseData
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @GET("/products")
    fun getProducts(): Observable<ResponseData<List<ProductResponse>>>

    @GET("/products/{id}")
    fun getProductsById(@Path("id")id:Int): Observable<ResponseData<ProductResponse>>

    @POST("/products")
    fun insertProduct(@Body body:RequestBody): Observable<ResponseData<ProductResponse>>

    @PUT("/products/{id}")
    fun updateProduct(@Path("id")id:Int, @Body body: RequestBody):Observable<ResponseData<ProductResponse>>

    @DELETE("/products/{id}")
    fun deleteProduct(@Path("id")id:Int):Observable<ResponseData<List<Any>>>

}