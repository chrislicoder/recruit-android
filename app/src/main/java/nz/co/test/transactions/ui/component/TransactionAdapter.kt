package nz.co.test.transactions.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import nz.co.test.transactions.R
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.util.Util.getDisplayedAmount
import nz.co.test.transactions.util.Util.getDisplayedDateString
import java.math.BigDecimal
import java.text.DecimalFormat

class TransactionAdapter(private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var transactions = emptyList<Transaction>()

    fun updateTransaction(transactions: List<Transaction>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(transactions[position], clickListener)
    }

    override fun getItemCount() = transactions.size

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val transactionDateTextView: TextView =
            itemView.findViewById(R.id.transactionDateTextView)
        private val summaryTextView: TextView = itemView.findViewById(R.id.summaryTextView)
        private val debitTextView: TextView = itemView.findViewById(R.id.debitTextView)
        private val creditTextView: TextView = itemView.findViewById(R.id.creditTextView)

        fun bind(transaction: Transaction, clickListener: (Int) -> Unit) {
            transactionDateTextView.text = getDisplayedDateString(transaction.transactionDate)

            summaryTextView.text = transaction.summary

            debitTextView.text = getDisplayedAmount(transaction.debit)
            debitTextView.isVisible = transaction.debit > BigDecimal.ZERO

            creditTextView.text = getDisplayedAmount(transaction.credit)
            creditTextView.isVisible = transaction.credit > BigDecimal.ZERO

            // Bind transaction data to views
            itemView.setOnClickListener {
                clickListener(transaction.id)
            }
        }
    }
}