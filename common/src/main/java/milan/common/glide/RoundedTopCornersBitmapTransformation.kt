package milan.common.glide

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest

class RoundedTopCornersBitmapTransformation constructor(private val roundingRadius: Int) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return pool.get(toTransform.width, toTransform.height, Bitmap.Config.ARGB_8888).apply {

            Canvas(this).apply {
                val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    shader = BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                }
                val rrFloat = roundingRadius.toFloat()

                drawRoundRect(0F, 0F, width.toFloat(), height.toFloat(), rrFloat, rrFloat, paint)
                drawRect(0F, rrFloat + 1F, width.toFloat(), height.toFloat(), paint)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is RoundedTopCornersBitmapTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private const val ID = "milan.common.glide.RoundedTopCornersBitmapTransformation"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }
}
