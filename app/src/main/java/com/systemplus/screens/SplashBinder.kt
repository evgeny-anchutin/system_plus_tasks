package com.systemplus.screens

import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.annotation.BindScreen
import com.e16din.screensadapter.binders.android.ScreenBinder
import com.e16din.screensadapter.settings.ScreenSettings
import com.systemplus.R
import com.systemplus.app.SystemPlusApp
import com.systemplus.server.RequestManager

@BindScreen(screen = SplashScreen::class)
class SplashBinder(adapter: ScreensAdapter<*, *>) : ScreenBinder<SplashScreen>(adapter),
        SplashScreen.UserAgent,
        SplashScreen.SystemAgent {

    companion object {
        fun createScreenSettings() = ScreenSettings(
                screenCls = SplashScreen::class.java,
                layoutId = R.layout.screen_splash,
                finishOnNextScreen = true,
                themeId = R.style.AppTheme_NoActionBar
        )
    }

    override fun onBind() {
        screen.userAgent = this
        screen.systemAgent = this
        screen.app = screensAdapter.getApp() as SystemPlusApp
        screen.server = RequestManager

        screen.onBind()
    }

    override fun onShow() {
        screen.onShow()
    }

    override fun initViews() {
        // do nothing
    }

    override fun showMainScreen() {
        val screenSettings = MainBinder.createScreenSettings()
        screensAdapter.showNextScreen(screenSettings)
    }
}