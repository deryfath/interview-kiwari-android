package com.example.dai_01.qiwarytest.activity.main

import com.example.dai_01.qiwarytest.model.ProductResponse
import com.example.dai_01.qiwarytest.model.Products
import com.example.dai_01.qiwarytest.mvp.View


interface MainView :View {

   fun onLoadAddSuccess(message : ProductResponse)

   fun onLoadAddFailed(message: String)

   fun onLoadEditSuccess(message : ProductResponse)

   fun onLoadEditFailed(message: String)

   fun onLoadDeleteSuccess(message : String)

   fun onLoadDeleteFailed(message: String)

   fun onLoadSuccess(message : List<ProductResponse>)

   fun onLoadFailed(message: String)
}