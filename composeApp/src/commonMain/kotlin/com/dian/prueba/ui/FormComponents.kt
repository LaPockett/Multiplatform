package com.dian.prueba.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

val focusedBorderColor = Color(0xFFf7f4f0)
val textFieldShape = RoundedCornerShape(15.dp)
val unfocusedBorderColor = Color(0xFF626D8B)
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    CommonTextField(
        value = value,
        onValueChange = onValueChange,
        label = "Email",
        /**
         * It's recommended to specify the keyboard type:
         * - In this case it shows you the emails that exist on your device (apparently only in Android)
         */
        keyboardType = KeyboardType.Email,
        modifier = modifier
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Password",
    isPasswordVisible: Boolean = false,
    modifier: Modifier = Modifier
) {
    CommonTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        /**
         * It's recommended to specify the keyboard type:
         * - In this case it doesn't show the password that the user writes
         */
        keyboardType = KeyboardType.Password,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier
    )
}

@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp),
        shape = textFieldShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = focusedBorderColor,
            unfocusedBorderColor = unfocusedBorderColor
        ),
        visualTransformation = visualTransformation,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}