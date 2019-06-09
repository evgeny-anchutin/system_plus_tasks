package com.systemplus.server

import com.e16din.screensadapter.annotation.model.Server
import com.systemplus.data.ResultData
import com.systemplus.data.TaskData

@Server
interface Server {
    suspend fun loginAndLoadTasks(onRequestError: RequestError,
                                  onResponseError: ResponseError)
            : ArrayList<TaskData>

    suspend fun sendTaskIsCompleted(taskId: String?, onRequestError: RequestError,
                                    onResponseError: ResponseError)
            : ResultData
}