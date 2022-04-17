package com.example.application.ext

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.data.Const
import okhttp3.Interceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
 
val devInterceptorModule = module {
    single { ChuckerCollector(androidContext()) }

    single<Interceptor>(named(Const.INTERCEPTOR_CHUCK)) {
        ChuckerInterceptor(androidContext(), get())
    }

    single<Set<@JvmSuppressWildcards Interceptor>>(named(Const.DEBUG_INTERCEPTOR)) {
        setOf(
            get(named(Const.INTERCEPTOR_CHUCK)),
            get(named(Const.INTERCEPTOR_HTTP_LOGGING))
        )
    }
}
