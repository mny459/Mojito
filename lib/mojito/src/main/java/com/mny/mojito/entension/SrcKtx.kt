package com.mny.mojito.entension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun Int.inflate(parent: ViewGroup, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(parent.context).inflate(this, parent, attachToRoot)
}