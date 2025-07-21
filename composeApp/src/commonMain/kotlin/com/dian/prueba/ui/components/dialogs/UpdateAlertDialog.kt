package com.dian.prueba.ui.components.dialogs

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dian.prueba.PlatformType
import com.dian.prueba.getPlatformType
import com.dian.prueba.ui.components.buttons.CustomDialogButton
import com.dian.prueba.utilities.Logger
import com.dian.prueba.viewModel.UpdateVM
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun UpdateAlertDialog(viewModel: UpdateVM = viewModel()) {
    val logger = Logger("UpdateAlertDialog")
    val uriHandler = LocalUriHandler.current
    val updateInfo = viewModel.updateInfo.collectAsState().value

    if (updateInfo == null || !viewModel.showUpdateDialog.collectAsState().value) return
    AlertDialog(
        onDismissRequest = {
            if (!updateInfo.mustUpdate) {
                viewModel.showUpdateDialog.value = false
                logger.debug(viewModel.showUpdateDialog.value.toString())
            }
        },
        title = { Text(text = "Nueva actualización disponible", modifier = Modifier.testTag("updateTitle")
        ) },
        text = { Text(text = "Versión actual: ${updateInfo.currentVersion} Hay una nueva versión disponible de la aplicación: ${updateInfo.newVersion}. Es obligatorio actualizar") },
        confirmButton = {
            CustomDialogButton(
                onClick = {
                /**
                 * Redirect to App Store or Google Play Store depending on the platform
                 */
                if (getPlatformType() == PlatformType.ANDROID){
                    uriHandler.openUri("https://play.google.com/store/apps/details?id=com.amazon.mShop.android.shopping&hl=es&pli=1")
                } else {
                    uriHandler.openUri("https://apps.apple.com/es/app/amazon-compras/id335187483")
                }
                viewModel.doUpdate()
                //viewModel.showUpdateDialog.value = false
            },
                text = "Actualizar",
            )
        },

        dismissButton = {
            if (!updateInfo.mustUpdate) {
                CustomDialogButton(onClick = {
                    /**
                     * Aquí solo cerramos el diálogo sin actualizar la versión a 1.3
                     */
                    logger.debug("currentVersion: " + updateInfo.currentVersion)
                    logger.debug("newVersion: " +updateInfo.newVersion)
                    viewModel.showUpdateDialog.value = false
                },
                    text = "Ignorar"
                )
            }
        }
    )
}