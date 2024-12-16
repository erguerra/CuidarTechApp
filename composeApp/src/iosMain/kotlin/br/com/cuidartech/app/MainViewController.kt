package br.com.cuidartech.app

import androidx.compose.ui.window.ComposeUIViewController
import br.com.cuidartech.app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }