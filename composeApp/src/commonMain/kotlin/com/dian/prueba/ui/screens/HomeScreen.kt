package com.dian.prueba.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.dian.prueba.model.getAllAmazonCategories
import com.dian.prueba.ui.components.WebViewHome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBarAmazon(
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it }
            )
            ScrollRowAmazon()
            WebViewHome(
                query = query,
            )
        }

    }
}

@Composable
fun ScrollRowAmazon() {

    Column(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        ScrollableTabRow(
            selectedTabIndex = 0,
            containerColor = Color.White,
            contentColor = Color.Black,
            edgePadding = 0.dp,

            ) {
            getAllAmazonCategories().forEach { category ->
                Tab(
                    selected = false,
                    onClick = { /*TODO*/ },
                    text = {
                        Text(
                            text = category.categoryName,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                )
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarAmazon(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit
) =
    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.fillMaxWidth(),
                query = query,
                onQueryChange = {
                    onQueryChange(it)
                },
                onSearch = { onSearch(query) },
                expanded = active,
                onExpandedChange = onActiveChange,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) {
                                onQueryChange("")
                            }
                        },
                        imageVector = Icons.Default.Search,
                        contentDescription = "Scan Icon"
                    )
                },
                placeholder = {Text("Buscar o hacer una pregunta")},
                interactionSource = MutableInteractionSource()
            )
        },
        windowInsets = WindowInsets(
            top = 0,
            bottom = 0
        ),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
        onExpandedChange = { onActiveChange },
        expanded = false,
        shape = SearchBarDefaults.inputFieldShape,
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xffbecaf6),
            dividerColor = Color(0xFF080e45)
        ),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        content = { Color.Transparent }
    )