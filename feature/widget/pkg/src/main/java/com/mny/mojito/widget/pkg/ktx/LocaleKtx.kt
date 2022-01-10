package com.mny.mojito.widget.pkg.ktx

import android.os.Build
import java.util.*

/**
 * LocaleKtx
 * Desc:
 */
fun curLocale(): Locale {
    val configuration = android.content.res.Resources.getSystem().configuration
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.locales.get(0)
    } else {
        configuration.locale
    }
}