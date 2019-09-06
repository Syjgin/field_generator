package com.syjgin.fieldgenerator.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    protected val prefs : SharedPreferences by lazy { applicationContext.getSharedPreferences(applicationContext.packageName, MODE_PRIVATE) }
}