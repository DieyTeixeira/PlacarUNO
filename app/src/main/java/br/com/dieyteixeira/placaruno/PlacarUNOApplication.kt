package br.com.dieyteixeira.placaruno

import android.app.Application
import br.com.dieyteixeira.placaruno.di.appModule
import br.com.dieyteixeira.placaruno.di.firebaseModule
import br.com.dieyteixeira.placaruno.di.storageModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PlacarUNOApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@PlacarUNOApplication)
            modules(
                appModule,
                storageModule,
                firebaseModule
            )
        }
    }
}