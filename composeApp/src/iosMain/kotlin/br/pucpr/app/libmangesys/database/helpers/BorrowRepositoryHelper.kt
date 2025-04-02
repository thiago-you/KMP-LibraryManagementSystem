package br.pucpr.app.libmangesys.database.helpers

import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BorrowRepositoryHelper: KoinComponent {
    private val borrowsRepository: BorrowsRepository by inject()
    fun getBorrowRepository(): BorrowsRepository = borrowsRepository
}