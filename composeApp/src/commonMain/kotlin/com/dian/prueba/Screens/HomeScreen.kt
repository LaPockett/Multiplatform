package com.dian.prueba.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dian.prueba.NavigationScroll.getAllAmazonCategories
import com.dian.prueba.ui.WebViewCustom

@Composable
fun HomeScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        WebViewCustom(
            modifier = Modifier.fillMaxWidth()
        )
        /*Text(
            text = "Home Screen",
            modifier = Modifier.align(Alignment.Center)
        )*/
    }
}

@Composable
fun ScrollRowAmazon(){

    Column (
        modifier = Modifier.padding(top = 15.dp)

    ){
        SearchBarAmazon()
        Column(
        ) {
            ScrollableTabRow(
                selectedTabIndex = 0,
                modifier = Modifier.padding(10.dp),
                backgroundColor = Color.White,
                contentColor = Color.Black,
                edgePadding = 0.dp,

                ){
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarAmazon(){
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    //Scaffold{
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChange = {query = it},
            onSearch = {active = false},
            active = active,
            onActiveChange = {active = it},
            placeholder = { Text("Buscar o hacer una pregunta") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        if (query.isNotEmpty()){
                            query = ""
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.CenterFocusStrong,
                    contentDescription = "Close Icon"
                )
            },

            ){
        //}
    }

}
