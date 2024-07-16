package nz.co.test.transactions.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import nz.co.test.transactions.services.Transaction
import nz.co.test.transactions.services.TransactionApi
import javax.inject.Inject

@HiltViewModel
class TransactionListViewModel @Inject constructor(
    private val transactionApi: TransactionApi
) : ViewModel() {

    private val _transactions = MutableStateFlow<Array<Transaction>>(emptyArray())
    val transactions: StateFlow<Array<Transaction>> = _transactions.asStateFlow()

    fun fetchTransactions() {
        viewModelScope.launch {
            try {
                _transactions.value = transactionApi.getTransactions()
                for (transaction in _transactions.value) {
                    Log.d("print exception", transaction.toString())
                }
            } catch (e: Exception) {
                Log.d("print exception", e.toString())
            }
        }
    }
}