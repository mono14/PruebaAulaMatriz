package com.example.primeraclaseandroid

import android.app.Application
import com.google.android.gms.ads.MobileAds

class AulaMoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}