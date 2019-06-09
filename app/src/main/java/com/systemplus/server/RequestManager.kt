package com.systemplus.server

import android.util.Base64
import android.util.Log
import com.systemplus.data.ResultData
import com.systemplus.data.TaskData
import kotlinx.coroutines.Deferred
import retrofit2.Response


typealias RequestError = (throwable: Throwable) -> Unit
typealias ResponseError = (statusCode: Int, message: String) -> Unit


object RequestManager : Server {

    override suspend fun loginAndLoadTasks(onRequestError: RequestError,
                                           onResponseError: ResponseError): ArrayList<TaskData> {
        val name = "petrov"
        val password = "123123"

        val authString = "$name:$password"

        val authEncBytes = Base64.encode(authString.toByteArray(), Base64.NO_WRAP)
        val authStringEnc = String(authEncBytes)

        val resultData = call(webService.login("Basic $authStringEnc"),
                onRequestError,
                onResponseError)

        return resultData!!
    }

    override suspend fun sendTaskIsCompleted(taskId: String?, onRequestError: RequestError, onResponseError: ResponseError): ResultData {
        val name = "petrov"
        val password = "123123"

        val authString = "$name:$password"

        val authEncBytes = Base64.encode(authString.toByteArray(), Base64.NO_WRAP)
        val authStringEnc = String(authEncBytes)

        val resultData = call(webService.sendTaskIsCompleted("Basic $authStringEnc", taskId!!),
                onRequestError,
                onResponseError)

        return resultData!!
    }

    private suspend fun <T> call(request: Deferred<Response<T>>,
                                 onRequestError: RequestError,
                                 onResponseError: ResponseError): T? {

        try {
            val result = request.await()
            if (result.isSuccessful) {
                result.body()?.let { body ->
                    return body
                }

            } else {
                onResponseError.invoke(result.code(), result.message())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("debug", "error.RequestManager: ${e.message}")
            onRequestError.invoke(e)
        }

        return null
    }
}