package br.pucpr.app.libmangesys

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import br.pucpr.app.libmangesys.ui.screens.home.HomeIntent
import br.pucpr.app.libmangesys.ui.screens.home.HomeScreen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            Navigator(HomeScreen(HomeIntent()))
        }
    }
}