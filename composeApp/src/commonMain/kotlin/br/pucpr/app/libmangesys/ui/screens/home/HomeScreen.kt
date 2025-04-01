package br.pucpr.app.libmangesys.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(bottom = 32.dp)
                    .padding(horizontal = 16.dp),
                text = intent.getTitleText(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ButtonComponent(text = intent.getBtnUsersText()) {
                    navigate(navigator, intent.onBtnUsersClick())
                }
                ButtonComponent(text = intent.getBtnBooksText()) {
                    navigate(navigator, intent.onBtnBooksClick())
                }
                ButtonComponent(text = intent.getBtnBorrowsText()) {
                    navigate(navigator, intent.onBtnBorrowsClick())
                }
            }
        }
    }
}

@Composable
fun ButtonComponent(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.White)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

private fun navigate(navigator: Navigator?, destination: NavigationEnum) {
    when (destination) {
        NavigationEnum.USERS -> navigator?.push(UsersScreen())
        NavigationEnum.BOOKS -> navigator?.push(BooksScreen())
        NavigationEnum.BORROWS -> navigator?.push(BorrowsScreen())
    }
}