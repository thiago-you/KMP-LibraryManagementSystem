package br.pucpr.app.libmangesys.ui.screens.users

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
import br.pucpr.app.libmangesys.data.models.User
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun UserBottomSheetPreview() {
    val showBottomSheet = remember { mutableStateOf(true) }
    UserBottomSheet(showBottomSheet)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserBottomSheet(showBottomSheet: MutableState<Boolean>) {
    val viewModel = koinViewModel<UsersViewModel>()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val isSaving = viewModel.isSaving
    val savedSuccessfully = viewModel.savedSuccessfully

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    val userEdit by remember { derivedStateOf { viewModel.userEdit } }

    viewModel.getUserToSave().also { user ->
        name = user?.name ?: ""
        surname = user?.surname ?: ""
    }

    if (savedSuccessfully) {
        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                showBottomSheet.value = false
                viewModel.clearUserEdit()
            }
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet.value = false
            viewModel.clearUserEdit()
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
                value = name,
                label = { Text("Nome") },
                onValueChange = { value ->
                    name = value
                },
            )
            Spacer(Modifier.size(16.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = surname,
                label = { Text("Sobrenome") },
                onValueChange = { value ->
                    surname = value
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

                    viewModel.save(
                        user = User(
                            id = userEdit?.id,
                            name = name,
                            surname = surname
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

            if (userEdit != null) {
                Spacer(Modifier.size(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = {
                                if (isSaving) {
                                    return@clickable
                                }

                                viewModel.delete(userEdit)
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
