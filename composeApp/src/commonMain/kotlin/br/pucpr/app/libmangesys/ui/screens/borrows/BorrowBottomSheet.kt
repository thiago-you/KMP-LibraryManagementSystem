package br.pucpr.app.libmangesys.ui.screens.borrows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import br.pucpr.app.libmangesys.data.models.Borrow
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun BorrowBottomSheetPreview() {
    val showBottomSheet = remember { mutableStateOf(true) }
    BorrowBottomSheet(showBottomSheet)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BorrowBottomSheet(showBottomSheet: MutableState<Boolean>) {
    val viewModel = koinViewModel<BorrowsViewModel>()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val users by viewModel.users.collectAsState()
    val books by viewModel.books.collectAsState()

    val usersData = users.takeIf { it.isNotEmpty() }?.map { it.name ?: "" } ?: listOf()
    val booksData = books.takeIf { it.isNotEmpty() }?.map { it.title ?: "" } ?: listOf()

    val isSaving = viewModel.isSaving
    val savedSuccessfully = viewModel.savedSuccessfully

    val borrowEdit by remember { derivedStateOf { viewModel.borrowEdit } }

    val selectedUserOption = remember { mutableStateOf("") }
    val selectedBookOption = remember { mutableStateOf("") }

    viewModel.getBorrowToSave().also { borrow ->
        selectedBookOption.value = books.find { it.id == borrow?.bookId }?.title ?: ""
        selectedUserOption.value = users.find { it.id == borrow?.userId }?.name ?: ""
    }

    if (savedSuccessfully) {
        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet.value = false
                viewModel.clearBorrowEdit()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
            viewModel.clearBorrowEdit()
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
            AutoComplete(
                data = usersData,
                label = "Usuário",
                selectedOption = selectedUserOption,
            )
            Spacer(Modifier.size(16.dp))
            AutoComplete(
                data = booksData,
                label = "Livro",
                selectedOption = selectedBookOption,
            )
            Spacer(Modifier.size(32.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF49ab6c),
                        contentColor = Color.White,
                    ),
                onClick = {
                    if (isSaving) {
                        return@Button
                    }

                    val userId = users.find { it.name == selectedUserOption.value }?.id
                    val bookId = books.find { it.title == selectedBookOption.value }?.id

                    viewModel.save(
                        borrow = Borrow(
                            id = borrowEdit?.id,
                            bookId = bookId,
                            userId = userId
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

            if (borrowEdit != null) {
                Spacer(Modifier.size(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = null,
                            onClick = {
                                if (isSaving) {
                                    return@clickable
                                }

                                viewModel.delete(borrowEdit)
                            }
                        ),
                    textAlign = TextAlign.Center,
                    text = "Deletar",
                    color = Color.Black,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoComplete(
    data: List<String>,
    label: String,
    selectedOption: MutableState<String>
) {
    var exp by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = exp, onExpandedChange = { exp = !exp }) {
        TextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            value = selectedOption.value,
            onValueChange = { selectedOption.value = it },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = exp,
                    modifier = Modifier. menuAnchor(MenuAnchorType. SecondaryEditable)
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        val filterOpts = data.filter { it.contains(selectedOption.value, ignoreCase = true) }

        if (filterOpts.isNotEmpty()) {
            ExposedDropdownMenu(expanded = exp, onDismissRequest = { exp = false }) {
                filterOpts.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(text = option)
                        },
                        onClick = {
                            selectedOption.value = option
                            exp = false
                        }
                    )
                }
            }
        }
    }
}