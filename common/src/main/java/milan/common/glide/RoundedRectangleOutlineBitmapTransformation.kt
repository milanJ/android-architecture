package milan.common.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest

class RoundedRectangleOutlineBitmapTransformation constructor(private val roundingRadius: Int,
                                                              private val outlineWidth: Int,
                                                              private val outlineColor: Int) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        if (toTransform.isMutable) {
            Canvas(toTransform).apply {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = outlineColor
                    strokeWidth = outlineWidth.toFloat()
                    style = Paint.Style.STROKE
                }
                val owFloat = (outlineWidth.toFloat() / 2F)
                drawRoundRect(owFloat, owFloat, width.toFloat() - owFloat, height.toFloat() - owFloat, roundingRadius.toFloat(), roundingRadius.toFloat(), paint)
            }
            return toTransform
        } else {
            return pool.get(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888).apply {

                Canvas(this).apply {
                    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        color = outlineColor
                        strokeWidth = outlineWidth.toFloat()
                        style = Paint.Style.STROKE
                    }

                    drawBitmap(toTransform, 0F, 0F, paint)

                    val owFloat = (outlineWidth.toFloat() / 2F)
                    drawRoundRect(owFloat, owFloat, width.toFloat() - owFloat, height.toFloat() - owFloat, roundingRadius.toFloat(), roundingRadius.toFloat(), paint)
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is RoundedRectangleOutlineBitmapTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private const val ID = "milan.common.glide.RoundedRectangleOutlineBitmapTransformation"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }
}
