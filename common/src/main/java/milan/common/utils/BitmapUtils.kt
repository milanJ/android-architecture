package milan.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.io.File

fun isDarkAsync(bitmap: Bitmap): Single<Boolean> {
    return Single.fromCallable {
        isDark(bitmap)
    }
}

fun isDark(bitmap: Bitmap): Boolean {
    var dark = false

    val darkThreshold = bitmap.width.toFloat() * bitmap.height.toFloat() * 0.45f
    var darkPixels = 0

    val pixels = IntArray(bitmap.width * bitmap.height)
    bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

    for (pixel in pixels) {
        val r = Color.red(pixel)
        val g = Color.green(pixel)
        val b = Color.blue(pixel)
        val luminance = 0.299 * r + 0.0 + 0.587 * g + 0.0 + 0.114 * b + 0.0
        if (luminance < 150) {
            darkPixels++
        }
    }

    if (darkPixels >= darkThreshold) {
        dark = true
    }
    return dark
}

fun loadBitmapOfHeightMaintainAspectRatio(file: File, dstHeight: Int): Bitmap? {
    val decodeResource: Bitmap?
    var createScaledBitmap: Bitmap?

    try {
        var options = BitmapFactory.Options()
                .apply {
                    inJustDecodeBounds = true
                }
        BitmapFactory.decodeFile(file.path, options)
        var bitmapHeight = options.outHeight

        var temp = bitmapHeight
        var sampleSize = 1
        while (temp / 2 >= dstHeight) {
            temp /= 2
            sampleSize++
        }

        if (sampleSize > 1) {
            options = BitmapFactory.Options()
                    .apply {
                        inSampleSize = sampleSize
                    }
            decodeResource = BitmapFactory.decodeFile(file.path, options)
        } else {
            decodeResource = BitmapFactory.decodeFile(file.path)
        }

        if (decodeResource != null) {
            if (decodeResource.height == dstHeight) {
                createScaledBitmap = decodeResource
                return createScaledBitmap
            }

            try {
                val scaleFactor = dstHeight.toFloat() / decodeResource.height.toFloat()
                val dstWidth = (decodeResource.width.toFloat() * scaleFactor).toInt()

                createScaledBitmap = Bitmap.createScaledBitmap(decodeResource, dstWidth, dstHeight, true)
                decodeResource.recycle()
            } catch (e: OutOfMemoryError) {
                Timber.e(e, "Failed to create bitmap.");
                createScaledBitmap = decodeResource
            } catch (e2: Exception) {
                Timber.e(e2, "Failed to create bitmap.");
                createScaledBitmap = decodeResource
            }
        } else {
            createScaledBitmap = null
        }
    } catch (error: OutOfMemoryError) {
        Timber.e(error, "Failed to create bitmap.");
        error.printStackTrace()
        createScaledBitmap = null
    } catch (exception: Exception) {
        Timber.e(exception, "Failed to create bitmap.");
        exception.printStackTrace()
        createScaledBitmap = null
    }

    return createScaledBitmap
}

fun loadBitmapOfMaxHeightMaintainAspectRatio(file: File, maxHeight: Int): Bitmap? {
    val decodeResource: Bitmap?
    var createScaledBitmap: Bitmap?

    try {
        var bitmapHeight = BitmapFactory.Options()
                .apply {
                    inJustDecodeBounds = true
                }
                .also {
                    BitmapFactory.decodeFile(file.path, it)
                }
                .outHeight

        if (maxHeight > bitmapHeight) {
            return BitmapFactory.decodeFile(file.path)
        } else {
            var temp = bitmapHeight
            var sampleSize = 1
            while (temp / 2 >= maxHeight) {
                temp /= 2
                sampleSize++
            }

            if (sampleSize > 1) {
                decodeResource = BitmapFactory.decodeFile(file.path, BitmapFactory.Options()
                        .apply {
                            inSampleSize = sampleSize
                        })
            } else {
                decodeResource = BitmapFactory.decodeFile(file.path)
            }

            if (decodeResource != null) {
                if (decodeResource.height == maxHeight) {
                    createScaledBitmap = decodeResource
                    return createScaledBitmap
                }

                try {
                    val scaleFactor = maxHeight.toFloat() / decodeResource.height.toFloat()
                    val dstWidth = (decodeResource.width.toFloat() * scaleFactor).toInt()

                    createScaledBitmap = Bitmap.createScaledBitmap(decodeResource, dstWidth, maxHeight, true)
                    decodeResource.recycle()
                } catch (e: OutOfMemoryError) {
                    Timber.e(e, "Failed to create bitmap.");
                    createScaledBitmap = decodeResource
                } catch (e2: Exception) {
                    Timber.e(e2, "Failed to create bitmap.");
                    createScaledBitmap = decodeResource
                }
            } else {
                createScaledBitmap = null
            }
        }
    } catch (error: OutOfMemoryError) {
        Timber.e(error, "Failed to create bitmap.");
        error.printStackTrace()
        createScaledBitmap = null
    } catch (exception: Exception) {
        Timber.e(exception, "Failed to create bitmap.");
        exception.printStackTrace()
        createScaledBitmap = null
    }

    return createScaledBitmap
}
