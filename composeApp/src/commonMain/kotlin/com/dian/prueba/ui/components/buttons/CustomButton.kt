package com.dian.prueba.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

val containerColor = Color(0xFF626D8B)
val contentColor = Color.White
val shape = RoundedCornerShape(10.dp)
@Composable
fun CustomDialogButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
) {
    Button(
        modifier= Modifier.testTag("loginDialogButton"),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape
    ){
        Text(text)
    }
}
@Composable
fun CustomButtonWithIcon(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    imageVector: ImageVector,
    contentDescription: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape,
        enabled = enabled,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = Modifier.padding(end=6.dp)
        )
        Text(text)
    }
}