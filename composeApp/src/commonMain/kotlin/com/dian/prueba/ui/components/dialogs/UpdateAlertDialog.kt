package com.dian.prueba.ui.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dian.prueba.model.UpdateInfo
import com.dian.prueba.network.APIClient
import com.dian.prueba.utilities.Logger
import com.dian.prueba.utilities.UpdateStorage
import com.dian.prueba.viewModel.LoginVM
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun UpdateAlertDialog(viewModel: LoginVM = viewModel()) {
    val logger = Logger()
    val apiClient = APIClient()
    val uriHandler = LocalUriHandler.current

    AlertDialog(
        onDismissRequest = {
            logger.debug(viewModel.mustUpdate.value.toString(), "UpdateAlertDialog-mustUpdate")
            if (viewModel.mustUpdate.value) {
                viewModel.showUpdateDialog.value = false
                logger.debug(viewModel.showUpdateDialog.value.toString(), "UpdateAlertDialog-showUpdateDialog")
            }
        },
        title = { Text(text = "Nueva actualización disponible") },
        text = { Text(text = "Hay una nueva versión disponible de la aplicación. ¿Deseas actualizarla?") },
        confirmButton = {
            Button(onClick = {
                UpdateStorage.saveUpdateAvailable(
                    UpdateInfo(
                        updateAvailable = false,
                        mustUpdate = viewModel.mustUpdate.value,
                        needsUpdate = false,
                        currentVersion = viewModel.newVersion.value,
                        newVersion = viewModel.newVersion.value,
                    )
                )
                UpdateStorage.updateToNewVersion(viewModel.newVersion.value).let { updatedInfo ->
                    viewModel.currentVersion.value = updatedInfo.currentVersion
                    viewModel.newVersion.value = updatedInfo.newVersion
                }
                viewModel.updateApp()
                logger.debug(viewModel.mustUpdate.value.toString(), "UpdateAlertDialog-mustUpdate")
                viewModel.currentVersion.value = viewModel.newVersion.value

                uriHandler.openUri("https://play.google.com/store/apps/details?id=com.amazon.mShop.android.shopping&hl=es&pli=1")

                viewModel.showUpdateDialog.value = false
            }) {
                Text("Actualizar")
            }
        },

        dismissButton = {
            if (viewModel.mustUpdate.value) {
                Button(onClick = {
                    /**
                     * Aquí solo cerramos el diálogo sin cambiar la versión a 1.3
                     */
                    logger.debug(viewModel.mustUpdate.value.toString(), "UpdateAlertDialog-mustUpdate")
                    logger.debug(viewModel.currentVersion.value, "UpdateAlertDialog-currentVersion")
                    logger.debug(viewModel.newVersion.value, "UpdateAlertDialog-newVersion")
                    viewModel.showUpdateDialog.value = false
                }) {
                    Text("Ignorar")
                }
            }
        }
    )
}