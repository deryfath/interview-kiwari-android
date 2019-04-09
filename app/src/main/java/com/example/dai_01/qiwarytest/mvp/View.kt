package com.example.dai_01.qiwarytest.mvp

import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable

interface View {

    fun onAttach()
    fun onDetach()

    fun lifecycle(): Observable<ActivityEvent>
    fun <T> bindTolifeCycle(): LifecycleTransformer<T>
}