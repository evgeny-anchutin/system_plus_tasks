package com.systemplus.server

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.systemplus.data.ResultData
import com.systemplus.data.TaskData
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


val webService by lazy {
    WebServiceCreator.create()
}

object WebServiceCreator {
    const val SERVER_URL = "http://192.168.43.250/tms/hs/DataExchange/"

    fun create(): WebService {
        val loggingInterceptor = createLoggingInterceptor()
        val authInterceptor = createAuthInterceptor()
        val interceptors = arrayOf(loggingInterceptor, authInterceptor)
        val httpClient = createOkHttpClient(interceptors)

        val retrofit = Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(httpClient.build())
                .build()

        return retrofit.create(WebService::class.java)
    }

    fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun createOkHttpClient(interceptors: Array<Interceptor>): OkHttpClient.Builder {
        val build = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

        interceptors.forEach {
            build.addInterceptor(it)
        }

        return build
    }

    fun createAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()?.newBuilder()
                    ?.build()!!

            val response = chain.proceed(request)
            response
        }
    }
}

interface WebService {

    @GET("GetData")
    fun login(@Header("Authorization") authHeader: String)
            : Deferred<Response<ArrayList<TaskData>>>

    @GET("GetData")
    fun sendTaskIsCompleted(@Header("Authorization") authHeader: String,
                            @Query("document_id") taskId: String)
            : Deferred<Response<ResultData>>
}