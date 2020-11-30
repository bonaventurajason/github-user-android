package com.bonaventurajason.consumerapp

import android.app.Application
import timber.log.Timber

class GithubUserApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}