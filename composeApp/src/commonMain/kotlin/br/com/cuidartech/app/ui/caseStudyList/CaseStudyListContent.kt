package br.com.cuidartech.app.ui.caseStudyList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.cuidartech.app.ui.model.CaseStudyItemUiModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CaseStudyListContent(
    navigateBack:  () -> Unit,
    viewState: CaseStudyListViewModel.ViewState,
    onItemClick: (CaseStudyItemUiModel) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Estudos de Caso") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        when (viewState) {
            is CaseStudyListViewModel.ViewState.Loading -> CircularProgressIndicator()
            is CaseStudyListViewModel.ViewState.Error -> Text("Que merda tá acontecendo????")
            is CaseStudyListViewModel.ViewState.Success -> LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = 16.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text(
                        text = viewState.title,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    )
                }

                items(viewState.caseStudies, key = { it.id }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        shape = MaterialTheme.shapes.medium,
                        elevation = 4.dp,
                        onClick = { onItemClick(it) }
                    ) {
                        Text(
                            text = it.title,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.h3,
                        )
                    }
                }
            }
        }
    }
}
