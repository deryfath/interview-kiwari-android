package com.example.dai_01.qiwarytest.activity.main

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.dai_01.qiwarytest.App
import com.example.dai_01.qiwarytest.R
import com.example.dai_01.qiwarytest.activity.main.adapter.RecyclerProductAdapter
import com.example.dai_01.qiwarytest.extension.debug
import com.example.dai_01.qiwarytest.model.ProductRequest
import com.example.dai_01.qiwarytest.model.ProductResponse
import com.example.dai_01.qiwarytest.model.Products
import com.google.gson.GsonBuilder
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_product.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var adapter: RecyclerProductAdapter

    lateinit var dialog: Dialog

    var STATE_CONFIRM = -1
    var EDIT_ID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        App.component.inject(this)

        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.layout_bottom_sheet_product)
        dialog.setCancelable(true)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp

        onAttach()

    }

    fun confirmButtonListener(){

        dialog.findViewById<Button>(R.id.bt_confirm).setOnClickListener {

            val gson = GsonBuilder().setPrettyPrinting().create()

            var productName = dialog.findViewById<EditText>(R.id.et_product_name).text.toString()
            var productPrice = dialog.findViewById<EditText>(R.id.et_product_price).text.toString()
            var productImage = dialog.findViewById<EditText>(R.id.et_product_image).text.toString()


            if(productImage!="" && productName!="" && productPrice!=""){

                var productObj = ProductRequest(productImage,productName,Integer.parseInt(productPrice))
                if(STATE_CONFIRM == 0){

                    val jsonProductAdd = gson.toJson(productObj)
                    presenter.addProduct(jsonProductAdd)

                }else{

                    val jsonProductEdit = gson.toJson(productObj)
                    presenter.editProduct(jsonProductEdit,EDIT_ID)

                }
            }else{
                Toast.makeText(this,"DATA TIDAK BOLEH KOSONG",Toast.LENGTH_SHORT).show()
            }





        }
    }

    override fun onAttach() {

        presenter.onAttach(this)
        presenter.loadProductList()

        confirmButtonListener()

        tv_add_product.setOnClickListener {
            STATE_CONFIRM = 0
            dialog.findViewById<EditText>(R.id.et_product_name).setText("")
            dialog.findViewById<EditText>(R.id.et_product_price).setText("")
            dialog.findViewById<EditText>(R.id.et_product_image).setText("")
            dialog.show()
        }


    }

    private fun initiateRecyclerUpdateView(data:List<ProductResponse>) {
        rv_product.layoutManager = GridLayoutManager(this,2)
        adapter= RecyclerProductAdapter(this,presenter,data)
        rv_product.adapter=adapter

    }

    override fun onLoadAddSuccess(message: ProductResponse) {
        dialog.hide()
        presenter.loadProductList()

    }

    override fun onLoadAddFailed(message: String) {
        dialog.hide()
    }

    override fun onLoadEditSuccess(message: ProductResponse) {
        dialog.hide()
        presenter.loadProductList()
    }

    override fun onLoadEditFailed(message: String) {
    }

    override fun onLoadDeleteSuccess(message: String) {
        presenter.loadProductList()
    }

    override fun onLoadDeleteFailed(message: String) {
    }

    override fun onLoadSuccess(message: List<ProductResponse>) {
        println("PRODUCT SUCCESS : $message")
        initiateRecyclerUpdateView(message)
    }

    override fun onLoadFailed(message: String) {
        println("PRODUCT FAILED : $message")
    }

    override fun onDetach() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lifecycle(): Observable<ActivityEvent> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> bindTolifeCycle(): LifecycleTransformer<T> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        dialog.dismiss()
        finish()
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
