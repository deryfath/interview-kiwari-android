package com.example.dai_01.qiwarytest.mvp

interface Presenter<in T: View> {

    fun onAttach(view: T)
    fun onDetach()

}