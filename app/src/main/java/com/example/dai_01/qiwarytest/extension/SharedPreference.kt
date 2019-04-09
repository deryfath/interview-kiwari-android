package com.example.dai_01.qiwarytest.extension

import android.content.SharedPreferences


fun SharedPreferences.get(name: String) : String? {
    return this.getString(name, null)
}

fun <T> SharedPreferences.save(name: String, data: T) {
    this.edit().putString(name, data.toString()).apply()
}

fun SharedPreferences.remove(name: String) {
    this.edit().putString(name, null).apply()
}

fun SharedPreferences.clear() {
    this.edit().clear().apply()
}