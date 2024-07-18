package org.gcaguilar.kmmbeers.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun Rating() {
    Row {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)  
        ) {
            Text(text = "4.5")
            Text(text = "100 reviews")
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)  
        ) {
            Text(text = "4.5")
            Text(text = "100 reviews")
        }
    }
}
