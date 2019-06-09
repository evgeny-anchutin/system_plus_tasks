package com.systemplus.app

import com.e16din.screensadapter.annotation.model.App
import com.e16din.screensmodel.BaseApp
import com.systemplus.data.TaskData

@App
interface SystemPlusApp : BaseApp {

    var tasks: ArrayList<TaskData>
}
