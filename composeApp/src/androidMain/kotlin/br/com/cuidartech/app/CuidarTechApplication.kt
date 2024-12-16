package br.com.cuidartech.app

import android.app.Application
import br.com.cuidartech.app.di.initKoin
import org.koin.android.ext.koin.androidContext

class CuidarTechApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@CuidarTechApplication)
        }
    }
}