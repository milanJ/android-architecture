package milan.common.glide

import android.graphics.Bitmap
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Scale the image so that the width of the image matches the given width and the height of the
 * image is greater than the given height, and then crop the height to match the given size.
 */
class CenterCropViaWidthTransformation : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        if (toTransform.width != outWidth) {
            val scaleFactor = outWidth.toFloat() / toTransform.width.toFloat()
            val newOutHeight = (toTransform.height.toFloat() * scaleFactor).toInt()

            return TransformationUtils.centerCrop(pool, toTransform, outWidth, newOutHeight)
        }
        return toTransform
    }

    override fun equals(other: Any?): Boolean {
        return other is CenterCropViaWidthTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    companion object {
        private const val ID = "milan.common.glide.CenterCropViaWidthTransformation"
        private val ID_BYTES = ID.toByteArray(Charset.forName("UTF-8"))
    }
}
