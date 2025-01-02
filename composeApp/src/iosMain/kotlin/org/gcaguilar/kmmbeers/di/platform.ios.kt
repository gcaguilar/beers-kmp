package org.gcaguilar.kmmbeers.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.appsupport.CodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.appsupport.PlatformCodeAuthFlow
import org.publicvalue.multiplatform.oidc.tokenstore.IosKeychainTokenStore
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore
import kotlin.experimental.ExperimentalObjCRefinement
import io.ktor.client.engine.darwin.Darwin

@OptIn(ExperimentalOpenIdConnect::class)
actual val platform: Module = module {
    factory<CodeAuthFlowFactory> { IosCodeAuthFlowFactory() }
    factory<TokenStore> { IosKeychainTokenStore() }

    single {
        createHttpClient(Darwin)
    }
}


@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
@Suppress("unused")
class IosCodeAuthFlowFactory(
    private val ephemeralBrowserSession: Boolean = false
) : CodeAuthFlowFactory {
    override fun createAuthFlow(client: OpenIdConnectClient): PlatformCodeAuthFlow {
        return PlatformCodeAuthFlow(client, ephemeralBrowserSession)
    }
}