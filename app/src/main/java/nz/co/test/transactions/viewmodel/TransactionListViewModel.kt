package nz.co.test.transactions.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.data.TransactionRepository
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.services.TransactionApi
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val transactionApi: TransactionApi,
    private val repository: TransactionRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    fun fetchTransactions() {
        viewModelScope.launch {
            try {
                // Try to fetch the local repository cache
                if (repository.getTransactions().isNotEmpty()) {
                    _transactions.value = repository.getTransactions()
                } else {
                    // if empty, try to fetch from the api
                    _transactions.value = transactionApi.getTransactions()
                    repository.cacheTransactions(_transactions.value)
                }
            } catch (e: Exception) {
                Log.d("print exception", e.toString())
            }
        }
    }

    fun setSelectedTransactionId(selectedTransactionIndex: Int) {
        repository.setSelectedTransactionIndex(selectedTransactionIndex)
    }
}