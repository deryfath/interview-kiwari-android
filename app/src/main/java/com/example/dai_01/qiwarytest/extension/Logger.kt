package com.example.dai_01.qiwarytest.extension

import android.app.Activity
import android.app.Application
import android.app.Fragment
import android.util.Log

/**
 * Created by ARIF on 12-Jun-17.
 */
fun Activity.debug(message: String, tag: String? = null) : Unit {
    if (tag != null) Log.d(tag, message)
    else Log.d(this.javaClass.simpleName, message)
}

fun Fragment.debug(message: String, tag: String? = null) : Unit {
    if (tag != null) Log.d(tag, message)
    else Log.d(this.javaClass.simpleName, message)
}

fun Application.debug(message: String, tag: String? = null): Unit {
    if (tag != null) Log.d(tag, message)
    else Log.d(this.javaClass.simpleName, message)
}

fun debug(message: String, tag: String = "DEBUG"): Unit {
    Log.d(tag, message)
}