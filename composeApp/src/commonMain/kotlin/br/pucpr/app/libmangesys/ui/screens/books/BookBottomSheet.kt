package br.pucpr.app.libmangesys.ui.screens.books

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.pucpr.app.libmangesys.data.models.Book
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun BookBottomSheetPreview() {
    val showBottomSheet = remember { mutableStateOf(true) }
    BookBottomSheet(showBottomSheet)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BookBottomSheet(showBottomSheet: MutableState<Boolean>) {
    val viewmodel = koinViewModel<BooksViewModel>()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val isSaving = viewmodel.isSaving
    val savedSuccessfully = viewmodel.savedSuccessfully

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    val bookEdit by remember { derivedStateOf { viewmodel.bookEdit } }

    viewmodel.getBookToSave().also { book ->
        title = book?.title ?: ""
        description = book?.description ?: ""
        imageUrl = book?.imageUrl ?: ""
    }

    if (savedSuccessfully) {
        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet.value = false
                viewmodel.clearBookEdit()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
            viewmodel.clearBookEdit()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 40.dp),
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                label = { Text("Título") },
                onValueChange = { value ->
                    title = value
                },
            )
            Spacer(Modifier.size(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                label = { Text("Descrição") },
                onValueChange = { value ->
                    description = value
                },
            )
            Spacer(Modifier.size(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = imageUrl,
                label = { Text("URL da Imagem") },
                onValueChange = { value ->
                    imageUrl = value
                },
            )
            Spacer(Modifier.size(32.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                    ),
                onClick = {
                    if (isSaving) {
                        return@Button
                    }

                    viewmodel.save(
                        book = Book(
                            id = bookEdit?.id,
                            title = title,
                            description = description,
                            imageUrl = imageUrl
                        )
                    )
                },
            ) {
                Text(
                    text = "Salvar",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                )
            }

            if (bookEdit != null) {
                Spacer(Modifier.size(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isSaving) {
                                return@clickable
                            }

                            viewmodel.delete(bookEdit)
                        },
                    textAlign = TextAlign.Center,
                    text = "Deletar",
                    color = Color.Black,
                )
            }
        }
    }
}
