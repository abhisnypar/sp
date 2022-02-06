package com.spidugu.nycshools.di

import androidx.lifecycle.MutableLiveData
import com.spidugu.nycshools.BuildConfig
import com.spidugu.nycshools.api.ChaseApi
import com.spidugu.nycshools.repository.ChaseRepositoryImpl
import com.spidugu.nycshools.repository.IChaseRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val READ_TIMEOUT: Long = 60
private const val CONNECT_TIMEOUT: Long = 120

//main module for application.

val appModule = module {

    single<MutableLiveData<String?>> { MutableLiveData() }

    single { createOkHttpClient() }

    single { createWebService<ChaseApi>(get(), BuildConfig.API_CHASE_BASE_URL) }

    single<IChaseRepository> { ChaseRepositoryImpl(get(), dispatcher = Dispatchers.IO) }
}

//OkHttp client builder class for print request, response and timeout in
//case of latency issues.
fun createOkHttpClient(): OkHttpClient {
    val dispatcher = Dispatcher()
    dispatcher.maxRequests = 1

    val logging = HttpLoggingInterceptor()
    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    val httpClient = OkHttpClient.Builder()
    httpClient.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    httpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    httpClient.addInterceptor(logging)
    httpClient.dispatcher(dispatcher)
    return httpClient.build()
}

// Retrofit instance.
inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}