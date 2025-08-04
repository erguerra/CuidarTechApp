package br.com.cuidartech.app.ui.caseStudy

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.cuidartech.app.ui.components.LargeText

@Composable
fun FeedbackDialog(
    modifier: Modifier = Modifier,
    variant: FeedbackDialogVariant = FeedbackDialogVariant.WRONG_ANSWER,
    primaryColor: Color,
    content: String,
    close: () -> Unit,
) {
    Dialog(
        onDismissRequest = close,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            elevation = 4.dp,
        ) {
            val scrollState = rememberScrollState(0)
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val (title, color, icon) =
                    when (variant) {
                        FeedbackDialogVariant.WRONG_ANSWER ->
                            Triple(
                                "Resposta Errada",
                                Color.Red.copy(alpha = 0.7f),
                                Icons.Default.Warning,
                            )

                        FeedbackDialogVariant.RIGHT_ANSWER ->
                            Triple(
                                "Resposta Certa!",
                                primaryColor,
                                Icons.Default.Check,
                            )
                    }
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = icon,
                        tint = color,
                        contentDescription = null,
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = color,
                    )
                }
                Spacer(Modifier.size(24.dp))

                LargeText(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 4.dp),
                    text = content,
                )

                Spacer(Modifier.size(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = close,
                        modifier = Modifier.padding(8.dp),
                    ) {
                        val dismissAction =
                            when (variant) {
                                FeedbackDialogVariant.RIGHT_ANSWER -> "Concluir"
                                FeedbackDialogVariant.WRONG_ANSWER -> "Tentar Novamente"
                            }
                        Text(
                            text = dismissAction,
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 18.sp,
                            color = primaryColor,
                        )
                    }
                }
            }
        }
    }
}

enum class FeedbackDialogVariant {
    RIGHT_ANSWER,
    WRONG_ANSWER,
}
