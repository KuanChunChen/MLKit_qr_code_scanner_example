package elegant.access.mlkit.qrcode.scanner.example.ui.barcode

import android.content.Context
import android.view.ScaleGestureDetector
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import elegant.access.mlkit.qrcode.scanner.example.base.mlkit.ScannerLifecycleObserver
import elegant.access.mlkit.qrcode.scanner.example.base.mlkit.ScannerViewState

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
open class BaseBarCodeActivity : AppCompatActivity() {

    private lateinit var scannerLifecycleObserver: ScannerLifecycleObserver

    internal fun initCamera(
        viewLifecycleOwner: LifecycleOwner,
        context: Context,
        previewView: PreviewView,
        onResult: (state: ScannerViewState, result: String) -> Unit,
    ) {
        scannerLifecycleObserver = ScannerLifecycleObserver(
            owner = viewLifecycleOwner,
            context = context,
            viewPreview = previewView,
            onResult = onResult,
            lensFacing = CameraSelector.LENS_FACING_BACK
        )
    }

    fun stopCamera() {
        scannerLifecycleObserver.stopCamera()
    }

    fun startCamera() {
        scannerLifecycleObserver.startCamera()
    }

    fun getScaleGestureDetector(): ScaleGestureDetector {
        return scannerLifecycleObserver.scaleGestureDetector
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}