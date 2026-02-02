package com.dian.prueba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Composables de prueba/práctica
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheetSample() {
    val state = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    //val scope = rememberCoroutineScope()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                isSheetOpen = true
            }
        ){
            Text(
                text = "Expand the bottom sheet"
            )
        }
    }
    if (isSheetOpen){
        ModalBottomSheet(
            onDismissRequest = {
                isSheetOpen = false
            },
            sheetState = state,
        ) {
            LazyColumn {
                items(2) {
                    ListItem(
                        text = { Text("Bag $it") },
                        icon = {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Localized description"
                            )
                        }
                    )
                }
            }
        }
    }
}

//@Preview
/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenWithBottomSheetSample(
    isSheetOpen: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                isSheetOpen = true
            }
        ){
            Text(
                text = "Expand the bottom sheet"
            )
        }
    }
}*/