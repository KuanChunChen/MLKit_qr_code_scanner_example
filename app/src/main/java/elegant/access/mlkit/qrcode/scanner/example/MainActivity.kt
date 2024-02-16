package elegant.access.mlkit.qrcode.scanner.example

import android.Manifest
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import elegant.access.mlkit.qrcode.scanner.example.base.mlkit.ScannerViewState
import elegant.access.mlkit.qrcode.scanner.example.base.utils.checkAndRequestPermission
import elegant.access.mlkit.qrcode.scanner.example.databinding.ActivityMainBinding
import elegant.access.mlkit.qrcode.scanner.example.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {


    private var _vb: ActivityMainBinding? = null
    private val vb get() = _vb!!
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        checkAndRequestPermission(Manifest.permission.CAMERA,
            onPermissionGranted = {
                viewModel.initCamera(this, vb.previewView, ::onResult)
                viewModel.initBeepSound()
                initZoomListener()
            }, onPermissionDenied = {

            })
    }

    override fun onResume() {
        super.onResume()


    }

    private fun onResult(state: ScannerViewState, result: String?) {
        when(state)
        {
            ScannerViewState.Success -> {
                viewModel.playBeepSoundAndVibrate(this)
//                viewModel.parserScanCode(result)
            }
            ScannerViewState.Error -> {
//                invalidScanCodeDialog.show()
            }
        }
    }

    private fun initZoomListener() {

        vb.captureContainer.setOnTouchListener { _: View?, event: MotionEvent? ->
            event?.let {
                viewModel.setOnScreenScaleTouchEvent(it)
            }?: true
        }
    }
}