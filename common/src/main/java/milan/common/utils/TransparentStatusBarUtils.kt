package milan.common.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import milan.common.R


fun makeStatusBarTransparentAndDrawContentBelowIt(activity: Activity, toolbar: View) {
    // Ensure content view `fitsSystemWindows` is false.
    val contentParent = activity.findViewById<View>(android.R.id.content) as ViewGroup
    val content = contentParent.getChildAt(0)
    // If using `DrawerLayout`, must ensure its subviews `fitsSystemWindows` are all false.
    // Because in some roms, such as MIUI, it will fits system windows for each subview.
    setFitsSystemWindows(content, false, true)

    // Add padding to hold the status bar place.
    clipToStatusBar(toolbar)

    // Add a view to hold the status bar place.
    // Note: if using appbar_scrolling_view_behavior of CoordinatorLayout, however,
    // the holder view could be scrolled to outside as it above the app bar.
    //holdStatusBar(toolbar, R.color.colorPrimary);
}

internal fun setFitsSystemWindows(view: View?, fitSystemWindows: Boolean, applyToChildren: Boolean) {
    if (view == null) return
    view.fitsSystemWindows = fitSystemWindows
    if (applyToChildren && view is ViewGroup) {
        val viewGroup = view as ViewGroup?
        var i = 0
        val n = viewGroup!!.childCount
        while (i < n) {
            viewGroup.getChildAt(i).fitsSystemWindows = fitSystemWindows
            i++
        }
    }
}

internal fun clipToStatusBar(view: View) {
    val statusBarHeight = getStatusBarHeight(view.context.resources)
    val toolbarHeight = getToolBarHeight(view.context)
    view.layoutParams.height = toolbarHeight + statusBarHeight
    view.setPadding(0, statusBarHeight, 0, 0)
}

internal fun getToolBarHeight(context: Context): Int {
    val attrs = intArrayOf(R.attr.actionBarSize)
    val ta = context.obtainStyledAttributes(attrs)
    val toolBarHeight = ta.getDimensionPixelSize(0, -1)
    ta.recycle()
    return toolBarHeight
}

internal fun getStatusBarHeight(resources: Resources): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun setMiuiStatusBarIconDarkMode(activity: Activity, dark: Boolean): Boolean {
    val clazz = activity.window.javaClass
    try {
        val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
        val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
        var darkModeFlag = field.getInt(layoutParams)
        clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                .invoke(activity.window, if (dark) darkModeFlag else 0, darkModeFlag)
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

fun setFlymeStatusBarIconDarkMode(activity: Activity, dark: Boolean): Boolean {
    try {
        val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
        val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
        darkFlag.isAccessible = true
        meizuFlags.isAccessible = true
        val bit = darkFlag.getInt(null)

        val lp = activity.window.attributes

        var value = meizuFlags.getInt(lp)
        if (dark) {
            value = value or bit
        } else {
            value = value and bit.inv()
        }
        meizuFlags.setInt(lp, value)
        activity.window.attributes = lp
        return true
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}
