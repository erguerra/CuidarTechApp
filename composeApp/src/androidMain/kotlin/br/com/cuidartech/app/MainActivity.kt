package br.com.cuidartech.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.cuidartech.app.app.App
import br.com.cuidartech.app.di.initKoin
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.koin.core.context.GlobalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CuidarTech)
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        Firebase.initialize(this)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // For preview, we need to make sure Koin is initialized
    if (GlobalContext.getOrNull() == null) {
        initKoin()
    }
    App()
}
