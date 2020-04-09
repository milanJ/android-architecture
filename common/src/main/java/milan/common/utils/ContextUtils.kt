package milan.common.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.os.ConfigurationCompat
import milan.common.R

fun Activity.closeKeyboard() {
    // Check if no view has focus:
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.enableLightStatusBar(enabled: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        var flags = window.decorView.systemUiVisibility
        if (enabled) {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // Add LIGHT_STATUS_BAR to flag
            window.decorView.systemUiVisibility = flags
        } else {
            flags = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // Remove LIGHT_STATUS_BAR flag via XOR
            window.decorView.systemUiVisibility = flags
        }
    }
}

fun Context.dipToPixels(dipValue: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics)

fun Context.getAppVersionName(): String {
    try {
        return packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }
    return ""
}

fun Context.fetchPrimaryColor(): Int {
    val typedValue = TypedValue()
    val typedArray = obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimary))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}

fun Context.fetchAccentColor(): Int {
    val typedValue = TypedValue()
    val typedArray = obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}

fun Context.getLocaleCode(): String {
//            val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
//            val localStringBuilder = StringBuilder(8)
//            if (!TextUtils.isEmpty(locale.language)) {
//                localStringBuilder.append(locale.language)
//            }
//            if (!TextUtils.isEmpty(locale.country)) {
//                if (localStringBuilder.isNotEmpty()) {
//                    localStringBuilder.append('_')
//                }
//                localStringBuilder.append(locale.country)
//            }
//            return locale.toString()

    return ConfigurationCompat.getLocales(resources.configuration)[0].toString()
}
