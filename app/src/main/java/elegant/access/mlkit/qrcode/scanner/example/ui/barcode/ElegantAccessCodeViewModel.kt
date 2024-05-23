package elegant.access.mlkit.qrcode.scanner.example.ui.barcode

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import elegant.access.mlkit.qrcode.scanner.example.R
import elegant.access.mlkit.qrcode.scanner.example.base.utils.ScanCodeParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
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
class ElegantAccessCodeViewModel : ViewModel(),KoinComponent {

    private var mediaPlayer: MediaPlayer? = null

    var commandLiveData: MutableLiveData<Command> = MutableLiveData()

    sealed class Command {
        data class ShowCaptureZoomTip(val isVisible: Boolean) : Command()
        data class ScanQrResult(val isValid: Boolean, val shareCode: String) : Command()
    }

    companion object {
        private const val ZOOM_TIP_TIMEOUT_IN_MS = 3_000L
        private const val VIBRATE_DURATION = 200L
        private const val BEEP_VOLUME = 0.50f
    }

    suspend fun showCaptureZoomTip() {
        withContext(Dispatchers.IO) {
            commandLiveData.postValue(Command.ShowCaptureZoomTip(true))
            delay(ZOOM_TIP_TIMEOUT_IN_MS)
            commandLiveData.postValue(Command.ShowCaptureZoomTip(false))
        }
    }

    fun parserScanCode(result: String?, qrCodeKey: String) {
        val scanCodeParser = ScanCodeParser(result,qrCodeKey)
        val isValid = qrCodeKey.isBlank() || scanCodeParser.isValid()
        val shareCode = if (isValid&&qrCodeKey.isNotBlank()) scanCodeParser.getShareCode() else result ?: ""
        commandLiveData.postValue(Command.ScanQrResult(isValid, shareCode))
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