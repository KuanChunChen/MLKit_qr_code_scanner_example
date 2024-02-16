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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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

abstract class BaseCameraManager(private val context: Context, private var lensFacing: Int) : DefaultLifecycleObserver {
    private var imgCapture: ImageCapture? = null
    private lateinit var cameraProvider: ProcessCameraProvider
    private var stopped: Boolean = false
    protected var camera:  Camera?= null
    private var flashMode: Int = ImageCapture.FLASH_MODE_OFF
    private lateinit var owner: LifecycleOwner
    private lateinit var viewPreview: PreviewView
    private var showHideFlashIcon: (show: Int) -> Unit = {}
    var onResult: (state: ScannerViewState, result: String) -> Unit = { _, _ -> }

    protected val cameraExecutor: ExecutorService by lazy {
        Executors.newSingleThreadExecutor()
    }

    fun init(
        owner: LifecycleOwner,
        viewPreview: PreviewView,
        onResult: (state: ScannerViewState, result: String) -> Unit,
    ) {
        this.owner = owner
        this.viewPreview = viewPreview
        this.onResult = onResult
        owner.lifecycle.addObserver(this)
    }

    fun startCamera(isSwitchButtonClicked: Boolean = false) {
        if (!this::cameraProvider.isInitialized) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                cameraProvider = cameraProviderFuture.get()
                controlWhichCameraToDisplay(isSwitchButtonClicked)
                bindCameraUseCases()
            }, ContextCompat.getMainExecutor(context))
        } else {
            bindCameraUseCases()
        }
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
        val cameraSelector = getCameraSelector()
        val previewView = getPreviewUseCase()
        imgCapture = getImageCapture()
        cameraProvider.unbindAll()
        try {
            imgCapture?.let {
                bindToLifecycle(cameraProvider, owner, cameraSelector, previewView, it)
            }
            previewView.setSurfaceProvider(viewPreview.surfaceProvider)
        } catch (exc: Exception) {
            Log.e(ContentValues.TAG, "Use case binding failed $exc")
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        if (this::cameraProvider.isInitialized) {
            cameraProvider.unbindAll()
            stopped = true
            super.onPause(owner)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        if (stopped) {
            bindCameraUseCases()
            stopped = false
        }
        super.onResume(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cameraExecutor.shutdown()
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
        if (this::cameraProvider.isInitialized) {
            cameraProvider.unbindAll()
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