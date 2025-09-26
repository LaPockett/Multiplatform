package com.lapockett.showkase.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import com.airbnb.android.showkase.annotation.ShowkaseComposable
import com.lapockett.showkase.ui.theme.ShowkaseTheme

// ============ COMPONENTES BÁSICOS ============

/**
 * Botón primario reutilizable con diferentes variantes
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}

@Preview
@ShowkaseComposable(name = "Primary Button", group = "Basic Components")
@Composable
fun PrimaryButtonPreview() {
    ShowkaseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            PrimaryButton(
                text = "Botón Normal",
                onClick = {}
            )
            PrimaryButton(
                text = "Con Icono",
                onClick = {},
                icon = Icons.Default.Add
            )
            PrimaryButton(
                text = "Cargando",
                onClick = {},
                isLoading = true
            )
            PrimaryButton(
                text = "Deshabilitado",
                onClick = {},
                enabled = false
            )
        }
    }
}

/**
 * Botón secundario outline
 */
//@ShowkaseComposable(name = "Secondary Button", group = "Basic Components")
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    ShowkaseTheme {
        SecondaryButton(
            text = "Botón Secundario",
            onClick = {}
        )
    }
}

/**
 * Campo de texto personalizado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    isError: Boolean = false,
    errorMessage: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(!isPassword) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            label = { if (label.isNotEmpty()) Text(label) },
            placeholder = { if (placeholder.isNotEmpty()) Text(placeholder) },
            isError = isError,
            leadingIcon = if (leadingIcon != null) {
                { Icon(leadingIcon, contentDescription = null) }
            } else null,
            trailingIcon = {
                if (trailingIcon != null && onTrailingIconClick != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(trailingIcon, contentDescription = null)
                    }
                } else if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar"
                        )
                    }
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )

        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview
@ShowkaseComposable(name = "Custom Text Field", group = "Form Components")
@Composable
fun CustomTextFieldPreview() {
    ShowkaseTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            CustomTextField(
                value = "",
                onValueChange = {},
                label = "Email",
                placeholder = "tu@email.com",
                leadingIcon = Icons.Default.Email
            )

            CustomTextField(
                value = "valor incorrecto",
                onValueChange = {},
                label = "Campo con error",
                isError = true,
                errorMessage = "Este campo es requerido"
            )

            CustomTextField(
                value = "contraseña",
                onValueChange = {},
                label = "Contraseña",
                isPassword = true
            )
        }
    }
}

// ============ COMPONENTES DE TARJETAS ============

/**
 * Tarjeta de producto estilo Amazon
 */
@Composable
fun ProductCard(
    title: String,
    price: String,
    originalPrice: String? = null,
    rating: Float = 0f,
    reviewCount: Int = 0,
    imageUrl: String? = null,
    isFavorite: Boolean = false,
    onCardClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onCardClick,
        modifier = modifier
            .width(160.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Imagen del producto
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl != null) {
                    // Aquí iría un AsyncImage en producción
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Botón de favorito
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Favorito",
                        tint = if (isFavorite) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }

            // Contenido
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    modifier = Modifier.height(40.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Precio
                if (originalPrice != null) {
                    Text(
                        text = originalPrice,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(
                    text = price,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Rating
                if (rating > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color(0xFFFFA000)
                        )
                        Text(
                            text = "%.1f".format(rating),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Text(
                            text = "($reviewCount)",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@ShowkaseComposable(name = "Product Card", group = "Card Components")
@Composable
fun ProductCardPreview() {
    ShowkaseTheme {
        Row(modifier = Modifier.padding(16.dp)) {
            ProductCard(
                title = "Smartphone Android 128GB",
                price = "$299.99",
                originalPrice = "$399.99",
                rating = 4.5f,
                reviewCount = 128,
                isFavorite = false,
                onCardClick = {},
                onFavoriteClick = {}
            )

            ProductCard(
                title = "Auriculares Inalámbricos",
                price = "$79.99",
                rating = 4.2f,
                reviewCount = 64,
                isFavorite = true,
                onCardClick = {},
                onFavoriteClick = {}
            )
        }
    }
}

// ============ COMPONENTES DE NAVEGACIÓN ============

/**
 * Top App Bar personalizado
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onMenuClick: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Medium
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                }
            } else if (onMenuClick != null) {
                IconButton(onClick = onMenuClick) {
                    Icon(Icons.Default.Menu, contentDescription = "Menú")
                }
            }
        },
        actions = { actions() },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview
@ShowkaseComposable(name = "Custom Top App Bar", group = "Navigation Components")
@Composable
fun CustomTopAppBarPreview() {
    ShowkaseTheme {
        Column {
            CustomTopAppBar(
                title = "Mi Aplicación",
                onMenuClick = {}
            )

            CustomTopAppBar(
                title = "Detalles del Producto",
                onBackClick = {},
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Favorite, contentDescription = "Favorito")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        }
    }
}

/**
 * Bottom Navigation Bar
 */
@Composable
fun CustomBottomNavigation(
    currentDestination: String,
    onDestinationSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        "Inicio" to Icons.Default.Home,
        "Buscar" to Icons.Default.Search,
        "Carrito" to Icons.Default.ShoppingCart,
        "Perfil" to Icons.Default.AccountCircle
    )

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            items.forEach { (title, icon) ->
                val isSelected = currentDestination == title

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { onDestinationSelected(title) }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = title,
                        fontSize = 10.sp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Preview
@ShowkaseComposable(name = "Bottom Navigation", group = "Navigation Components")
@Composable
fun CustomBottomNavigationPreview() {
    ShowkaseTheme {
        CustomBottomNavigation(
            currentDestination = "Inicio",
            onDestinationSelected = {}
        )
    }
}

// ============ COMPONENTES DE FEEDBACK ============

/**
 * Diálogo de alerta personalizado
 */
@Composable
fun AlertDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = "Aceptar",
    dismissText: String = "Cancelar",
    showDismissButton: Boolean = true
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (showDismissButton) {
                        SecondaryButton(
                            text = dismissText,
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    PrimaryButton(
                        text = confirmText,
                        onClick = onConfirm,
                        modifier = if (showDismissButton) Modifier.weight(1f) else Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@ShowkaseComposable(name = "Alert Dialog", group = "Feedback Components")
@Composable
fun AlertDialogPreview() {
    ShowkaseTheme {
        Box(
            modifier = Modifier
                .size(300.dp, 200.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            AlertDialog(
                title = "Eliminar producto",
                message = "¿Estás seguro de que quieres eliminar este producto del carrito?",
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}

/**
 * Indicador de carga
 */

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size - 16.dp),
            strokeWidth = strokeWidth,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@ShowkaseComposable(name = "Loading Indicator", group = "Feedback Components")
@Composable
fun LoadingIndicatorPreview() {
    ShowkaseTheme {
        LoadingIndicator()
    }
}

// ============ COMPONENTES ESPECIALES ============

/**
 * Barra de búsqueda estilo Amazon
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Buscar productos..."
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = "Buscar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.width(8.dp))

            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 16.sp
                ),
                decorationBox = { innerTextField ->
                    if (query.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                },
                singleLine = true
            )

            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Limpiar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@ShowkaseComposable(name = "Search Bar", group = "Special Components")
@Composable
fun SearchBarPreview() {
    ShowkaseTheme {
        Column {
            SearchBar(
                query = "",
                onQueryChange = {},
                onSearch = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            SearchBar(
                query = "Smartphone",
                onQueryChange = {},
                onSearch = {}
            )
        }
    }
}