package com.dian.prueba.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.dian.prueba.ui.components.buttons.CustomDialogButton

@Composable
fun ShowAlertDialogLogin(
    title: String,
    texto: String,
    onDismissRequest: () -> Unit
){
    AlertDialog(
        title = { Text(text = title) },
        onDismissRequest = onDismissRequest,
        text = { Text(text = texto) },
        confirmButton = {
            CustomDialogButton(
                onClick = onDismissRequest,
                text = "Cerrar"
            )
        },
        modifier = Modifier.testTag("AlertDialog")
    )

}