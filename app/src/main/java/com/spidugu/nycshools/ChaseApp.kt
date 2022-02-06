package com.spidugu.nycshools

import android.app.Application
import com.spidugu.nycshools.di.appModule
import com.spidugu.nycshools.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class ChaseApp : Application() {

    private var koinApp: KoinApplication? = null

    override fun onCreate() {
        super.onCreate()
        koinApp ?: let {
            koinApp = startKoin {
                androidContext(this@ChaseApp)
                modules(porteApp)
            }
        }
    }
}

val porteApp = listOf(appModule, viewModelModule)