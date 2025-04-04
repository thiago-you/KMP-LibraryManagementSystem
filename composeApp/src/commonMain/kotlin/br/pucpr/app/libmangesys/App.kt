package br.pucpr.app.libmangesys

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.pucpr.app.libmangesys.ui.screens.home.HomeIntent
import br.pucpr.app.libmangesys.ui.screens.home.HomeScreen
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            Navigator(HomeScreen(HomeIntent()))
        }
    }
}

@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()

    return viewModel {
        scope.get<T>()
    }
}