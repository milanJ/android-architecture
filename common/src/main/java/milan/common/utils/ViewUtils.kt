package milan.common.utils

/**
 * Extension functions for View and subclasses of View.
 */

import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.suspendCancellableCoroutine
import milan.common.data.SingleLiveEvent
import kotlin.coroutines.resume

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

/**
 * Extension function to simplify setting an afterTextChanged action to TextInputEditText components.
 */
fun TextInputEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackBar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

/**
 * Triggers a SnackBar message when the value contained by snackBarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackBar(
        lifecycleOwner: LifecycleOwner,
        snackbarMessageLiveEvent: SingleLiveEvent<String>, timeLength: Int
) {
    snackbarMessageLiveEvent.observe(lifecycleOwner, Observer {
        it?.let { showSnackBar(it, timeLength) }
    })
}

suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { cont ->
    // This lambda is invoked immediately, allowing us to create a callback/listener.
    val listener = object : View.OnLayoutChangeListener {

        override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
        ) {
            // The next layout has happened!
            // First remove the listener to not leak the coroutine
            view.removeOnLayoutChangeListener(this)
            // Finally resume the continuation, and wake the coroutine up
            cont.resume(Unit)
        }
    }

    // If the coroutine is cancelled, remove the listener
    cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }

    // And finally add the listener to view
    addOnLayoutChangeListener(listener)

    // The coroutine will now be suspended. It will only be resumed when calling cont.resume()
    // in the listener above.
}

fun View.dipToPixels(dipValue: Float) =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics)

fun ViewGroup.relayoutChildren() {
    measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY))
    layout(left, top, right, bottom)
}


