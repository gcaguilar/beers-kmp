package org.gcaguilar.kmmbeers.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import coil3.compose.AsyncImage

@Composable
fun InfoSection(
    imageUrl: String,
    name: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Logger.d(
            tag = "TAG",
            throwable = null,
            messageString = imageUrl
        )
        AsyncImage(
            model = imageUrl,
            contentDescription = "",
            modifier = Modifier.fillMaxWidth()
                .height(100.dp),
            contentScale = ContentScale.Fit,
            onError = {
                Logger.d(tag = "TAG", throwable = null, messageString = "ERROR")
            }
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
