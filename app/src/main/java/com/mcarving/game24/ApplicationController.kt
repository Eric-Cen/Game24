package com.mcarving.game24

import android.app.Application
import timber.log.Timber

class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }

        Timber.i("BuildConfig.VERSION_NAME=%s, VERSION_CODE=%d", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

}