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
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal


@ExperimentalCoroutinesApi
class TransactionDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var repository: TransactionRepository
    private lateinit var viewModel: TransactionDetailViewModel

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = TransactionDetailViewModel(repository)
    }

    @Test
    fun `test fetches transaction from repository`() =
        coroutineTestRule.runBlockingTest {
            // Arrange
            val transaction = Transaction(
                id = 1,
                transactionDate = "2021-08-31T15:47:10",
                summary = "Hackett, Stamm and Kuhn",
                debit = BigDecimal(9379.55),
                credit = BigDecimal.ZERO
            )
            coEvery { repository.getSelectedTransaction() } returns transaction

            // Act
            viewModel.fetchSelectedTransaction()

            // Assert
            assertEquals(transaction, viewModel.selectedTransaction.first())
            coVerify { repository.getSelectedTransaction() }
        }
}