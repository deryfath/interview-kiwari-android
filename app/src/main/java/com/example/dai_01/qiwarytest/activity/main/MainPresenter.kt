package com.example.dai_01.qiwarytest.activity.main

import com.example.dai_01.qiwarytest.dagger.qualifier.Authorized
import com.example.dai_01.qiwarytest.extension.errorConverter
import com.example.dai_01.qiwarytest.model.ProductRequest
import com.example.dai_01.qiwarytest.mvp.Presenter
import com.example.dai_01.qiwarytest.service.ApiService
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import okhttp3.RequestBody



class MainPresenter @Inject constructor(
        @Authorized val api: ApiService,
        val retrofit: Retrofit
): Presenter<MainView>{

    private var view : MainView? = null

    var productListDisposables = Disposables.empty()
    var productAddDisposables = Disposables.empty()
    var productEditDisposables = Disposables.empty()
    var productDeleteDisposables = Disposables.empty()

    override fun onAttach(view: MainView) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    fun loadProductList(){

        productListDisposables.dispose()
        productListDisposables = api.getProducts()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->

                    view?.onLoadSuccess(res.data)

                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response<Throwable>>(err)
                        view?.onLoadFailed("Error: ${body}")
                    } else {
                        view?.onLoadFailed(err.localizedMessage)
                    }
                })
    }

    fun addProduct(product:String){

        productAddDisposables.dispose()
        val bodySend = RequestBody.create(MediaType.parse("text/json"), product)

        productAddDisposables = api.insertProduct(bodySend)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->

                    view?.onLoadAddSuccess(res.data)

                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response<Throwable>>(err)
                        view?.onLoadAddFailed("Error: ${body}")
                    } else {
                        view?.onLoadAddFailed(err.localizedMessage)
                    }
                })
    }

    fun editProduct(product:String, id:Int){

        productEditDisposables.dispose()
        val bodySend = RequestBody.create(MediaType.parse("text/json"), product)

        productEditDisposables = api.updateProduct(id,bodySend)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->

                    view?.onLoadEditSuccess(res.data)

                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response<Throwable>>(err)
                        view?.onLoadEditFailed("Error: ${body}")
                    } else {
                        view?.onLoadEditFailed(err.localizedMessage)
                    }
                })
    }


    fun deleteProduct(id:Int){

        productDeleteDisposables.dispose()
        productDeleteDisposables = api.deleteProduct(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    res ->

                    view?.onLoadDeleteSuccess(res.msg)

                }, {
                    err ->
                    if (err is HttpException) {
                        val body = retrofit.errorConverter<Response<Throwable>>(err)
                        view?.onLoadDeleteFailed("Error: ${body}")
                    } else {
                        view?.onLoadDeleteFailed(err.localizedMessage)
                    }
                })
    }

}