package elegant.access.mlkit.qrcode.scanner.example.ui.main

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Vibrator
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import elegant.access.mlkit.qrcode.scanner.example.R
import elegant.access.mlkit.qrcode.scanner.example.base.mlkit.ScannerManager
import elegant.access.mlkit.qrcode.scanner.example.base.mlkit.ScannerViewState
import org.koin.core.component.KoinComponent
import java.io.IOException

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
class MainViewModel(private val scannerManager: ScannerManager) : ViewModel(), KoinComponent {

    companion object {
        private const val ZOOM_TIP_TIMEOUT_IN_MS = 3_000L
        private const val VIBRATE_DURATION = 200L
        private const val BEEP_VOLUME = 0.50f
    }

    private var mediaPlayer: MediaPlayer? = null

    fun initCamera(
        viewLifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        onResult: (state: ScannerViewState, result: String) -> Unit,
    ) {
        scannerManager.init(viewLifecycleOwner, previewView, onResult)
        startCamera()
    }

    fun setOnScreenScaleTouchEvent(event: MotionEvent): Boolean {
        return scannerManager.scaleGestureDetector.onTouchEvent(event)
    }

    fun setOnTapToFocus(event: MotionEvent) {
        scannerManager.onTapToFocus(event)
    }

    fun stopCamera() {
        scannerManager.stopCamera()
    }

    fun startCamera() {
        scannerManager.startCamera()
    }

    fun initBeepSound(context: Context) {
        val playBeep = shouldPlayBeep(context)
        if (playBeep && mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setOnCompletionListener { mediaPlayer -> mediaPlayer.seekTo(0) }
            }
            val file = context.resources.openRawResourceFd(R.raw.qr_code_beep)
            try {
                mediaPlayer?.setDataSource(file.fileDescriptor, file.startOffset, file.length)
                file.close()
                mediaPlayer?.setVolume(BEEP_VOLUME, BEEP_VOLUME)
                mediaPlayer?.prepare()
            } catch (e: IOException) {
                mediaPlayer = null
            }
        }
    }

    private fun shouldPlayBeep(context: Context): Boolean {
        val audioService = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioService.ringerMode == AudioManager.RINGER_MODE_NORMAL
    }

    fun playBeepSoundAndVibrate(context: Context) {
        if (shouldPlayBeep(context)) {
            mediaPlayer?.start()
        }
        val vibrator = context.getSystemService(AppCompatActivity.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VIBRATE_DURATION)
    }
}