package br.com.cuidartech.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Header(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color? = null,
    description: String? = null,
    descriptionColor: Color? = null,
) {

    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = titleColor ?: MaterialTheme.colorScheme.primary,
        )

        description?.let {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = descriptionColor ?: MaterialTheme.colorScheme.onBackground,
            )
        }

    }
}