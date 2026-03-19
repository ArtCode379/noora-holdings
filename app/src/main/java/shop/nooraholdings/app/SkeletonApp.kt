package shop.nooraholdings.app

import android.app.Application
import shop.nooraholdings.app.di.dataModule
import shop.nooraholdings.app.di.dispatcherModule
import shop.nooraholdings.app.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class NORHDApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@NORHDApp)
            modules(appModules)
        }
    }
}