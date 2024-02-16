package elegant.access.mlkit.qrcode.scanner.example

import android.app.Application
import elegant.access.mlkit.qrcode.scanner.example.di.appModule
import elegant.access.mlkit.qrcode.scanner.example.di.scannerModule
import elegant.access.mlkit.qrcode.scanner.example.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

/**
 * This file is part of an Android project developed by elegant.access.
 *
 * For more information about this project, you can visit our website:
 * {@link https://elegantaccess.org}
 *
 * Please note that this project is for educational purposes only and is not intended
 * for use in production environments.
 *
 * @author Willy.Chen
 * @version 1.0.0
 * @since 2020~2024
 */

class MLApplication : Application() {

    private fun defineDependencies() = listOf(
        appModule,
        scannerModule,
        viewModelModule,
    )

    override fun onCreate() {
        super.onCreate()
        injection()
    }

    private fun injection() {
        startKoin {
            androidLogger()
            androidContext(this@MLApplication)
            modules(defineDependencies())
        }
    }
}