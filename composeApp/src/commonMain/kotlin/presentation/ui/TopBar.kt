package presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    navigationIcon: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = { Text("Untappd") },
        navigationIcon = navigationIcon
    )
}

@Composable
fun BackNavigationIcon(
    onClickIconButton: () -> Unit
) {
    IconButton(
        onClick = onClickIconButton
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null
        )
    }
}
