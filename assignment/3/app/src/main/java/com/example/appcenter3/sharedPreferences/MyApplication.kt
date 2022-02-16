package com.example.appcenter3.sharedPreferences

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var beforeprefs: BeforeSharedPref
        lateinit var ingprefs: IngSharedPref
        lateinit var doneprefs:DoneSharedPref
    }

    override fun onCreate() {
        beforeprefs = BeforeSharedPref(applicationContext)
        ingprefs = IngSharedPref(applicationContext)
        doneprefs = DoneSharedPref(applicationContext)
        super.onCreate()
    }

}

