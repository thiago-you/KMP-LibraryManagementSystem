package br.pucpr.app.libmangesys.ui.screens.users

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.pucpr.app.libmangesys.data.models.User
import br.pucpr.app.libmangesys.koinViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview

class UsersScreen : Screen {
    @Composable
    override fun Content() {
        UsersScreenContent()
    }
}

@Preview
@Composable
fun UsersScreenPreview() {
    MaterialTheme {
        UsersScreenContent()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UsersScreenContent() {
    val navigator = LocalNavigator.current
    val viewModel = koinViewModel<UsersViewModel>()

    val showBottomSheet = remember { mutableStateOf(false) }
    val userEdit = viewModel.userEdit

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuários") },
                colors =
                    TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White,
                    ),
                navigationIcon = {
                    IconButton(onClick = { navigator?.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showBottomSheet.value = true
                },
                containerColor = Color.Green,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Show Bottom Sheet")
            }
        },
    ) { innerPadding ->
        UsersList(viewModel, innerPadding)

        val isBottomSheetVisible = showBottomSheet.value || userEdit != null

        if (isBottomSheetVisible) {
            UserBottomSheet(showBottomSheet)
        }
    }
}

@Composable
private fun UsersList(
    viewModel: UsersViewModel,
    contentPadding: PaddingValues,
) {
    val users by viewModel.users.collectAsState()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        if (users.isNotEmpty()) {
            Column {
                Spacer(Modifier.size(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(users) { user ->
                        UserListItem(user, viewModel)
                    }
                    item { Spacer(Modifier.size(96.dp)) }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    modifier =
                        Modifier
                            .padding(bottom = 32.dp)
                            .padding(horizontal = 24.dp),
                    text = "Nenhum usuário cadastrado ainda",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun UserListItem(
    user: User,
    viewModel: UsersViewModel,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp),
                ).padding(16.dp)
                .clickable {
                    viewModel.edit(user)
                },
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = user.name.toString(),
            fontSize = 18.sp,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = user.surname.toString(),
            fontSize = 18.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}
