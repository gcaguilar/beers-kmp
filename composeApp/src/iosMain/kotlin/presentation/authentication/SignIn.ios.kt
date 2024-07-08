package presentation.authentication

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.*
import platform.UIKit.*
import platform.WebKit.*
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
actual fun signIn(url: String) {
    val nsUrl = NSURL(string = url)
    val request = NSMutableURLRequest.requestWithURL(nsUrl).apply {
        setValue("BeersKMM D912C0B80A28A04F4EA2FD48E625853326BEAB1C", forHTTPHeaderField = "User-Agent")
    }

    val viewController = UIViewController().apply {
        val webView = WKWebView(frame = UIScreen.mainScreen.bounds).apply {
            navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
                override fun webView(webView: WKWebView, decidePolicyForNavigationAction: WKNavigationAction, decisionHandler: (WKNavigationActionPolicy) -> Unit) {
                    // Permitir la navegación
                    decisionHandler(WKNavigationActionPolicy.WKNavigationActionPolicyAllow)                }
            }
            loadRequest(request)
        }
        view.addSubview(webView)
    }

    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    rootViewController?.presentViewController(viewController, animated = true, completion = null)
}
