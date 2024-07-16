package nz.co.test.transactions.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.uicomponents.TransactionAdapter

class TransactionListFragment : Fragment(R.layout.fragment_transaction_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Assuming you have a RecyclerView with ID `recyclerView`
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val adapter = TransactionAdapter { transactionId ->
            findNavController().navigate(R.id.action_transactionListFragment_to_transactionDetailFragment)
        }
        recyclerView.adapter = adapter
    }
}