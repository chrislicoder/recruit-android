package nz.co.test.transactions.data

import nz.co.test.transactions.services.Transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor() {

    private var selectedTransactionIndex: Int = 0

    private val cachedTransactions = mutableListOf<Transaction>()

    fun getTransactions(): List<Transaction> {
        return cachedTransactions
    }

    fun cacheTransactions(transactions: List<Transaction>) {
        cachedTransactions.clear()
        cachedTransactions.addAll(transactions)
    }

    fun setSelectedTransactionIndex(selectedTransactionIndex: Int) {
        this.selectedTransactionIndex = selectedTransactionIndex
    }

    fun getSelectedTransaction(): Transaction? =
        cachedTransactions.find { selectedTransactionIndex == it.id }
}