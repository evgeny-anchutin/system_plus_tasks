package com.systemplus.helpers.screenstub

import com.e16din.screensadapter.annotation.model.Screen
import com.e16din.screensmodel.BaseScreen
import com.systemplus.app.SystemPlusApp

@Screen
class StubScreen(var nothing: Any?, var parent: Any?) {

    interface UserAgent : BaseScreen.UserAgent {

    }

    interface SystemAgent : BaseScreen.SystemAgent {
        fun initViews()
    }

    lateinit var userAgent: UserAgent
    lateinit var systemAgent: SystemAgent
    lateinit var app: SystemPlusApp

    fun onBind() {
        systemAgent.initViews()
    }
}