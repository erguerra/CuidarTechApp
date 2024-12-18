package br.com.cuidartech.app.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun CuidarTechAppBar(
    title: String?,
    contentColor: Color? = null,
    navigateBackAction: (() -> Unit)?,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.surface,
        title = {
            title?.let{
                Text(text = it, color = contentColor ?: MaterialTheme.colorScheme.primary)
            }
        },
        navigationIcon = {
            navigateBackAction?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar", // TODO: Internationalize
                        tint = contentColor ?: MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    )
}