package elegant.access.mlkit.qrcode.scanner.example.base.mlkit

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.LensFacing
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

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

sealed class ScannerViewState {
    object Success : ScannerViewState()
    object Error : ScannerViewState()
}

class ScannerManager(context: Context, lensFacing: Int) : BaseCameraManager(context, lensFacing) {


    private fun getImageAnalysis(): ImageAnalysis {
        return ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, ScannerAnalyzer(onResult))
            }
    }

    override fun bindToLifecycle(
        cameraProvider: ProcessCameraProvider,
        owner: LifecycleOwner,
        cameraSelector: CameraSelector,
        previewView: Preview,
        imageCapture: ImageCapture
    ) {
        camera = cameraProvider.bindToLifecycle(
            owner,
            cameraSelector,
            previewView,
            getImageAnalysis(),
            imageCapture
        )
    }
}