package com.kuikly.kuiklylottie

import com.tencent.kuikly.core.base.Attr
import com.tencent.kuikly.core.base.DeclarativeBaseView
import com.tencent.kuikly.core.base.ViewContainer
import com.tencent.kuikly.core.base.event.Event
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

@Suppress("detekt:FunctionNaming")
fun ViewContainer<*, *>.LottieView(init: LottieView.() -> Unit) {
    addChild(LottieView(), init)
}

class LottieView : DeclarativeBaseView<LottieAttr, LottieEvent>() {
    override fun createAttr(): LottieAttr = LottieAttr()

    override fun createEvent(): LottieEvent = LottieEvent()

    override fun viewName(): String = "KTLottieView"

    fun play() {
        performTaskWhenRenderViewDidLoad {
            renderView?.callMethod(PLAY, null)
        }
    }

    fun pause() {
        performTaskWhenRenderViewDidLoad {
            renderView?.callMethod(PAUSE, null)
        }
    }

    fun resume() {
        performTaskWhenRenderViewDidLoad {
            renderView?.callMethod(RESUME, null)
        }
    }

    fun progress(value: Float) {
        performTaskWhenRenderViewDidLoad {
            renderView?.callMethod(PROGRESS, params = value.toString())
        }
    }

    companion object {
        const val PLAY = "play"
        const val PAUSE = "pause"
        const val RESUME = "resume"
        const val PROGRESS = "progress"
    }
}

/**
 * 属性只控制资源首次加载完成后的行为
 */
class LottieAttr : Attr() {

    /**
     * 资源assets路径
     */
    fun src(value: String): LottieAttr {
        SRC with value
        return this
    }

    /**
     * 图片资源路径
     */
    fun imagePath(value: String): LottieAttr {
        IMAGE_PATH with value
        return this
    }

    /**
     * 资源加载完成后，是否自动播放
     */
    fun autoPlay(value: Boolean): LottieAttr {
        AUTO_PLAY with value
        return this
    }

    /**
     * 资源加载完成后，播放的循环状态
     */
    fun loop(value: Boolean): LottieAttr {
        LOOP with value
        return this
    }

    companion object {
        const val SRC = "src"
        const val IMAGE_PATH = "imagePath"
        const val AUTO_PLAY = "autoPlay"
        const val LOOP = "loop"
        const val PROGRESS = "progress"
    }
}

class LottieEvent : Event() {
    fun onAnimationUpdate(block: (Float) -> Unit) {
        register(ON_ANIMATION_UPDATE) {
            val param = it as? JSONObject ?: return@register
            block(param.optDouble("value", 0.0).toFloat())
        }
    }

    fun onAnimationRepeat(block: () -> Unit) {
        register(ON_ANIMATION_REPEAT) {
            block()
        }
    }

    fun onAnimationComplete(block: () -> Unit) {
        register(ON_ANIMATION_COMPLETE) {
            block()
        }
    }

    fun onAnimationLoaded(block: () -> Unit) {
        register(ON_ANIMATION_LOADED) {
            block()
        }
    }

    fun onAnimationLoadFailed(block: () -> Unit) {
        register(ON_ANIMATION_LOAD_FAILED) {
            block()
        }
    }

    companion object {
        const val ON_ANIMATION_UPDATE = "onAnimationUpdate"
        const val ON_ANIMATION_REPEAT = "onAnimationRepeat"
        const val ON_ANIMATION_COMPLETE = "onAnimationComplete"
        const val ON_ANIMATION_LOADED = "onAnimationLoaded"
        const val ON_ANIMATION_LOAD_FAILED = "onAnimationLoadFailed"
    }
}
