package com.systemplus.app

import androidx.multidex.MultiDexApplication
import com.e16din.screensadapter.GeneratedScreensAdapter
import com.e16din.screensadapter.ScreensAdapter
import com.e16din.screensadapter.ScreensAdapterApplication
import com.systemplus.data.TaskData
import com.systemplus.screens.SplashBinder
import com.systemplus.server.RequestManager


class AndroidApp : MultiDexApplication(),
        ScreensAdapterApplication,
        SystemPlusApp {

    object DATA {
        const val KEY_STUB = "KEY_STUB"
    }

    override val screensAdapter: ScreensAdapter<*, *> by lazy {
        GeneratedScreensAdapter(this, this, RequestManager, 100L)
    }

    override var tasks: ArrayList<TaskData> = arrayListOf()

    override fun onCreate() {
        super.onCreate()
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not initAnswersListener your app in this process.
//            return
//        }
//        LeakCanary.install(this)

        val screenSettings = SplashBinder.createScreenSettings()
        screensAdapter.setFirstScreen(screenSettings)
        screensAdapter.start()
    }

    override fun onHideAllScreens(screensCount: Int) {
        // do nothing
    }

    override fun onStart(launchNumber: Int) {
        // do nothing
    }
}