package presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

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
        KamelImage(
            modifier = Modifier.fillMaxWidth()
                .height(100.dp),
            resource = asyncPainterResource(imageUrl),
            contentDescription = "",
            contentScale = ContentScale.Fit
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