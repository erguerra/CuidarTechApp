package br.com.cuidartech.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NursingProcessListContent(
    state: NursingProcessListViewModel.ViewState,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            is NursingProcessListViewModel.ViewState.Loading -> CircularProgressIndicator()
            is NursingProcessListViewModel.ViewState.Error -> Text("QUe merda tá acontecendo????")
            is NursingProcessListViewModel.ViewState.Success -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Text(
                        text = "Processos de Enfermagem",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Cyan,
                        )
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }

                items(state.nursingProcessList) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(all = 8.dp),
                        elevation = 4.dp,
                    ) {
                        Column {
                            Text(
                                text = it.title,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                ),
                                color = Color.Magenta,
                            )

                            Text(
                                text = it.body,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                ),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }

}