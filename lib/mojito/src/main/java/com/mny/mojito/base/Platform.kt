package com.mny.mojito.base

object Platform {
    val DEPENDENCY_GLIDE: Boolean
    val DEPENDENCY_LIVE_DATA_BUS: Boolean
    val DEPENDENCY_EVENTBUS: Boolean

    init {
        DEPENDENCY_GLIDE = findClassByClassName("com.bumptech.glide.Glide")
        DEPENDENCY_LIVE_DATA_BUS = findClassByClassName("org.simple.eventbus.EventBus")
        DEPENDENCY_EVENTBUS = findClassByClassName("org.greenrobot.eventbus.EventBus")
    }

    private fun findClassByClassName(className: String) = try {
        Class.forName(className)
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}