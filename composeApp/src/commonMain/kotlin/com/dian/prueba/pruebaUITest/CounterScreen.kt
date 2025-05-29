package com.dian.prueba.pruebaUITest

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * PRUEBA UI TEST
 */
class CounterViewModel {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count.asStateFlow()

    fun increment() {
        _count.value += 1
    }
}

@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    val count by viewModel.count.collectAsState()

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = count.toString(), fontSize = 24.sp, modifier = Modifier.testTag("counterText"))
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.increment() }, modifier = Modifier.testTag("incrementButton")) {
            Text("Increment")
        }
    }
}

//Otro ejemplo:
@Composable
fun ContentButton() {
    Text(
        modifier = Modifier.testTag("TextAdd"),
        text = "Add"
    )
    Icon(
        modifier = Modifier.testTag("AddIcon"),
        imageVector = Icons.Default.Add,
        contentDescription = "AddIcon"
    )
}

@Composable
fun ButtonAdd(color: Color) {
    Button(
        onClick = { /*TODO*/ },
        content = { ContentButton() },
        modifier = Modifier
            .testTag("ButtonAdd"),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    )
}