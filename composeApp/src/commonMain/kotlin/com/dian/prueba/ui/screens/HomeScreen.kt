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
import androidx.compose.ui.unit.*
import com.dian.prueba.model.getAllAmazonCategories
import com.dian.prueba.ui.components.WebViewHome
import org.jetbrains.compose.ui.tooling.preview.Preview

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
        PrimaryScrollableTabRow(
            selectedTabIndex = 0,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            edgePadding = 0.dp,
            divider = {
                HorizontalDivider(
                    thickness = 0.dp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            ) {
            getAllAmazonCategories().forEach { category ->
                Tab(
                    selected = false,
                    unselectedContentColor = MaterialTheme.colorScheme.secondary,
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    onClick = { /*TODO*/ },
                    text = {
                        Text(
                            text = category.categoryName,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyMedium
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
                placeholder = {Text(text = "Buscar o hacer una pregunta",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary)},
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
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            dividerColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        content = { Color.Transparent }
    )