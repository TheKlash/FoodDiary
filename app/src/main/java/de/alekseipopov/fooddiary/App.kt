package de.alekseipopov.fooddiary

import android.app.Application
import de.alekseipopov.fooddiary.data.di.dataModule
import de.alekseipopov.fooddiary.domain.di.domainModule
import de.alekseipopov.fooddiary.ui.details.di.detailsModule
import de.alekseipopov.fooddiary.ui.overview.di.overviewModule
import de.alekseipopov.fooddiary.ui.report.di.reportModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                domainModule,
                overviewModule,
                detailsModule,
                reportModule
            )
        }
    }
}