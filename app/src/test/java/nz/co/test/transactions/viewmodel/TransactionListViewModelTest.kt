package nz.co.test.transactions.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import nz.co.test.transactions.CoroutineTestRule
import nz.co.test.transactions.data.TransactionRepository
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.services.TransactionApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class TransactionListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var transactionApi: TransactionApi
    private lateinit var repository: TransactionRepository
    private lateinit var viewModel: TransactionListViewModel

    @Before
    fun setUp() {
        transactionApi = mockk()
        repository = mockk()
        viewModel = TransactionListViewModel(transactionApi, repository)
    }

    @Test
    fun `fetchTransactions with cached transactions`() = runBlockingTest {
        // Arrange
        val cachedTransactions = listOf(
            Transaction(
                id = 1,
                transactionDate = "2021-08-31T15:47:10",
                summary = "Hackett, Stamm and Kuhn",
                debit = BigDecimal(9379.55),
                credit = BigDecimal.ZERO
            )
        )
        coEvery { repository.getTransactions() } returns cachedTransactions

        // Act
        viewModel.fetchTransactions()

        // Assert
        assertEquals(cachedTransactions, viewModel.transactions.first())
        coVerify { repository.getTransactions() }
        coVerify(exactly = 0) { transactionApi.getTransactions() }
    }

    @Test
    fun `fetchTransactions without cached transactions`() = runBlockingTest {
        // Arrange
        val apiTransactions = listOf(
            Transaction(
                id = 1,
                transactionDate = "2021-08-31T15:47:10",
                summary = "Hackett, Stamm and Kuhn",
                debit = BigDecimal(9379.55),
                credit = BigDecimal.ZERO
            )
        )
        coEvery { repository.getTransactions() } returns emptyList()
        coEvery { transactionApi.getTransactions() } returns apiTransactions
        coEvery { repository.cacheTransactions(apiTransactions) } returns Unit

        // Act
        viewModel.fetchTransactions()

        // Assert
        assertEquals(apiTransactions, viewModel.transactions.first())
        coVerify { repository.getTransactions() }
        coVerify { transactionApi.getTransactions() }
        coVerify { repository.cacheTransactions(apiTransactions) }
    }

    @Test
    fun `setSelectedTransactionId calls repository`() {
        // Arrange
        val selectedTransactionIndex = 1
        coEvery { repository.setSelectedTransactionIndex(selectedTransactionIndex) } returns Unit

        // Act
        viewModel.setSelectedTransactionId(selectedTransactionIndex)

        // Assert
        coVerify { repository.setSelectedTransactionIndex(selectedTransactionIndex) }
    }
}
