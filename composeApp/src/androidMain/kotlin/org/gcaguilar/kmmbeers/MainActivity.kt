package org.gcaguilar.kmmbeers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.android.ext.android.inject
import org.publicvalue.multiplatform.oidc.appsupport.AndroidCodeAuthFlowFactory
import org.publicvalue.multiplatform.oidc.appsupport.CodeAuthFlowFactory

class MainActivity : ComponentActivity() {
    private val codeAuthFlowFactory: CodeAuthFlowFactory by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (codeAuthFlowFactory as AndroidCodeAuthFlowFactory).registerActivity(this)

        setContent {
            //Rinku {
            App()
            // }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}