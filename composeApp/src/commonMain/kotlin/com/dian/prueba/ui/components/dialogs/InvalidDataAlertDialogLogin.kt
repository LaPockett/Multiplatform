package com.dian.prueba.ui.components.dialogs

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.dian.prueba.ui.components.buttons.CustomDialogButton

@Composable
fun InvalidDataAlertDialogLogin(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Datos inválidos") },
        text = { Text("Por favor, ingresa un email válido y/o una contraseña válida. " +
                "El email debe de ser un correo válido y la contraseña debe de tener entre 6 y 20 caracteres") },
        confirmButton = {
            CustomDialogButton(
                onClick = onDismissRequest,
                text = "OK",
            )
        }
    )
}