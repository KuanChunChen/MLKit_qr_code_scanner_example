package elegant.access.mlkit.qrcode.scanner.example.ui.barcode

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toolbar
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import elegant.access.mlkit.qrcode.scanner.example.R
import elegant.access.mlkit.qrcode.scanner.example.Styles
import elegant.access.mlkit.qrcode.scanner.example.base.mlkit.ScannerViewState
import elegant.access.mlkit.qrcode.scanner.example.base.utils.registerPermissionRequest
import elegant.access.mlkit.qrcode.scanner.example.base.utils.viewBinding
import elegant.access.mlkit.qrcode.scanner.example.constants.BarCodeConstant
import elegant.access.mlkit.qrcode.scanner.example.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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
abstract class ElegantAccessBarCodeActivity : BaseBarCodeActivity() {

    private val vb by viewBinding(ActivityMainBinding::inflate)
    open val elegantAccessCodeViewModel: ElegantAccessCodeViewModel by viewModel<ElegantAccessCodeViewModel>()

    abstract val qrCodeKey: String
    abstract val onScanResult: (shareCode: String) -> Unit
    abstract val onScanFailed: (errorCode: Int) -> Unit

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        initObservable()
        requestPermissionLauncher = registerPermissionRequest( onPermissionGranted = {
            Log.d(TAG, "onPermissionGranted")
            initCamera(this, this@ElegantAccessBarCodeActivity, vb.previewView, ::onResult)
            startCamera()
            elegantAccessCodeViewModel.initBeepSound(context = this)
            initZoomListener()
        }, onPermissionDenied = {
            Log.d(TAG, "onPermissionDenied")

        })
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    fun applyStyles(block: Styles.() -> Unit) {
        Styles.apply(block)
        Styles.previewViewStyle.block?.let { vb.previewView.apply(it) }
        Styles.zoomTipTextStyle.block?.let { vb.tvZoomTip.apply(it) }
        Styles.zoomTipImageStyle.block?.let { vb.ivZoomTip.apply(it)}
        Styles.captureHintTextStyle.block?.let { vb.captureHintText.apply(it) }
        Styles.toolbarStyle.block?.let {
            val toolbar = Toolbar(this)
            toolbar.apply(it)
            vb.captureContainer.addView(toolbar)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionResult(requestCode, grantResults)
    }

    private fun handlePermissionResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initCamera(this, this@ElegantAccessBarCodeActivity, vb.previewView, ::onResult)
                    startCamera()
                } else {
                    finish()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initZoomListener() {
        vb.captureContainer.setOnTouchListener { _: View?, event: MotionEvent? ->
            event?.let {
                getScaleGestureDetector().onTouchEvent(it)
            }?: true
        }
    }

    private fun onResult(state: ScannerViewState, result: String?) {
        when (state) {
            ScannerViewState.Success -> {
                elegantAccessCodeViewModel.playBeepSoundAndVibrate(this)
                elegantAccessCodeViewModel.parserScanCode(result, qrCodeKey)
            }

            ScannerViewState.Error -> {
                onScanFailed.invoke(BarCodeConstant.SCAN_ERROR_ANALYZE_FAILED)
            }
        }
    }

    private fun initObservable() {
        elegantAccessCodeViewModel.commandLiveData.observe(this) {
            when (it) {
                is ElegantAccessCodeViewModel.Command.ShowCaptureZoomTip -> {
                    vb.zoomTipLayout.isVisible = it.isVisible

                }

                is ElegantAccessCodeViewModel.Command.ScanQrResult -> {
                    if (it.isValid) {
                        onScanResult.invoke(it.shareCode)
                    } else {
                        onScanFailed.invoke(BarCodeConstant.SCAN_ERROR_NOT_QR_CODE_FORMAT)
                    }
                    stopCamera()
                }
            }
        }
    }

    fun showDialog(message: String) {
        AlertDialog.Builder(this@ElegantAccessBarCodeActivity)
            .setTitle(R.string.app_name)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.dialog_positive_content)) { _, _ ->
                startCamera()

            }
            .show()
    }

    companion object {
        private const val TAG = "ElegantAccessBarCodeActivity"
        private const val REQUEST_CODE_CAMERA = 1
    }
}
