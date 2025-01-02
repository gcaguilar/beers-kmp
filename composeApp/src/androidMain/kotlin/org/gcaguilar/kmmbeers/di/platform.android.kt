package org.gcaguilar.kmmbeers.di

import io.ktor.client.engine.okhttp.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.appsupport.AndroidCodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.appsupport.CodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.tokenstore.AndroidSettingsTokenStore
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore

@OptIn(ExperimentalOpenIdConnect::class)
actual val platform: Module = module {
    single<CodeAuthFlowFactory> { AndroidCodeAuthFlowFactory(useWebView = false) }
    factory<TokenStore> { AndroidSettingsTokenStore(androidContext()) }
    single {
        createHttpClient(OkHttp)
    }
}
