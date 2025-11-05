package com.kuikly.lottieview_android

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.tencent.kuikly.core.render.android.export.IKuiklyRenderViewExport
import com.tencent.kuikly.core.render.android.export.KuiklyRenderCallback
import kotlin.math.max
import kotlin.math.min

/**
 * 先实现最基本的功能，不必求全
 */
class AndroidLottieView(context: Context) :
    LottieAnimationView(context),
    IKuiklyRenderViewExport {

    private var autoPlay = true
    private var loop = true

    private val tmpMap = mutableMapOf<String, Float>()
    private val emptyMap = mutableMapOf<String, Any>()
    private var outAnimUpdateListener: KuiklyRenderCallback? = null
    private var outAnimRepeatListener: KuiklyRenderCallback? = null
    private var outAnimEndListener: KuiklyRenderCallback? = null
    private var outAnimLoadedListener: KuiklyRenderCallback? = null

    private var curAnimUpdateListener: ValueAnimator.AnimatorUpdateListener? = null
    private var curAnimStateListener: Animator.AnimatorListener? = null

    init {
        addLottieOnCompositionLoadedListener {
            Log.d(TAG, "onCompositionLoaded: ")
            repeatCount = if (loop) -1 else 0
            if (!isAnimating && autoPlay) {
                playAnimation()
            }
            outAnimLoadedListener?.invoke(emptyMap)
        }
    }

    private fun createAnimateUpdateListener() = ValueAnimator.AnimatorUpdateListener { animation ->
        animation.animatedFraction.apply {
            tmpMap["value"] = this
            outAnimUpdateListener?.invoke(tmpMap)
        }
    }

    private fun createAnimationListener() = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            Log.d(TAG, "onAnimationStart: ")
        }

        override fun onAnimationEnd(animation: Animator) {
            Log.d(TAG, "onAnimationEnd: ")
            outAnimEndListener?.invoke(emptyMap)
        }

        override fun onAnimationCancel(animation: Animator) {
            Log.d(TAG, "onAnimationCancel: ")
        }

        override fun onAnimationRepeat(animation: Animator) {
            Log.d(TAG, "onAnimationRepeat: ")
            outAnimRepeatListener?.invoke(emptyMap)
        }
    }

    override fun setProp(propKey: String, propValue: Any): Boolean {
        Log.d(TAG, "setProp: $propKey $propValue")
        return when (propKey) {
            SRC -> setSrc(propValue)
            LOOP -> setRepeatCount(propValue)
            AUTO_PLAY -> setAutoPlay(propValue)
            ON_ANIMATION_UPDATE -> {
                outAnimUpdateListener = propValue as KuiklyRenderCallback
                setupOnAnimateUpdateListener()
            }

            ON_ANIMATION_LOADED -> {
                outAnimLoadedListener = propValue as KuiklyRenderCallback
                true
            }

            ON_ANIMATION_REPEAT -> {
                outAnimRepeatListener = propValue as KuiklyRenderCallback
                setupOnAnimationListener()
            }

            ON_ANIMATION_COMPLETE -> {
                outAnimEndListener = propValue as KuiklyRenderCallback
                setupOnAnimationListener()
            }

            else -> super.setProp(propKey, propValue)
        }
    }

    override fun call(method: String, params: String?, callback: KuiklyRenderCallback?): Any? {
        Log.d(TAG, "call: $method $params")
        return when (method) {
            PLAY -> play(params)
            PAUSE -> pauseAnimation()
            RESUME -> resumeAnimation()
            PROGRESS -> updateProgress(params)
            else -> super.call(method, params, callback)
        }
    }

    private fun updateProgress(params: String?) {
        val value = params?.toFloat() ?: return
        cancelAnimation()
        progress = max(0f, min(1f, value))
    }

    private fun play(params: String?) {
        playAnimation()
    }

    private fun setSrc(propValue: Any): Boolean {
        val path = propValue as? String ?: return false
        setAnimation(path)
        return true
    }

    private fun setRepeatCount(propValue: Any): Boolean {
        val loop = propValue as? Boolean ?: return false
        this.loop = loop
        return true
    }

    private fun setAutoPlay(propValue: Any): Boolean {
        val autoPlay = propValue as? Boolean ?: return false
        this.autoPlay = autoPlay
        return true
    }

    private fun setupOnAnimateUpdateListener(): Boolean {
        if (curAnimUpdateListener == null) {
            curAnimUpdateListener = createAnimateUpdateListener()
            addAnimatorUpdateListener(curAnimUpdateListener)
        }
        return true
    }

    private fun setupOnAnimationListener(): Boolean {
        if (curAnimStateListener == null) {
            curAnimStateListener = createAnimationListener()
            addAnimatorListener(curAnimStateListener)
        }
        return true
    }

    companion object {
        private const val TAG = "AndroidLottieView"
        const val COMPONENT_NAME = "KTLottieView"

        const val PLAY = "play"
        const val PAUSE = "pause"
        const val RESUME = "resume"
        const val PROGRESS = "progress"

        const val ON_ANIMATION_UPDATE = "onAnimationUpdate"
        const val ON_ANIMATION_REPEAT = "onAnimationRepeat"
        const val ON_ANIMATION_COMPLETE = "onAnimationComplete"
        const val ON_ANIMATION_LOADED = "onAnimationLoaded"
        const val ON_ANIMATION_LOAD_FAILED = "onAnimationLoadFailed"

        const val SRC = "src"
        const val IMAGE_PATH = "imagePath"
        const val AUTO_PLAY = "autoPlay"
        const val LOOP = "loop"
//        const val PROGRESS = "progress"
    }

}
