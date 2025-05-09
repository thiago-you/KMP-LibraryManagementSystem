package br.pucpr.app.libmangesys.ui.screens.books

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import br.pucpr.app.libmangesys.data.models.Book
import br.pucpr.app.libmangesys.ui.screens.users.SnackbarCustom
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

class BooksScreen : Screen {
    @Composable
    override fun Content() {
        BooksScreenContent()
    }
}

@Preview
@Composable
fun BooksScreenPreview() {
    MaterialTheme {
        BooksScreenContent()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BooksScreenContent() {
    val navigator = LocalNavigator.current
    val viewModel = koinViewModel<BooksViewModel>()

    val showBottomSheet = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val bookEdit = viewModel.bookEdit
    val deleteError = viewModel.deleteError

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text("Livros") },
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
        BooksList(viewModel, innerPadding)

        val isBottomSheetVisible = showBottomSheet.value || bookEdit != null

        if (isBottomSheetVisible) {
            BookBottomSheet(showBottomSheet)
        }
        if (deleteError == true) {
            SnackbarCustom(
                snackbarHostState = snackbarHostState,
                message =  "Não é possível deletar livros com empréstimos registrados"
            )
        }
    }
}

@Composable
private fun BooksList(
    viewModel: BooksViewModel,
    contentPadding: PaddingValues,
) {
    val books by viewModel.books.collectAsState()

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(contentPadding),
        contentAlignment = Alignment.Center,
    ) {
        if (books.isNotEmpty()) {
            Column {
                Spacer(Modifier.size(16.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(books) { book ->
                        BookListItem(book, viewModel)
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
                    text = "Nenhum livro adicionado ainda",
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
private fun BookListItem(
    book: Book,
    viewModel: BooksViewModel,
) {
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
                        viewModel.edit(book)
                    },
        ) {
            AsyncImage(
                modifier = Modifier.size(width = 80.dp, height = 120.dp),
                model = book.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )
            Spacer(Modifier.size(16.dp))
            Column {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = book.title.toString(),
                    fontSize = 18.sp,
                    color = Color.White,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = book.title.toString(),
                    fontSize = 14.sp,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 4,
                )
            }
        }
    }
}
