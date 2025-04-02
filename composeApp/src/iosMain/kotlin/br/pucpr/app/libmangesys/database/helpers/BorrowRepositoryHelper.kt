package br.pucpr.app.libmangesys.database.helpers

import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BorrowRepositoryHelper: KoinComponent {
    private val borrowRepository: BorrowRepository by inject()
    fun getBorrowRepository(): BorrowRepository = borrowRepository
}