package nz.co.test.transactions.di

import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.services.TransactionApi
import java.math.BigDecimal

class MockedTransactionApi : TransactionApi {
    override suspend fun getTransactions(): List<Transaction> {
        return listOf(
            Transaction(
                id = 1,
                transactionDate = "2021-08-31T15:47:10",
                summary = "Hackett, Stamm and Kuhn",
                debit = BigDecimal.ONE,
                credit = BigDecimal.ZERO
            ),
            Transaction(
                id = 2,
                transactionDate = "2021-10-30T15:47:10",
                summary = "Hackett, Stamm and Kuhn2",
                debit = BigDecimal.ZERO,
                credit = BigDecimal.TEN
            )
        )
    }
}