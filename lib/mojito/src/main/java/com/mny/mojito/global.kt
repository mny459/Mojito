package com.mny.mojito

import android.os.Handler
import android.os.Looper

val mainHandler by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Handler(Looper.getMainLooper()) }