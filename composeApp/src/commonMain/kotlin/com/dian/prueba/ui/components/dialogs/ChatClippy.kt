package com.dian.prueba.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dian.prueba.data.globalResources.LocalColors
import com.dian.prueba.data.globalResources.LocalDimension
import com.dian.prueba.data.globalResources.LocalPadding
import com.dian.prueba.ui.components.BubbleMessage
import com.dian.prueba.utilities.toDisplayFormat
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

//TODO: Luego lo cambio de lugar
data class Message(
    val text: String,
    val isFromMe: Boolean
)

@Composable
fun ChatClippyNormal(
    navController: NavHostController,
) {
    val focusManager = LocalFocusManager.current
    val colorModifier = LocalColors.current
    val paddingModifier = LocalPadding.current
    val dimensionModifier = LocalDimension.current

    var msg by rememberSaveable { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }
    val listState = rememberLazyListState()
    val suggestions = listOf(
        "Ayúdame a encontrar mi bolso ideal",
        "Quiero devolver un bolso",
        "Quiero dejar a Logo alguno de mis bolsos"
    )

    var thereIsText by remember { mutableStateOf(false) }

    //* Auto-scroll al último mensaje
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    Box(
        modifier = Modifier.fillMaxSize()
            .background(colorModifier.backgroundApp)
            .padding(paddingModifier.small)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
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
                IconButton(
                    onClick = {
                        //* Vuelve hacia atrás en la pila de navegación
                        navController.navigateUp()
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(dimensionModifier.iconBig),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = colorModifier.blackLight
                    )
                }
            }

            Spacer(modifier = Modifier.padding(paddingModifier.small))
            //* Fecha y hora actual (formato: 20 April 2026, 10:40)
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = dimensionModifier.iconBig + paddingModifier.tiny + paddingModifier.small)
                    .padding(bottom = paddingModifier.extraTiny),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = now.toDisplayFormat(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Normal
                )
            }

            //* Lista de mensajes
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(paddingModifier.small)
            ) {
                //* Mensaje inicial de Clippy de Logo :D
                item {
                    BubbleMessage(
                        text = "Hola María, estoy aquí para ayudarte a resolver cualquier duda que tengas",
                        isFromMe = false,
                        colorModifier = colorModifier,
                        dimensionModifier = dimensionModifier,
                        paddingModifier = paddingModifier
                    )
                }

                //* Suggestion chips solo si no hay mensajes del usuario
                if (messages.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                        ) {
                            suggestions.forEach { suggestion ->
                                SuggestionChip(
                                    onClick = { messages.add(Message(suggestion, true)) },
                                    label = {
                                        Text(
                                            text = suggestion,
                                            style = MaterialTheme.typography.bodySmall,
                                            fontWeight = FontWeight.Light
                                        )
                                    },
                                    enabled = true
                                )
                            }
                        }
                    }
                }

                //* Mensajes del chat
                items(messages) { message ->
                    BubbleMessage(
                        text = message.text,
                        isFromMe = message.isFromMe,
                        colorModifier = colorModifier,
                        dimensionModifier = dimensionModifier,
                        paddingModifier = paddingModifier
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                CommonTextFieldCustom(
                    value = msg,
                    onValueChange = {
                        msg = it
                        thereIsText = it.isNotBlank()
                    },
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.weight(1f),
                    placeholder = "Escribe un mensaje",
                    trailingIcon = {
                        if (thereIsText) {
                            Icon(
                                modifier = Modifier.size(dimensionModifier.iconTiny)
                                    .clickable {
                                        //* Elimina el contenido del TextField
                                        msg = ""
                                        thereIsText = false
                                        focusManager.clearFocus()
                                    },
                                imageVector = Icons.Default.Close,
                                contentDescription = "Delete message",
                                tint = colorModifier.blackLight
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.padding(horizontal = paddingModifier.tiny))
                if (thereIsText) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        modifier = Modifier.size(dimensionModifier.iconNormal).align(Alignment.CenterVertically)
                            .clickable {
                                if (msg.isNotBlank()) {
                                    messages.add(Message(msg, true))
                                    msg = ""
                                    thereIsText = false
                                    focusManager.clearFocus()
                                }
                            },
                        contentDescription = "Send Message",
                        tint = colorModifier.logoColor
                    )
                } else {
                    IconButton(
                        onClick = {
                            //todo: cuando el usuario mantenga esto presionado la acción será de voice recorder
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardVoice,
                            modifier = Modifier.size(dimensionModifier.iconNormal).align(Alignment.CenterVertically),
                            contentDescription = "Voice Recorder",
                            tint = colorModifier.logoColor
                        )
                    }
                }
            }
        }
    }
}

/* *
 * Lo he dejado aquí porque este componente es uno de los que irá en la librería, como aún no
 * hemos pasado el proyecto al nuevo, prefiero esperar para usar el módulo que he hecho ahí
 * y no crear otro para este proyecto.
 */
@Composable
fun CommonTextFieldCustom(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    keyboardType: KeyboardType,
    placeholder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    val hasLabel = label.isNotEmpty()

    OutlinedTextField(
        enabled = true,
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        label = if (hasLabel) {
            {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else null,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center
            )
        },
        textStyle = TextStyle(
            textAlign = TextAlign.Start
        ),
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
        )
    )
}