package com.dian.prueba.ui.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun showAlertDialogLogin(
    texto: String,
    onDismissRequest: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text(text = texto) },
        confirmButton = {
            Button(onClick = onDismissRequest, modifier = Modifier.testTag("loginDialogButton")) {
                Text("Cerrar")
            }
        },
        modifier = Modifier.testTag("AlertDialog")
    )

}