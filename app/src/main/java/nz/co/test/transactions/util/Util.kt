package nz.co.test.transactions.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Util {
    fun getDisplayedDateString(inputDateString: String): String {
        val readableDateString: String
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dateTime = LocalDateTime.parse(inputDateString, formatter)

            // Format the LocalDateTime into a more readable format
            val readableFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm a")
            readableDateString = dateTime.format(readableFormatter)
        } catch (_: Exception) {
            return ""
        }


        return readableDateString
    }

    fun getDisplayedAmount(amount: BigDecimal): String {
        return try {
            DecimalFormat.getCurrencyInstance().format(amount)
        } catch (_: Exception) {
            ""
        }
    }

    fun <T> LifecycleOwner.collectStateFlow(
        stateFlow: StateFlow<T>,
        state: Lifecycle.State = Lifecycle.State.RESUMED,
        collect: (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(state) {
                stateFlow.collect { value ->
                    collect(value)
                }
            }
        }
    }
}