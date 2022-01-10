package com.mny.mojito.widget.pkg.presentation.launcher

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * LauncherViewService
 * @author caicai
 * Created on 2021-09-08 18:35
 * Desc:
 */
class LauncherViewService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return LauncherViewsFactory(applicationContext, intent)
    }
}