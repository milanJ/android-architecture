package milan.common.utils

import android.view.View
import android.view.animation.Animation
import io.reactivex.rxjava3.core.Completable
import milan.common.R

fun slideUp(view: View): Animation {
    return android.view.animation.AnimationUtils.loadAnimation(view.context.applicationContext, R.anim.slide_up)
}

fun slideDown(view: View): Animation {
    return android.view.animation.AnimationUtils.loadAnimation(view.context.applicationContext, R.anim.slide_down)
}

fun scaleDownAndBackAndRun(view: View): Completable {
    return scaleDownAndBackAndRun(view, 0.6F)
}

fun scaleDownAndBackAndRun(view: View, amount: Float): Completable {
    val animUp = Completable.create {
        view.animate()
                .scaleX(amount)
                .scaleY(amount)
                .setDuration(150L)
                .withEndAction(it::onComplete)
    }
    val animDown = Completable.create {
        view.animate()
                .scaleX(1F)
                .scaleY(1F)
                .setDuration(150L)
                .withEndAction(it::onComplete)
    }
    return animUp.andThen(animDown)
}
