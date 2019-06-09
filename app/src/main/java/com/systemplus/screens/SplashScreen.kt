package com.systemplus.screens

import com.e16din.screensadapter.annotation.model.Screen
import com.e16din.screensmodel.BaseScreen
import com.systemplus.app.SystemPlusApp
import com.systemplus.server.RequestError
import com.systemplus.server.RequestManager
import com.systemplus.server.ResponseError

@Screen
class SplashScreen(var nothing: Any?, var parent: Any?) {

    interface UserAgent : BaseScreen.UserAgent {
        fun showMainScreen()
    }

    interface SystemAgent : BaseScreen.SystemAgent {
        fun initViews()
    }

    lateinit var userAgent: UserAgent
    lateinit var systemAgent: SystemAgent
    lateinit var app: SystemPlusApp
    lateinit var server: RequestManager

    private val onLoginRequestError: RequestError = {
        systemAgent.runOnUiThread {
            systemAgent.log("onLoginRequestError!!!!!!!")
        }
    }

    private val onLoginResponseError: ResponseError = { statusCode, message ->
        systemAgent.runOnUiThread {
            systemAgent.log("onLoginResponseError!!!!!!! statusCode:$statusCode | message: $message")
        }
    }

    fun onBind() {
        systemAgent.initViews()
    }

    fun onShow(){
        systemAgent.runOnBackgroundThread {
            val result = server.loginAndLoadTasks(onLoginRequestError, onLoginResponseError)
            systemAgent.log("result: $result")

            app.tasks =  result
            userAgent.showMainScreen()
        }
    }
}