package nz.co.test.transactions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.R
import nz.co.test.transactions.uicomponents.TransactionAdapter
import nz.co.test.transactions.viewmodel.TransactionListViewModel

@AndroidEntryPoint
class TransactionListFragment : Fragment(R.layout.fragment_transaction_list) {

    private val viewModel: TransactionListViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming you have a RecyclerView with ID `recyclerView`
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = TransactionAdapter { transactionId ->
            findNavController().navigate(R.id.action_transactionListFragment_to_transactionDetailFragment)
        }
        recyclerView.adapter = adapter

        viewModel.fetchTransactions()
    }
}