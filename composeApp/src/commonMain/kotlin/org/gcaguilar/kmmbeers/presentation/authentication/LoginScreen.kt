package org.gcaguilar.kmmbeers.presentation.authentication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import com.mmk.kmpauth.uihelper.google.GoogleSignInButtonIconOnly
//import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
//import com.mmk.kmpauth.google.GoogleAuthCredentials
//import com.mmk.kmpauth.google.GoogleAuthProvider
//import com.mmk.kmpauth.uihelper.google.GoogleSignInButtonIconOnly
import org.koin.compose.viewmodel.koinViewModel

private const val FetchAuthenticationUrlKey = "FetchUri"

@Composable
fun LoginScreen() {
    val screenModel = koinViewModel<LoginScreenModel>()
    val state by screenModel.state.collectAsState()
    GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = "550364172787-1he86uhtnthdhron339d94m7tb38om9d.apps.googleusercontent.com"))

    LaunchedEffect(Unit) {
        screenModel.checkStatus()
    }

//    screenModel.state.value.events?.let { it ->
//        when (it) {
//            AuthenticationEvents.LoggedIn -> {
//                //navigator.replace(SearchScreen)
//                screenModel.processNavigation()
//            }
//
//            else -> {
    GoogleButtonUiContainerFirebase(onResult = { authResult ->
        authResult.fold({

        }, {

        })
    }, linkAccount = true) {
        GoogleSignInButtonIconOnly(onClick = { this.onClick() })
    }
}
//        }
//    }
//}