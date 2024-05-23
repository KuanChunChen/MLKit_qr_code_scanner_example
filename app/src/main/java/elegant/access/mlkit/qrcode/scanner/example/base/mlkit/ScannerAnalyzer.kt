package elegant.access.mlkit.qrcode.scanner.example.base.mlkit

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
class ScannerAnalyzer(
    private val onResult: (state: ScannerViewState, barcode: String) -> Unit
) : ImageAnalysis.Analyzer {

    private val delayForProcessingNextImage = 300L

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val options =
            BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
        val scanner = BarcodeScanning.getClient(options)
        val mediaImage = imageProxy.image ?: return onResult(ScannerViewState.Error, "Image is empty")
        InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            .let { image ->
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            onResult(ScannerViewState.Success, barcode.rawValue ?: "")
                        }
                    }
                    .addOnFailureListener {
                        onResult(ScannerViewState.Error, it.message.toString())
                    }
                    .addOnCompleteListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            delay(delayForProcessingNextImage)
                            imageProxy.close()
                        }
                    }
            }
    }
}