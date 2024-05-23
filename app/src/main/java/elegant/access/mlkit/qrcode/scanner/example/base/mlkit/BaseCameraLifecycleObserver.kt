package elegant.access.mlkit.qrcode.scanner.example.base.mlkit

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.ScaleGestureDetector
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

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
abstract class BaseCameraLifecycleObserver(
    private val owner: LifecycleOwner,
    private val context: Context,
    private val viewPreview: PreviewView,
    private var lensFacing: Int,
    private val showHideFlashIcon: (show: Int) -> Unit
) : DefaultLifecycleObserver, CoroutineScope {
    override val coroutineContext: CoroutineContext = owner.lifecycleScope.coroutineContext + SupervisorJob()

    private val cameraProviderDeferred by lazy {
        CompletableDeferred<ProcessCameraProvider>().apply {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                complete(cameraProviderFuture.get())
            }, ContextCompat.getMainExecutor(context))
        }
    }

    private var stopped: Boolean = false
    protected var camera:  Camera?= null
    private var flashMode: Int = ImageCapture.FLASH_MODE_OFF

    protected val cameraExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    init {
        owner.lifecycle.addObserver(this)
    }

    fun startCamera(isSwitchButtonClicked: Boolean = false) {
        controlWhichCameraToDisplay(isSwitchButtonClicked)
        bindCameraUseCases()
    }

    private fun controlWhichCameraToDisplay(isSwitchButtonClicked: Boolean): Int {
        if (isSwitchButtonClicked) {
            lensFacing =
                if (lensFacing == CameraSelector.LENS_FACING_FRONT) CameraSelector.LENS_FACING_BACK
                else CameraSelector.LENS_FACING_FRONT
        } else lensFacing
        showHideFlashIcon(lensFacing)
        return lensFacing
    }

    private fun bindCameraUseCases() {
        launch {
            val cameraSelector = getCameraSelector()
            val previewView = getPreviewUseCase()
            val imgCapture: ImageCapture = getImageCapture()
            cameraProviderDeferred.await().unbindAll()
            try {
                bindToLifecycle(cameraProviderDeferred.await(), owner, cameraSelector, previewView, imgCapture)
                previewView.setSurfaceProvider(viewPreview.surfaceProvider)
            } catch (exc: Exception) {
                Log.e(ContentValues.TAG, "Use case binding failed $exc")
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        launch {
            cameraProviderDeferred.await().unbindAll()
            stopped = true
        }
        super.onPause(owner)
    }

    override fun onResume(owner: LifecycleOwner) {
        launch {
            if (stopped) {
                bindCameraUseCases()
                stopped = false
            }
        }
        super.onResume(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cameraExecutor.shutdown()
        coroutineContext.cancelChildren()
    }

    protected abstract fun bindToLifecycle(
        cameraProvider: ProcessCameraProvider,
        owner: LifecycleOwner,
        cameraSelector: CameraSelector,
        previewView: Preview,
        imageCapture: ImageCapture
    )

    private fun getCameraSelector(): CameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    private fun getPreviewUseCase(): Preview = Preview.Builder()
        .build()

    private fun getImageCapture(): ImageCapture =
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setFlashMode(flashMode).build()

    fun stopCamera() {
        launch {
            cameraProviderDeferred.await().unbindAll()
        }
    }

    val scaleGestureDetector = ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val zoomRatio = camera?.cameraInfo?.zoomState?.value?.zoomRatio ?: 1f
            camera?.cameraControl?.setZoomRatio(zoomRatio * detector.scaleFactor)
            return true
        }
    })

}