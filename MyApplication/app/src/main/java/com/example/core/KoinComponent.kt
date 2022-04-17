package com.example.core

import com.example.application.BuildConfig
import com.example.application.ext.devInterceptorModule
import com.example.data.Const
import com.example.data.api.MainAPI
import com.example.data.api.base.MainInterceptor
import com.example.data.api.ext.initOkHttpClient
import com.example.data.api.ext.initRetrofit
import com.example.data.local.MainDatabase
import com.example.ui.mainModule
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private val featureModules = listOf(
    mainModule
)

val netModule = module {
    single { Gson() }
    single { initRetrofit(get(named(Const.KEY_BASE_URL)), get(), get()) }
    single {
        initOkHttpClient(
            isDebug = BuildConfig.DEBUG,
            interceptors = get(named(Const.DEFAULT_INTERCEPTORS)),
            debugInterceptor = get(named(Const.DEBUG_INTERCEPTOR))
        )
    }
    single { NetworkUtil(androidApplication()) }
    single(named(Const.KEY_BASE_URL)) { "https://api.coindesk.com/" }
}

val apiModule = module {
    single { get<Retrofit>().create(MainAPI::class.java) }
}

val interceptorModule = module {
    single<Interceptor>(named(Const.MAIN_INTERCEPTOR)) {
        MainInterceptor(get())
    }
    single<Interceptor>(named(Const.INTERCEPTOR_HTTP_LOGGING)) {
        HttpLoggingInterceptor().setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }
    single<Set<@JvmSuppressWildcards Interceptor>>(named(Const.DEFAULT_INTERCEPTORS)) {
        setOf(
            get(named(Const.MAIN_INTERCEPTOR))
        )
    }
}

val dbModule = module {
    single { MainDatabase.init(androidContext()) }
    single { get<MainDatabase>().mainDao() }
}

val appModules = listOf(
    dbModule,
    apiModule,
    devInterceptorModule,
    interceptorModule,
    netModule
) + featureModules