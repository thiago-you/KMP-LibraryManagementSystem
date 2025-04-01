package br.pucpr.app.libmangesys.ui.screens.home

import br.pucpr.app.libmangesys.enums.NavigationEnum

class HomeIntent {
    fun getTitleText(): String = "Sistema de Gerenciamento de Biblioteca"

    fun getBtnBooksText() = "Livros"

    fun getBtnUsersText() = "Usuários"

    fun getBtnBorrowsText() = "Empréstimos"

    fun onBtnBooksClick() = NavigationEnum.BOOKS

    fun onBtnUsersClick() = NavigationEnum.USERS

    fun onBtnBorrowsClick() = NavigationEnum.BORROWS
}