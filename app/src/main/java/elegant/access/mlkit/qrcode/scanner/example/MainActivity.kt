package elegant.access.mlkit.qrcode.scanner.example

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import elegant.access.mlkit.qrcode.scanner.example.base.mlkit.ScannerViewState
import elegant.access.mlkit.qrcode.scanner.example.base.utils.checkAndRequestPermission
import elegant.access.mlkit.qrcode.scanner.example.databinding.ActivityMainBinding
import elegant.access.mlkit.qrcode.scanner.example.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private var _vb: ActivityMainBinding? = null
    private val vb get() = _vb!!
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        checkAndRequestPermission(Manifest.permission.CAMERA,
            onPermissionGranted = {
                Log.d(TAG, "onPermissionGranted")
                viewModel.initCamera(this, vb.previewView, ::onResult)
                viewModel.initBeepSound(this)
                initZoomListener()
            }, onPermissionDenied = {
                Log.d(TAG, "onPermissionDenied")

            })
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    private fun onResult(state: ScannerViewState, result: String?) {
        viewModel.stopCamera()
        when (state) {
            ScannerViewState.Success -> {
                viewModel.playBeepSoundAndVibrate(this)
                Log.d(TAG, "Success : $result")
                showDialog("$result")
            }

            ScannerViewState.Error -> {
                showDialog(getString(R.string.device_qr_code_invalid))
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initZoomListener() {

        vb.captureContainer.setOnTouchListener { _: View?, event: MotionEvent? ->
            event?.let {
                when (it.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        // Only trigger focus on down events.
                        viewModel.setOnTapToFocus(it)
                    }
                }
                viewModel.setOnScreenScaleTouchEvent(it)
            } ?: true
        }
    }

    private fun showDialog(title: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(title)
            .setPositiveButton(getString(R.string.dialog_positive_content)) { _, _ ->
                viewModel.startCamera()
            }
            .show()
    }
}