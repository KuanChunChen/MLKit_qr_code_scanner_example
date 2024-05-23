package elegant.access.mlkit.qrcode.scanner.example

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.ColorInt
import androidx.camera.view.PreviewView

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
class Style<T : View> {
    var block: (T.() -> Unit)? = null

    operator fun invoke(block: T.() -> Unit) {
        this.block = block
    }
}

object Styles {
    val previewViewStyle = Style<PreviewView>()
    val zoomTipTextStyle = Style<TextView>()
    val zoomTipImageStyle = Style<ImageView>()
    val captureHintTextStyle = Style<TextView>()
    val toolbarStyle = Style<Toolbar>()
}

fun Drawable.tint(@ColorInt color: Int): Drawable {
    val drawable = this.mutate()
    drawable.clearColorFilter()
    drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    return drawable
}
