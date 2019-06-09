package com.systemplus.helpers.screenstub

import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.annotation.BindScreen
import com.e16din.screensadapter.binders.android.ScreenBinder
import com.e16din.screensadapter.settings.ScreenSettings
import com.systemplus.app.SystemPlusApp
import com.systemplus.R

@BindScreen(screen = StubScreen::class)
class StubBinder(adapter: ScreensAdapter<*, *>) : ScreenBinder<StubScreen>(adapter),
        StubScreen.UserAgent,
        StubScreen.SystemAgent {

    companion object {
        const val FRAGMENT_TAG = "StubBinder"

        fun createScreenSettings() = ScreenSettings(
                screenCls = StubScreen::class.java,
                layoutId = R.layout.screen_stub,
                themeId = R.style.AppTheme_NoActionBar
        )
    }

    override fun onBind() {
        screen.userAgent = this
        screen.systemAgent = this
        screen.app = screensAdapter.getApp() as SystemPlusApp

        screen.onBind()
    }

    override fun initViews() {
    }
}