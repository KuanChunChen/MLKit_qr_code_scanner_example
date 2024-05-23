package elegant.access.mlkit.qrcode.scanner.example

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import elegant.access.mlkit.qrcode.scanner.example.constants.BarCodeConstant
import elegant.access.mlkit.qrcode.scanner.example.ui.barcode.ElegantAccessBarCodeActivity
import kotlinx.coroutines.launch

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

class MainActivity : ElegantAccessBarCodeActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    /**If there are some key to decode qrcode,you can set it here
     * Or you can get to ScanCodeParser to refactor your decode logic**/
    override val qrCodeKey: String
        get() = ""

    override val onScanResult: (shareCode: String) -> Unit
        get() = { qrcode ->
            Log.d(TAG,"qr code : $qrcode")
            showDialog("Success!Qr Code: $qrcode")
        }

    override val onScanFailed: (errorCode: Int) -> Unit
        get() = { errorCode ->
            Log.d(TAG,"error code : $errorCode")
            when (errorCode) {
                BarCodeConstant.SCAN_ERROR_ANALYZE_FAILED,
                BarCodeConstant.SCAN_ERROR_NOT_QR_CODE_FORMAT -> {
                    showDialog("Error code: $errorCode")
                }
            }
        }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            elegantAccessCodeViewModel.showCaptureZoomTip()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        applyStyles {
            zoomTipTextStyle {
                text = getString(R.string.zoom_tips)
            }
            captureHintTextStyle {
                text = getString(R.string.common_scan_pc_code)
            }
            toolbarStyle {
                val color = ContextCompat.getColor(this@MainActivity, android.R.color.darker_gray)
                title = getString(R.string.app_name)
                setTitleTextColor(color)
            }
        }
    }

}