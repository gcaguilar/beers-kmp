package org.gcaguilar.kmmbeers.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun BeerItem(
    image: String,
    name: String,
    style: String,
    abv: String,
    ibu: String,
    showRate: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            KamelImage(
                { asyncPainterResource(image) }, contentDescription = "data.Beer image",
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    .height(100.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = style,
                style = MaterialTheme.typography.bodySmall
            )
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "$abv - $ibu IBU",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            if (showRate) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.Start)
                ) {
                    Text(text = "Rate")
                }
            }
        }
    }
}