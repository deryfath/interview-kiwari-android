package com.example.dai_01.qiwarytest.activity.main.adapter

import android.content.DialogInterface
import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dai_01.qiwarytest.R
import com.example.dai_01.qiwarytest.activity.main.MainActivity
import com.example.dai_01.qiwarytest.activity.main.MainPresenter
import com.example.dai_01.qiwarytest.extension.inflate
import com.example.dai_01.qiwarytest.model.ProductRequest
import com.example.dai_01.qiwarytest.model.ProductResponse
import com.google.gson.GsonBuilder

class RecyclerProductAdapter (private val activity: MainActivity, private val presenter: MainPresenter, private val items:List<ProductResponse>) : RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerProductAdapter.ViewHolder {
        val inflatedView= parent!!.inflate(R.layout.item_product,false)
        return RecyclerProductAdapter.ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerProductAdapter.ViewHolder?, position: Int) {
        val view=holder?.itemView
        val data=items[position]

        view?.let {

            it.visibility= View.VISIBLE
            Glide.with(it.context).load(data.image).into(it.findViewById(R.id.iv_image_product) as ImageView)
            (it.findViewById(R.id.tv_name_product) as TextView).text=data.name
            (it.findViewById(R.id.tv_price_product) as TextView).text=data.price.toString()

        }

        view?.findViewById<ImageView>(R.id.iv_delete_product)!!.setOnClickListener {
            println("delete")

            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Hapus Produk Ini ?")
            builder.setPositiveButton("HAPUS", DialogInterface.OnClickListener {
                dialogInterface, i ->  presenter.deleteProduct(data.id)
            })
            builder.setNegativeButton("BATAL", null)
            builder.show()

        }

        view?.findViewById<ImageView>(R.id.iv_edit_product)!!.setOnClickListener {
            println("EDIT ${data.price}")

            activity.STATE_CONFIRM = 1
            activity.EDIT_ID = data.id
            activity.dialog.show()

            activity.dialog.findViewById<EditText>(R.id.et_product_name).setText(data.name)
            activity.dialog.findViewById<EditText>(R.id.et_product_price).setText(data.price.toString())
            activity.dialog.findViewById<EditText>(R.id.et_product_image).setText(data.image)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

    }
}