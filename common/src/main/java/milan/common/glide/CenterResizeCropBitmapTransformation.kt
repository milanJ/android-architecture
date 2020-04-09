package milan.common.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Re-sizes the bitmap to have the size of the ImageView by scaling it down and drawing it aligned
 * to the bottom, which means that the top of the bitmap will be transparent.
 */
class CenterResizeCropBitmapTransformation : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return if (toTransform.width == outWidth && toTransform.height == outHeight) {
            toTransform
        } else {
            pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888).apply {
                Canvas(this).apply {
                    val aspectRatio = outWidth.toFloat() / toTransform.width.toFloat()
                    val srcHeight = outHeight.toFloat() / aspectRatio
                    val srcTop = (toTransform.height - srcHeight) / 2F

                    val srcRect = Rect(0, srcTop.toInt(), toTransform.width, (srcTop + srcHeight).toInt())
                    val dstRect = Rect(0, 0, outWidth, outHeight)

                    drawBitmap(toTransform, srcRect, dstRect, null)
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is CenterResizeCropBitmapTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private const val ID = "milan.common.glide.CenterResizeCropBitmapTransformation"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }
}
