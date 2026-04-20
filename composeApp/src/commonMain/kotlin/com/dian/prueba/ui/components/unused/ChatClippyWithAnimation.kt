package com.dian.prueba.ui.components.unused

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalDimension
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.ui.components.CustomSearchBar
import com.dian.prueba.ui.components.dialogs.CommonTextFieldCustom

@Composable
fun ChatClippyWithAnimation() {
    val colorModifier = LocalColors.current
    val paddingModifier = LocalPadding.current
    val dimensionModifier = LocalDimension.current
    val isFromMe by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorModifier.backgroundApp)
            .padding(paddingModifier.small)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.padding(paddingModifier.small))

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Clippy Logo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )

                Icon(
                    modifier = Modifier.size(dimensionModifier.iconBig),
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = colorModifier.blackLight
                )
            }
            //* Fecha y hora actual
            Spacer(modifier = Modifier.padding(paddingModifier.small))

            //* Reutilizando el textfield animado (no row)
            CustomSearchBar(
                query = "",
                onQueryChange = {},
                placeholder = "Hola María, estoy aquí para ayudarte a lucir tu bolsazo ideal",
                modifier = Modifier.fillMaxWidth(),
            )
            //* Posibles peticiones que el usuario podría elegir
            Spacer(modifier = Modifier.padding(paddingModifier.small))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                SuggestionChip(
                    onClick = {
                        // TODO: Enviar la petición
                    },
                    label = {
                        Text(
                            text = "Ayúdame a encontrar mi bolso ideal",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Light,
                        )
                    },
                    enabled = true
                )
                SuggestionChip(
                    onClick = {
                        // TODO: Enviar la petición
                    },
                    label = {
                        Text(
                            text = "Quiero devolver un bolso",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Light,
                        )
                    },
                    enabled = true
                )
                SuggestionChip(
                    onClick = {
                        // TODO: Enviar la petición
                    },
                    label = {
                        Text(
                            text = "Quiero dejar a Logo alguno de mis bolsos",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Light,

                            )
                    },
                    enabled = true
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                //* Textfield para escribir el mensaje
                CommonTextFieldCustom(
                    value = "",
                    onValueChange = {},
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.weight(1f),
                    placeholder = "Escribe un mensaje",
                )
                Spacer(
                    modifier = Modifier.padding(horizontal = paddingModifier.tiny)
                )
                //* Para grabar audio de voz y que se escriba en el chat
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxHeight().aspectRatio(1f),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colorModifier.containerColor,
                        contentColor = colorModifier.logoColorLight
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardVoice,
                        modifier = Modifier.size(dimensionModifier.iconNormal),
                        contentDescription = "Voice Recorder"
                    )
                }
            }
        }
    }
}