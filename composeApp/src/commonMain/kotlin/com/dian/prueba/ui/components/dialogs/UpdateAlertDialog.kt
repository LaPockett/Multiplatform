package com.dian.prueba.ui.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dian.prueba.model.UpdateInfoUser
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.UpdateStorage
import com.dian.prueba.viewModel.LoginVM
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun UpdateAlertDialog(
    viewModel: LoginVM = viewModel()
){
    val logger  = Logger()
    val uriHandler = LocalUriHandler.current
    AlertDialog(
        onDismissRequest = {
            if (!viewModel.mustUpdate.value){
                viewModel.showUpdateDialog.value = false
            }
        },
        title = { Text(text = "Nueva actualización disponible") },
        text = { Text(text = "Hay una nueva versión disponible de la aplicación. ¿Deseas actualizarla?") },

        confirmButton = {
            Button(onClick = {
                UpdateStorage.setUpdateDone(UpdateInfoUser(true))
                uriHandler.openUri("https://play.google.com/store/apps/details?id=com.amazon.mShop.android.shopping&hl=es&pli=1")
                viewModel.showUpdateDialog.value = false
            }) {
                Text("Actualizar")
            }
        },
        dismissButton = {
            logger.debug("El valor de mustUpdate es: ${viewModel.mustUpdate.value}", "UpdateAlertDialog")
            if (!viewModel.mustUpdate.value){
                Button(onClick = {
                    viewModel.showUpdateDialog.value = false
                }) {
                    Text("Ignorar")
                }
            }

        }

    )
}
