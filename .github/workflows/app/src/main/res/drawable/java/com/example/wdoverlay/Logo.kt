package com.example.wdoverlay

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable

/** The logo image was left out of this copy, so the app simply skips the logo background. */
object Logo {
    val bitmap: Bitmap? = null
}

/**
 * Draws the logo as a faint background (does nothing while there is no logo image).
 */
class LogoBackground(private val fit: Boolean, alphaValue: Int) : Drawable() {
    private val paint = Paint(Paint.FILTER_BITMAP_FLAG).apply { alpha = alphaValue }

    override fun draw(canvas: Canvas) {
        val b = bounds
        if (b.isEmpty) return
        val bmp = Logo.bitmap ?: return
        val sx = b.width().toFloat() / bmp.width
        val sy = b.height().toFloat() / bmp.height
        val s = if (fit) minOf(sx, sy) else maxOf(sx, sy)
        val w = bmp.width * s
        val h = bmp.height * s
        val l = b.left + (b.width() - w) / 2f
        val t = b.top + (b.height() - h) / 2f
        canvas.save()
        canvas.clipRect(b)
        canvas.drawBitmap(bmp, null, RectF(l, t, l + w, t + h), paint)
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) { paint.alpha = alpha; invalidateSelf() }
    override fun setColorFilter(colorFilter: ColorFilter?) { paint.colorFilter = colorFilter }
    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}
