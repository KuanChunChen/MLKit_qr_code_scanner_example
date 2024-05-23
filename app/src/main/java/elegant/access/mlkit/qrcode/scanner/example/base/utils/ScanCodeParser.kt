package elegant.access.mlkit.qrcode.scanner.example.base.utils

import android.util.Log

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

class ScanCodeParser(private val content: String?, qrCodeKey: String) {

    companion object {
        private const val TAG = "ScanCodeParser"
    }

    private val scanCodePegPattern = "$qrCodeKey=[0-9]{9}".toRegex(RegexOption.IGNORE_CASE)

    private val shareCode: String

    init {
        Log.d(TAG,"scan code $content")
        shareCode = content?.takeIf { scanCodePegPattern.containsMatchIn(it) }
            ?.split("$qrCodeKey=", ignoreCase = true)?.getOrNull(1)?.substring(0, 9)
            ?: "".also {  Log.d(TAG,"Invalid scanning code $content") }
    }

    fun isValid(): Boolean {
        return shareCode != ""
    }

    fun getShareCode() = shareCode
}