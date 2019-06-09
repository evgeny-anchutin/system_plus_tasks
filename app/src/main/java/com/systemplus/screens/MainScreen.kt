package com.systemplus.screens

import com.e16din.screensadapter.annotation.model.Screen
import com.e16din.screensmodel.BaseScreen
import com.systemplus.app.SystemPlusApp
import com.systemplus.data.TaskData
import com.systemplus.server.RequestError
import com.systemplus.server.RequestManager
import com.systemplus.server.ResponseError

@Screen
class MainScreen(var nothing: Any?, var parent: Any?) {

    interface UserAgent : BaseScreen.UserAgent {
        fun showTasks(tasks: MutableList<TaskData>)
        fun showTaskDoneImage(position: Int)
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
            systemAgent.log("onLoginRequestError2!!!!!!!")
        }
    }

    private val onLoginResponseError: ResponseError = { statusCode, message ->
        systemAgent.runOnUiThread {
            systemAgent.log("onLoginResponseError2!!!!!!! statusCode:$statusCode | message: $message")
        }
    }

    fun onBind() {
        systemAgent.initViews()

        userAgent.showTasks(app.tasks)
    }

    fun onDoneClick(position: Int) {
        val taskId = app.tasks[position].document_id
        systemAgent.runOnBackgroundThread {
            val result = server.sendTaskIsCompleted(taskId, onLoginRequestError, onLoginResponseError)
            systemAgent.log(result.result!!)
            systemAgent.runOnUiThread {
                app.tasks[position].isDone = true
                userAgent.showTaskDoneImage(position)
            }
        }
    }
}