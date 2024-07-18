package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.R
import nz.co.test.transactions.ui.component.TransactionAdapter
import nz.co.test.transactions.util.Util.collectStateFlow
import nz.co.test.transactions.viewmodel.TransactionListViewModel

@AndroidEntryPoint
class TransactionListFragment : Fragment(R.layout.fragment_transaction_list) {
    private val viewModel: TransactionListViewModel by viewModels()
    private lateinit var transactionList: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        transactionList = view.findViewById(R.id.recyclerView)

        val adapter = TransactionAdapter { transactionId ->
            viewModel.setSelectedTransactionId(transactionId)
            findNavController().navigate(R.id.action_transactionListFragment_to_transactionDetailFragment)
        }
        transactionList.layoutManager = LinearLayoutManager(context)
        transactionList.adapter = adapter

        // Display the transaction list after fetching transaction list from API call or local repository cache
        collectStateFlow(viewModel.transactions) { transactions ->
            adapter.updateTransaction(transactions)
        }

        viewModel.fetchTransactions()
    }
}