package com.dian.prueba.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.ui.screens.splash.SmoothAppear
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

/* *
 * Cuando el usuario utilice el slide para reservar el bolso saldrá
 * este componente de confirmación de reserva para que verifique sus datos
 * o los modifique en caso de ser necesario.
 */
@Preview
@Composable
fun ConfirmOrderDialog() {
    val colorModifier = LocalColors.current
    val paddingModifier = LocalPadding.current
    var showProductData by remember { mutableStateOf(false) }
    var showAddress by remember { mutableStateOf(false) }
    var orderConfirmed by remember { mutableStateOf(false) }
    var orderIcons by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(2000)
        showProductData = true
        delay(1000)
        showAddress = true
        delay(1000)
        orderConfirmed = true
        delay(1000)
        orderIcons = true
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorModifier.backgroundApp)
            .padding(horizontal = paddingModifier.small),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Confirming order...",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.padding(vertical = paddingModifier.tiny))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                VerticalDivider(
                    color = colorModifier.logoColor,
                    modifier = Modifier.fillMaxHeight().width(1.dp)
                )
                Spacer(modifier = Modifier.padding(horizontal = paddingModifier.extraTiny))
                SmoothAppear(visible = showProductData) {

                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = paddingModifier.extraTiny)
                    ) {
                        Text(
                            text = "Bottega Veneta",
                            modifier = Modifier.padding(bottom = paddingModifier.extraTiny),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )
                        Text(
                            text = "Foulard",
                            modifier = Modifier.padding(bottom = paddingModifier.tiny),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Justify
                        )

                        SmoothAppear(visible = showAddress) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "To Calle Hermosilla 20",
                                    modifier = Modifier.padding(bottom = paddingModifier.extraTiny),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify
                                )
                                Text(
                                    text = "Next Saturday",
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = paddingModifier.tiny))

            }
            Spacer(modifier = Modifier.padding(vertical = paddingModifier.tiny))
            SmoothAppear(visible = orderConfirmed) {

                Text(
                    text = "Order confirmed.",
                    modifier = Modifier.padding(bottom = paddingModifier.extraTiny),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            SmoothAppear(visible = orderIcons) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = colorModifier.logoColor,
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Pencil"
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = paddingModifier.extraExtraLarge))
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = colorModifier.logoColor,
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Check"
                        )
                    }
                }
            }

        }
    }
}