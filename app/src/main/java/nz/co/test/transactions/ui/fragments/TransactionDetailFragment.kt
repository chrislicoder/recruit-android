package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.R
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.util.Util
import nz.co.test.transactions.util.Util.collectStateFlow
import nz.co.test.transactions.util.Util.getDisplayedAmount
import nz.co.test.transactions.util.Util.getDisplayedGST
import nz.co.test.transactions.viewmodel.TransactionDetailViewModel
import java.math.BigDecimal

@AndroidEntryPoint
class TransactionDetailFragment : Fragment(R.layout.fragment_transaction_detail) {
    private val viewModel: TransactionDetailViewModel by viewModels()

    private lateinit var tvDate: TextView
    private lateinit var tvSummary: TextView
    private lateinit var tvDebit: TextView
    private lateinit var tvCredit: TextView
    private lateinit var tvGst: TextView
    private lateinit var debitContainer: View
    private lateinit var creditContainer: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDate = view.findViewById(R.id.tvDate)
        tvSummary = view.findViewById(R.id.tvSummary)
        tvDebit = view.findViewById(R.id.tvDebit)
        tvCredit = view.findViewById(R.id.tvCredit)
        tvGst = view.findViewById(R.id.tvGst)
        debitContainer = view.findViewById(R.id.debitContainer)
        creditContainer = view.findViewById(R.id.creditContainer)

        // Display the transaction detail when the transaction is fetched from local repository
        collectStateFlow(viewModel.selectedTransaction) { transaction ->
            transaction?.let {
                displayTransactionDetail(it)
            }
        }

        viewModel.fetchSelectedTransaction()
    }

    private fun displayTransactionDetail(transaction: Transaction) {
        tvDate.text = Util.getDisplayedDateString(transaction.transactionDate)

        tvSummary.text = transaction.summary

        debitContainer.isVisible = transaction.debit > BigDecimal.ZERO
        tvDebit.text = getDisplayedAmount(transaction.debit)

        creditContainer.isVisible = transaction.credit > BigDecimal.ZERO
        tvCredit.text = getDisplayedAmount(transaction.credit)

        val processedAmount = when {
            transaction.debit > BigDecimal.ZERO -> transaction.debit
            transaction.credit > BigDecimal.ZERO -> transaction.credit
            else -> BigDecimal.ZERO
        }
        tvGst.text = getDisplayedGST(processedAmount)
    }
}