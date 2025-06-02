package com.dian.prueba.ui.components.dialogs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun showAlertDialogLogin(
    texto: String,
    onDismissRequest: () -> Unit
){
    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = { Text(text = texto) },
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.testTag("loginDialogButton"),
                colors = ButtonDefaults.buttonColors(
                    // Color crema #b7af98
                    backgroundColor = Color(0xFFb7af98),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Text("Cerrar")
            }
        },
        modifier = Modifier.testTag("AlertDialog")
    )

}