package com.example.dai_01.qiwarytest.extension

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <E> List<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null