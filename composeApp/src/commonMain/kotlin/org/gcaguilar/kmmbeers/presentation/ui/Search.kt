package org.gcaguilar.kmmbeers.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.KeyEventType.Companion.KeyUp
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction

/*
* Based on
* https://github.com/android/compose-samples/blob/b7adb98ebe36b17c23bfb7d27a9fa93d7416e47e/JetNews/app/src/main/java/com/example/jetnews/ui/home/HomeScreens.kt#L597
*/

@Composable
fun Search(
    searchInput: String,
    placeHolder: String,
    onSearchInputChanged: (String) -> Unit,
    onClearTapped: () -> Unit,
    submitSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchInput,
        onValueChange = onSearchInputChanged,
        placeholder = { Text(placeHolder) },
        leadingIcon = {
            IconButton(
                onClick = submitSearch
            ) {
                Icon(Icons.Filled.Search, null)
            }
        },
        trailingIcon = {
            if (searchInput.isNotBlank()) {
                IconButton(
                    onClick = onClearTapped
                ) {
                    Icon(Icons.Filled.Clear, null)
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .interceptKey(Key.Enter) {
                submitSearch()
                keyboardController?.hide()
                focusManager.clearFocus(force = true)
            },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                submitSearch()
                keyboardController?.hide()
            }
        )
    )
}

fun Modifier.interceptKey(key: Key, onKeyEvent: () -> Unit): Modifier {
    return this.onPreviewKeyEvent {
        if (it.key == key && it.type == KeyUp) {
            onKeyEvent()
            true
        } else it.key == key
    }
}
