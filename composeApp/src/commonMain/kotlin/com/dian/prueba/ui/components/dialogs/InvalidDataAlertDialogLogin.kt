package com.dian.prueba.ui.components.dialogs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*

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
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    // Color crema #b7af98
                    backgroundColor = Color(0xFF626D8B),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("OK")
            }
        }
    )
}