package di


import data.AuthenticationServiceImpl
import data.AuthenticatorService
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import org.publicvalue.multiplatform.oidc.ExperimentalOpenIdConnect
import org.publicvalue.multiplatform.oidc.OpenIdConnectClient
import org.publicvalue.multiplatform.oidc.appsupport.AndroidCodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.appsupport.CodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.appsupport.PlatformCodeAuthFlow
import org.publicvalue.multiplatform.oidc.tokenstore.AndroidSettingsTokenStore
import org.publicvalue.multiplatform.oidc.tokenstore.TokenStore
import kotlin.experimental.ExperimentalObjCRefinement

@OptIn(ExperimentalOpenIdConnect::class)
actual val platform: Module = module {
    single<CodeAuthFlowFactory> { AndroidCodeAuthFlowFactory(useWebView = false) }
    factory<TokenStore> { AndroidSettingsTokenStore(androidContext()) }
}
