package milan.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import kotlin.math.roundToInt

class BlurBuilder {

    private var bitmapScale = BITMAP_SCALE
    private var blurRadius = BLUR_RADIUS

    fun setScale(scale: Float): BlurBuilder {
        bitmapScale = scale
        return this
    }

    fun setRadius(radius: Float): BlurBuilder {
        blurRadius = radius
        return this
    }

    fun blur(context: Context, image: Bitmap): Bitmap {
        val inputBitmap: Bitmap
        if (bitmapScale.roundToInt() == 1) {
            inputBitmap = image
        } else {
            val width = Math.round(image.width * bitmapScale)
            val height = Math.round(image.height * bitmapScale)
            inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)

        }
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)

        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

        val intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        intrinsicBlur.setRadius(blurRadius)
        intrinsicBlur.setInput(tmpIn)
        intrinsicBlur.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        rs.destroy()

        return outputBitmap
    }

    companion object {
        private const val BITMAP_SCALE = 1F // 0.6F
        private const val BLUR_RADIUS = 15F
    }
}
