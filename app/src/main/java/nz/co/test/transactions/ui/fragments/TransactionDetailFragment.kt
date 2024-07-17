package nz.co.test.transactions.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import nz.co.test.transactions.R

class TransactionDetailFragment : Fragment(R.layout.fragment_transaction_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactionId = arguments?.getInt("transactionId")
        // Use the transactionId to fetch and display transaction details
    }
}