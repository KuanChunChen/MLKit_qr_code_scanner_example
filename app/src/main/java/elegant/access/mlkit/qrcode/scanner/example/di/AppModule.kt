package elegant.access.mlkit.qrcode.scanner.example.di

import elegant.access.mlkit.qrcode.scanner.example.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

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

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}