package br.com.cuidartech.app.ui.subjects

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.domain.model.SubjectFeatures
import br.com.cuidartech.app.ui.model.SubjectUIModel

@Composable
fun SubjectItem(
    modifier: Modifier = Modifier,
    subject: SubjectUIModel
) {
    Surface (
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        color = Color(subject.backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                Text(
                    subject.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
            if (subject.features?.contains(SubjectFeatures.CASE_STUDIES) == true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            subject.goToCaseStudies(
                                subject.id,
                                subject.title,
                                subject.backgroundColor
                            )
                        }
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "Estudos de Caso",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                        )
                    )
                }
            }
            if (subject.features?.contains(SubjectFeatures.NURSING_DIAGNOSTICS) == true) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            subject.goToNursingDiagnostics(
                                subject.id,
                                subject.title,
                                subject.backgroundColor
                            )
                        }
                        .padding(vertical = 16.dp, horizontal = 16.dp)
                ) {
                    Text(
                        text = "Diagnósticos",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White,
                        )
                    )
                }
            }
        }
    }

}