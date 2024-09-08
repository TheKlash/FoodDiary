package de.alekseipopov.fooddiary

import android.app.Application
import de.alekseipopov.fooddiary.data.di.dataModule
import de.alekseipopov.fooddiary.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                uiModule
            )
        }
    }
}