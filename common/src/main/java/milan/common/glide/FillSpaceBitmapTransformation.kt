package milan.common.glide

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Re-sizes the bitmap to fit the entire space of the ImageView.
 */
class FillSpaceBitmapTransformation : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return if (toTransform.width == outWidth && toTransform.height == outHeight) {
            toTransform
        } else Bitmap.createScaledBitmap(toTransform, outWidth, outHeight, true)
    }

    override fun equals(other: Any?): Boolean {
        return other is FillSpaceBitmapTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private const val ID = "milan.common.glide.FillSpace"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }
}
