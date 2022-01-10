package com.mny.mojito.entension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * RepeatOnLifecycleKtx
 * Desc:
 */
fun <T> LifecycleOwner.collectOnLifecycle(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: suspend CoroutineScope.(T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            flow.collect { data ->
                action(data)
            }
        }
    }
}