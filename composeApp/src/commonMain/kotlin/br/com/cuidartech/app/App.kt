package br.com.cuidartech.app

import androidx.compose.runtime.Composable
import br.com.cuidartech.app.ui.NursingProcessListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {

    KoinContext {
        NursingProcessListScreen()
    }
}

