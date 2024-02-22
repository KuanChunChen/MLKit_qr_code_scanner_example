package elegant.access.mlkit.qrcode.scanner.example.base.mlkit

import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.util.Size
import android.view.WindowManager
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

class ScannerManager(private val context: Context, lensFacing: Int) : BaseCameraManager(context, lensFacing) {

    private fun getCameraDisplayOrientation(): Point {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val metrics = DisplayMetrics().also { display.getRealMetrics(it) }
        val screenResolution = Point(metrics.widthPixels, metrics.heightPixels)
        val screenResolutionForCamera = Point()

        screenResolutionForCamera.x = screenResolution.x
        screenResolutionForCamera.y = screenResolution.y
        if (screenResolution.x < screenResolution.y) {
            screenResolutionForCamera.x = screenResolution.y
            screenResolutionForCamera.y = screenResolution.x
        }
        return screenResolutionForCamera
    }

    private fun getImageAnalysis(): ImageAnalysis {
        val screenResolutionForCamera = getCameraDisplayOrientation()
        Log.d("Debug","getImageAnalysis, width = ${screenResolutionForCamera.x} , height : ${screenResolutionForCamera.y}")
        return ImageAnalysis.Builder()
            .setTargetResolution(Size(screenResolutionForCamera.x, screenResolutionForCamera.y))
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