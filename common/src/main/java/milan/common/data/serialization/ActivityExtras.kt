package milan.common.data.serialization

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

interface ActivityExtras {

    fun intent(context: Context): Intent

    fun launch(context: Context) = context.startActivity(intent(context))

    fun launchForResult(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(intent(activity), requestCode)
    }

    fun launchForResult(fragment: Fragment, requestCode: Int) {
        fragment.startActivityForResult(intent(fragment.context!!), requestCode)
    }

    fun launchWithActivityTransitions(activity: Activity) {
        activity.startActivity(
            intent(activity),
            ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
        )
    }

    fun launchWithActivityTransitions(activity: Activity, sharedElement: View) {
        val sharedElementName = ViewCompat.getTransitionName(sharedElement)
        val intent = intent(activity).apply {
            putExtra(EXTRA_TRANSITION_SHARED_ELEMENT_NAME, sharedElementName)
        }
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName)
            .toBundle()

        activity.startActivity(intent, activityOptions)
    }

    fun launchWithActivityTransitions(activity: Activity, sharedElement: View, sharedElementName: String) {
        activity.startActivity(
            intent(activity),
            ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName).toBundle()
        )
    }

    fun launchForResultWithActivityTransitions(activity: Activity, requestCode: Int) {
        activity.startActivityForResult(
            intent(activity),
            requestCode,
            ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
        )
    }

    fun launchForResultWithActivityTransitions(activity: Activity, intentFlags: Int, requestCode: Int) {
        val intent = intent(activity).apply {
            addFlags(intentFlags)
        }

        activity.startActivityForResult(
            intent,
            requestCode,
            ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
        )
    }

    fun launchForResultWithActivityTransitions(activity: Activity, requestCode: Int, sharedElement: View) {
        val sharedElementName = ViewCompat.getTransitionName(sharedElement)
        val intent = intent(activity).apply {
            putExtra(EXTRA_TRANSITION_SHARED_ELEMENT_NAME, sharedElementName)
        }
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName)
            .toBundle()

        activity.startActivityForResult(intent, requestCode, activityOptions)
    }

    fun launchForResultWithActivityTransitions(
        activity: Activity,
        intentFlags: Int,
        requestCode: Int,
        sharedElement: View
    ) {
        launchForResultWithActivityTransitions(activity, intentFlags, requestCode, sharedElement, null)
    }

    fun launchForResultWithActivityTransitions(
        activity: Activity,
        intentFlags: Int,
        requestCode: Int,
        sharedElement: View,
        initialSharedElementName: String?
    ) {
        val sharedElementName = ViewCompat.getTransitionName(sharedElement)
        val intent = intent(activity).apply {
            putExtra(EXTRA_TRANSITION_SHARED_ELEMENT_NAME, sharedElementName)
            putExtra(
                EXTRA_INITIAL_TRANSITION_SHARED_ELEMENT_NAME, initialSharedElementName
                    ?: sharedElementName
            )
            addFlags(intentFlags)
        }
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, sharedElement, sharedElementName)
            .toBundle()

        activity.startActivityForResult(intent, requestCode, activityOptions)
    }

    companion object {

        private const val EXTRA_INITIAL_TRANSITION_SHARED_ELEMENT_NAME = "EXTRA_INITIAL_TRANSITION_SHARED_ELEMENT_NAME"
        private const val EXTRA_TRANSITION_SHARED_ELEMENT_NAME = "EXTRA_TRANSITION_SHARED_ELEMENT_NAME"

        fun getTransitionSharedElementName(intent: Intent): String? {
            return if (intent.extras == null) {
                null
            } else {
                intent.extras
                    ?.getString(EXTRA_TRANSITION_SHARED_ELEMENT_NAME)
            }
        }

        fun getInitialTransitionSharedElementName(intent: Intent): String? {
            return if (intent.extras == null) {
                null
            } else {
                intent.extras
                    ?.getString(EXTRA_INITIAL_TRANSITION_SHARED_ELEMENT_NAME)
            }
        }

        fun areTransitionElementNamesSame(intent: Intent): Boolean {
            val def = intent.getStringExtra(EXTRA_TRANSITION_SHARED_ELEMENT_NAME)
            val ini = intent.getStringExtra(EXTRA_INITIAL_TRANSITION_SHARED_ELEMENT_NAME)
            return def == ini
        }
    }
}
