package br.pucpr.app.libmangesys.ui.screens.borrows

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.pucpr.app.libmangesys.data.models.BorrowWithDetails
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class BorrowsScreen : Screen {
    @Composable
    override fun Content() {
        BorrowsScreenContent()
    }
}

@Preview
@Composable
fun BorrowsScreenPreview() {
    MaterialTheme {
        BorrowsScreenContent()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BorrowsScreenContent() {
    val navigator = LocalNavigator.current
    val viewModel = koinViewModel<BorrowsViewModel>()

    val showBottomSheet = remember { mutableStateOf(false) }
    val bookEdit = viewModel.borrowEdit

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Empréstimos") },
                colors =
                    TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(0xFF1f1d2b),
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
                containerColor = Color(0xFF49ab6c),
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp),
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Show Bottom Sheet")
            }
        },
        containerColor = Color(0xFF1f1d2b),
        contentColor = Color.White
    ) { innerPadding ->
        BorrowsList(viewModel, innerPadding)

        val isBottomSheetVisible = showBottomSheet.value || bookEdit != null

        if (isBottomSheetVisible) {
            BorrowBottomSheet(showBottomSheet)
        }
    }
}

@Composable
fun BorrowsList(
    viewModel: BorrowsViewModel,
    contentPadding: PaddingValues,
) {
    val borrows by viewModel.borrows.collectAsState()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding),
        contentAlignment = Alignment.Center,
    ) {
        if (borrows.isNotEmpty()) {
            Column {
                Spacer(Modifier.size(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(borrows) { borrowWithDetails ->
                        BorrowListItem(borrowWithDetails, viewModel)
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
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    modifier =
                        Modifier
                            .padding(bottom = 32.dp)
                            .padding(horizontal = 24.dp),
                    text = "Nenhum empréstimo encontrado",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun BorrowListItem(
    borrowWithDetails: BorrowWithDetails,
    viewModel: BorrowsViewModel,
) {
    val borrow = borrowWithDetails.borrow
    val book = borrowWithDetails.book
    val user = borrowWithDetails.user

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color(0xFF332f3f)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF382e57),
            contentColor = Color.White
        )
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        viewModel.edit(borrow)
                    },
        ) {
            AsyncImage(
                modifier = Modifier.size(width = 80.dp, height = 120.dp),
                model = book?.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(Modifier.size(16.dp))
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Usuário",
                    fontSize = 12.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = user?.name ?: "",
                    fontSize = 18.sp,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Spacer(Modifier.size(16.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Livro",
                    fontSize = 12.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = book?.title ?: "",
                    fontSize = 18.sp,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
            }
        }
    }
}

