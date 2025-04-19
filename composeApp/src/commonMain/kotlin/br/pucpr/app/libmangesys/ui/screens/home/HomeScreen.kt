package br.pucpr.app.libmangesys.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.pucpr.app.libmangesys.enums.NavigationEnum
import br.pucpr.app.libmangesys.ui.screens.books.BooksScreen
import br.pucpr.app.libmangesys.ui.screens.borrows.BorrowsScreen
import br.pucpr.app.libmangesys.ui.screens.users.UsersScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

data class HomeScreen(private val intent: HomeIntent) : Screen {
    @Composable
    override fun Content() {
        HomeScreenContent(intent)
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreenContent(HomeIntent())
    }
}

@Composable
fun HomeScreenContent(intent: HomeIntent) {
    val navigator = LocalNavigator.current

    Scaffold(
        containerColor = Color(0xFF1f1d2b),
        contentColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1f1d2b),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color(0xFF332f3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = intent.getTitleText(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.size(48.dp))

                    ButtonWithIcon(intent.getBtnUsersText(), Icons.Default.Person) {
                        navigate(navigator, intent.onBtnUsersClick())
                    }
                    ButtonWithIcon(intent.getBtnBooksText(), Icons.Filled.Favorite) {
                        navigate(navigator, intent.onBtnBooksClick())
                    }
                    ButtonWithIcon(intent.getBtnBorrowsText(), Icons.AutoMirrored.Filled.List) {
                        navigate(navigator, intent.onBtnBorrowsClick())
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonWithIcon(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3d314f))
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

private fun navigate(navigator: Navigator?, destination: NavigationEnum) {
    when (destination) {
        NavigationEnum.USERS -> navigator?.push(UsersScreen())
        NavigationEnum.BOOKS -> navigator?.push(BooksScreen())
        NavigationEnum.BORROWS -> navigator?.push(BorrowsScreen())
    }
}